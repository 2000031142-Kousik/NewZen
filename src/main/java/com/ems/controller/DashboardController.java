package com.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ems.entity.Employee;
import com.ems.service.EmpService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

	@GetMapping("/")
	public String landing()
	{
		return "landing";
	}

	@GetMapping("/dashboard")
    public String dashboard(HttpSession session, HttpServletResponse response) {
        // Check if user is logged in, if not, redirect to login page
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
        return "dashboard";
    }
	
//	@GetMapping("/salary")
//	public String goToSalary()
//	{
//		return "salary";
//	}
}
