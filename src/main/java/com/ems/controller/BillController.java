package com.ems.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.http.MediaType;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ems.entity.Bill;
import com.ems.entity.FileUploadUtil;
import com.ems.service.BillService;
import com.ems.entity.Seller;
import com.ems.service.SellerService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class BillController {
    
    @Autowired
    private BillService billService;

    @Autowired
    private SellerService sellerService;
    
    
    @GetMapping("/bills")
    public String showBills(Model model, HttpSession session) {
    	if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        List<Bill> bills = billService.getAllBills();
        model.addAttribute("bills", bills);
        return "bills";
    }

    @GetMapping("/addbill")
    public String addBillForm(Model model, HttpSession session) {
    	if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        List<Seller> sellers = sellerService.getAllSellers();
        model.addAttribute("sellers", sellers);
        model.addAttribute("bill", new Bill());
        return "addbill";
    }

    @PostMapping("/billregister")
    public String registerBill(@ModelAttribute Bill bill, HttpSession session, @RequestParam("image")MultipartFile multiPartFile) throws IOException {
        if (bill.getModeofpayment() == null || bill.getModeofpayment().isEmpty()) {
            bill.setModeofpayment("Cash"); 
        }
        if(!multiPartFile.isEmpty())
        {
        	String fileName=StringUtils.cleanPath(multiPartFile.getOriginalFilename());
        	bill.setPhotos(fileName);
        	Bill savedBill= billService.save(bill);
        	String upload= "images/"+bill.getId();
        	
        	FileUploadUtil.saveFile(upload, fileName, multiPartFile);
        }
        else
        {
        	if(bill.getPhotos().isEmpty())
        	{
        		bill.setPhotos(null);
        		billService.save(bill);
        	}
        }
        billService.addBill(bill);
        return "redirect:/bills";
    }

    @GetMapping("/editbill/{id}")
    public String editBill(@PathVariable int id, Model model, HttpSession session) {
    	if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        List<Seller> sellers = sellerService.getAllSellers();
        model.addAttribute("sellers", sellers);
    	Bill bill = billService.getBillById(id); 
        if (bill == null) {
            return "error"; 
        }
        model.addAttribute("bill", bill);
        return "editbill";
    }

    @PostMapping("/updatebill")
    public String updateBill(@ModelAttribute Bill bill, @RequestParam("image") MultipartFile multiPartFile) throws IOException {
        if (!multiPartFile.isEmpty()) {
            // Save the new image file
            String fileName = StringUtils.cleanPath(multiPartFile.getOriginalFilename());
            bill.setPhotos(fileName);
            // Save the file to the appropriate location
            String upload = "images/" + bill.getId();
            FileUploadUtil.saveFile(upload, fileName, multiPartFile);
        }
        // Update the bill details
        billService.updateBill(bill);
        return "redirect:/bills"; // Redirect to the bills page after updating
    }


    @GetMapping("/deletebill/{id}")
    public String deleteBill(@PathVariable int id) {
        billService.deleteBill(id);
        return "redirect:/bills";
    }
    
    @GetMapping("/download/{id}")
    public void downloadBillImage(@PathVariable int id, HttpServletResponse response) {
        // Retrieve the bill by ID
        Bill bill = billService.getBillById(id);

        if (bill != null && bill.getPhotos() != null) {
            // Get the file name
            String fileName = bill.getPhotos();

            try {
                // Set the content type and attachment header
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

                // Stream the file content to the response
                InputStream inputStream = new FileInputStream("images/" + id + "/" + fileName);
                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
                inputStream.close();
            } catch (IOException e) {
                // Handle exceptions
                e.printStackTrace();
            }
        } else {
            // Handle case where image is not found or bill doesn't exist
            try {
                response.sendRedirect("/error"); // Redirect to the error page
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
