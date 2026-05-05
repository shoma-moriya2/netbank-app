package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@GetMapping("/welcome")
	public String getWelcome() {
		return "welcome";
	}
	
	@GetMapping("/withdrawal/complete")
	public String withdrawalComplete() {
		return "user/withdrawal/complete";
	}



}
