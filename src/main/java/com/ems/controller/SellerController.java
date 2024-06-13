package com.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ems.entity.Seller;
import com.ems.service.SellerService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class SellerController {
    
    @Autowired
    private SellerService service;
    
    @GetMapping("/sellers")
    public String sellersHome(Model m, HttpSession session, HttpServletResponse response) {
    	if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        List<Seller> sellers = service.getAllSellers();
        m.addAttribute("sellers", sellers);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
        return "sellers"; // Assuming sellers.html is the page to display sellers
    }

    @GetMapping("/addseller")
    public String addSellerForm(HttpSession session, HttpServletResponse response) {
    	if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
        return "addseller";
    }
    
    @PostMapping("/sellerregister")
    public String sellerRegister(@ModelAttribute Seller seller, HttpSession session) {
        System.out.println(seller);
        service.addSeller(seller);
        return "redirect:/sellers"; // Redirecting to the list of sellers after registration
    }
    
    @GetMapping("/editseller/{id}")
    public String editSeller(@PathVariable int id, Model m, HttpSession session, HttpServletResponse response) {
    	if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        Seller seller = service.findById(id);
        m.addAttribute("seller", seller);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
        return "editseller";
    }
    
    @PostMapping("/updateseller")
    public String updateSeller(@ModelAttribute Seller seller, HttpSession session) {
        service.updateSeller(seller);
        return "redirect:/sellers"; // Redirecting to the list of sellers after update
    }
    
    @GetMapping("/deleteseller/{id}")
    public String deleteSeller(@PathVariable int id) {
        service.deleteSeller(id);
        return "redirect:/sellers"; // Redirecting to the list of sellers after deletion
    }
}
