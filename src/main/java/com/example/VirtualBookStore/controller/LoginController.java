package com.example.VirtualBookStore.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden // from springdoc-openapi
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/{customerId}")
    public String login(@PathVariable Long customerId, HttpSession session) {
        session.setAttribute("customerId", customerId);
        return "🔐 Logged in as customer " + customerId;
    }
//	@GetMapping("/{customerId}")
//	public String loginViaGet(@PathVariable Long customerId, HttpSession session) {
//	    session.setAttribute("customerId", customerId);
//	    return "🔐 Logged in as customer " + customerId + " (via GET)";
//	}

    @GetMapping
    public String checkLogin(HttpSession session) {
        Object customerId = session.getAttribute("customerId");
        return customerId != null
            ? "✅ Logged in as customer " + customerId
            : "❌ Not logged in";
    } 
    
}