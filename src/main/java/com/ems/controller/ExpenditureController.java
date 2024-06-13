package com.ems.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ems.entity.EmpExpenditure;
import com.ems.entity.Employee;
import com.ems.entity.Seller;
import com.ems.entity.SellerExpenditure;
import com.ems.entity.OtherExpenditure;
import com.ems.service.EmpService;
import com.ems.service.SellerExpenditureService;
import com.ems.service.SellerService;
import com.ems.service.OtherExpenditureService;
import com.ems.service.EmpExpenditureService;

@Controller
public class ExpenditureController {
    
    @Autowired
    private EmpService employeeService;
    
    @Autowired
    private EmpExpenditureService expenditureService;
    
    @Autowired
    private SellerService sellerService;
    
    @Autowired
    private SellerExpenditureService sellerExpenditureService;

    @Autowired
    private OtherExpenditureService otherExpenditureService;
    
    @GetMapping("/addexpenditures")
    public String expenditures(Model model) {
        List<Employee> employees = employeeService.getAllEmp();
        model.addAttribute("employees", employees);
        List<Seller> sellers = sellerService.getAllSellers();
        model.addAttribute("sellers", sellers);
        return "addexpenditures";
    }
    @GetMapping("/expendituresreport")
    public String expendituresReport(Model model) {
        // Fetch data for each tab
        List<EmpExpenditure> empExpenditures = expenditureService.getAllEmpExpenditures();
        List<SellerExpenditure> sellerExpenditures = sellerExpenditureService.getAllSellerExpenditures();
        List<OtherExpenditure> otherExpenditures = otherExpenditureService.getAllOtherExpenditures();
        
        // Add the data to the model
        model.addAttribute("empExpenditures", empExpenditures);
        model.addAttribute("sellerExpenditures", sellerExpenditures);
        model.addAttribute("otherExpenditures", otherExpenditures);
        
        return "expendituresreport";
    }
    
    
    @PostMapping("/empexpenditures")
    public String submitExpenditureForm(@RequestParam("employeeId") int employeeId,
                                        @RequestParam("date") String date,
                                        @RequestParam("advanceAmount") double advanceAmount) {
        // Convert date string to LocalDate
        LocalDate expenditureDate = LocalDate.parse(date);
        
        // Create an Expenditure object
        EmpExpenditure expenditure = new EmpExpenditure();
        expenditure.setEmployee(employeeService.getEmpById(employeeId));
        expenditure.setExpenditureDate(expenditureDate);
        expenditure.setAdvanceAmount(advanceAmount);
        
        // Save the expenditure
        expenditureService.saveExpenditure(expenditure);
        
        // Redirect to a confirmation page or any other page as needed
        return "redirect:/addexpenditures";
    }
    
    
    @PostMapping("/sellerexpenditures")
    public String submitSellerExpenditureForm(@RequestParam("sellerId") int sellerId,
                                              @RequestParam("date") String date,
                                              @RequestParam("expenditureAmount") double expenditureAmount) {
        // Convert date string to LocalDate
        LocalDate expenditureDate = LocalDate.parse(date);
        
        // Retrieve the Seller by ID
        Seller seller = sellerService.findById(sellerId);
        
        // Create a SellerExpenditure object
        SellerExpenditure expenditure = new SellerExpenditure();
        expenditure.setSeller(seller);
        expenditure.setDate(expenditureDate);
        expenditure.setExpenditureAmount(expenditureAmount);
        
        // Save the expenditure
        sellerExpenditureService.saveExpenditure(expenditure);
        
        // Redirect to a confirmation page or any other page as needed
        return "redirect:/addexpenditures";
    }

    
    @GetMapping("/otherexpenditures")
    public String otherExpenditures(Model model) {
        List<OtherExpenditure> otherExpenditures = otherExpenditureService.getAllOtherExpenditures();
        model.addAttribute("otherExpenditures", otherExpenditures);
        return "otherexpenditures";
    }
    
    @PostMapping("/otherexpenditures")
    public String submitOtherExpenditureForm(@RequestParam("name") String name,
                                             @RequestParam("date") String date,
                                             @RequestParam("expenditureAmount") double expenditureAmount) {
        // Convert date string to LocalDate
        LocalDate expenditureDate = LocalDate.parse(date);
        
        // Create an OtherExpenditure object
        OtherExpenditure expenditure = new OtherExpenditure();
        expenditure.setName(name);
        expenditure.setDate(expenditureDate);
        expenditure.setExpenditureAmount(expenditureAmount);
        
        // Save the expenditure
        otherExpenditureService.saveExpenditure(expenditure);
        
        // Redirect to a confirmation page or any other page as needed
        return "redirect:/expenditures";
    }

