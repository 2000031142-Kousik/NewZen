package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ems.entity.Login;
import com.ems.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        // Check if user is already logged in, if yes, redirect to dashboard
        if (session.getAttribute("username") != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        Login user = loginService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Set session attribute upon successful login
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return new ModelAndView("redirect:/dashboard");
        } else {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Invalid username or password");
            return modelAndView;
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate session and redirect to login page
        session.invalidate();
        return "redirect:/login";
    }
}
