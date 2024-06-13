package com.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ems.entity.Employee;
import com.ems.service.EmpService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class EmpController {
	
	@Autowired
	private EmpService service;

	@GetMapping("/employees")
	public String home(Model m, HttpSession session, HttpServletResponse response) {
		if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
	    List<Employee> emp = service.getAllEmp();
	    m.addAttribute("emp", emp);
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
	    return "employees";
	}

	@GetMapping("/addemp")
	public String addEmpForm(HttpSession session, HttpServletResponse response) {
		if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
		return "addemp";
	}
	
	@PostMapping("/empregister")
	public String empRegister(@ModelAttribute Employee e, HttpSession session) {
		System.out.println(e);
		service.addEmp(e);
		return "redirect:/employees";
	}
	
	@GetMapping("/editemployee/{id}")
	public String editEmp(@PathVariable int id, Model m, HttpSession session, HttpServletResponse response) {
		if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
		Employee e = service.getEmpById(id);
		m.addAttribute("emp", e);
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
		return "editemployee";
	}
	
	@PostMapping("/updateemployee")
	public String updateEmp(@ModelAttribute Employee e, HttpSession session) {
		service.updateEmp(e);
		return "redirect:/employees";
	}
	
	@GetMapping("/deleteemployee/{id}")
	public String deleteEmp(@PathVariable int id) {
		service.deleteEmp(id);
		return "redirect:/employees";
	}
}
