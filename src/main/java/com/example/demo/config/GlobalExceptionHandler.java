package com.example.demo.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	public String handleException(Exception ex, Model model) {
		
		ex.printStackTrace();
		
		model.addAttribute("errMsg", ex.getMessage());
		
		return "error";
	}

}