    @PostMapping("/updateEmpExpenditures")
    public String updateEmpExpenditures(@RequestParam("empId") int empId,
                                        @RequestParam("empDate") String empDate,
                                        @RequestParam("empAmount") double empAmount) {
        // Convert date string to LocalDate
        LocalDate expenditureDate = LocalDate.parse(empDate);

        // Retrieve the EmpExpenditure record by ID
        EmpExpenditure expenditure = expenditureService.findById(empId);

        // Update the fields with the new values
        expenditure.setExpenditureDate(expenditureDate);
        expenditure.setAdvanceAmount(empAmount);

        // Save the updated expenditure record
        expenditureService.saveExpenditure(expenditure);

        // Redirect to a confirmation page or any other page as needed
        return "redirect:/editexpenditures";
    }

    @PostMapping("/updateSellerExpenditures")
    public String updateSellerExpenditures(@RequestParam("sellerId") int sellerId,
                                           @RequestParam("sellerDate") String sellerDate,
                                           @RequestParam("sellerAmount") double sellerAmount) {
        // Convert date string to LocalDate
        LocalDate expenditureDate = LocalDate.parse(sellerDate);

        // Retrieve the SellerExpenditure record by ID
        SellerExpenditure expenditure = sellerExpenditureService.findById(sellerId);

        // Update the fields with the new values
        expenditure.setDate(expenditureDate);
        expenditure.setExpenditureAmount(sellerAmount);

        // Save the updated expenditure record
        sellerExpenditureService.saveExpenditure(expenditure);

        // Redirect to a confirmation page or any other page as needed
        return "redirect:/editexpenditures";
    }

    @PostMapping("/updateOtherExpenditures")
    public String updateOtherExpenditures(@RequestParam("otherId") int otherId,
                                          @RequestParam("otherDate") String otherDate,
                                          @RequestParam("otherAmount") double otherAmount) {
        // Convert date string to LocalDate
        LocalDate expenditureDate = LocalDate.parse(otherDate);

        // Retrieve the OtherExpenditure record by ID
        OtherExpenditure expenditure = otherExpenditureService.findById(otherId);

        // Update the fields with the new values
        expenditure.setDate(expenditureDate);
        expenditure.setExpenditureAmount(otherAmount);

        // Save the updated expenditure record
        otherExpenditureService.saveExpenditure(expenditure);

        // Redirect to a confirmation page or any other page as needed
        return "redirect:/editexpenditures";
    }

    
    @GetMapping("/download-expenditures-report")
    public ResponseEntity<byte[]> downloadExpendituresReport() {
        List<EmpExpenditure> empExpenditures = expenditureService.getAllEmpExpenditures();
        List<SellerExpenditure> sellerExpenditures = sellerExpenditureService.getAllSellerExpenditures();
        List<OtherExpenditure> otherExpenditures = otherExpenditureService.getAllOtherExpenditures();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (OutputStreamWriter writer = new OutputStreamWriter(baos);) {
            // Write the EMP Expenditures sheet
            writer.write("\"Id\",\"Employee Name\",\"Expenditure Date\",\"Advance Amount\"\n");
            for (EmpExpenditure expenditure : empExpenditures) {
                writer.write(String.format("\"%d\",\"%s\",\"%s\",\"%.2f\"\n",
                        expenditure.getId(),
                        expenditure.getEmployee().getName(),
                        expenditure.getExpenditureDate(),
                        expenditure.getAdvanceAmount()));
            }
            writer.write("\n");

            // Write the Seller Expenditures sheet
            writer.write("\"Id\",\"Seller Name\",\"Expenditure Date\",\"Expenditure Amount\"\n");
            for (SellerExpenditure expenditure : sellerExpenditures) {
                Seller seller = expenditure.getSeller();
                writer.write(String.format("\"%d\",\"%s\",\"%s\",\"%.2f\"\n",
                        expenditure.getId(),
                        seller.getName(),
                        expenditure.getDate(),
                        expenditure.getExpenditureAmount()));
            }
            writer.write("\n");

            // Write the Other Expenditures sheet
            writer.write("\"Id\",\"Name\",\"Expenditure Date\",\"Expenditure Amount\"\n");
            for (OtherExpenditure expenditure : otherExpenditures) {
                writer.write(String.format("\"%d\",\"%s\",\"%s\",\"%.2f\"\n",
                        expenditure.getUid(),
                        expenditure.getName(),
                        expenditure.getDate(),
                        expenditure.getExpenditureAmount()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        byte[] csvBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("expenditures_report.csv").build());

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }



    @GetMapping("/editexpenditures")
    public String editExpenditures(Model model) {
        // Fetch data for each tab to display in editexpenditures.html
        List<Employee> employees = employeeService.getAllEmp();
        List<Seller> sellers = sellerService.getAllSellers();
        List<OtherExpenditure> otherExpenditures = otherExpenditureService.getAllOtherExpenditures();

        // Add the data to the model
        model.addAttribute("employees", employees);
        model.addAttribute("sellers", sellers);
        model.addAttribute("otherExpenditures", otherExpenditures);

        return "editexpenditures";
    }

}
