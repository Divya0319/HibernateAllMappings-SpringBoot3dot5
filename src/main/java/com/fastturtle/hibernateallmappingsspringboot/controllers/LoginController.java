package com.fastturtle.hibernateallmappingsspringboot.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "returnTo", required = false) String returnTo,
                            Model model, HttpSession session) {
        System.out.println("Login GET called. returnTo = " + returnTo);
        model.addAttribute("returnTo", returnTo);
        if (returnTo != null) {
            session.setAttribute("redirectAfterLogin", returnTo);
        }
        return "login";
    }
}
