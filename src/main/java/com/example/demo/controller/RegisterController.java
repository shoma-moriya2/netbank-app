package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Registration;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.RegisterService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AccountRepository accountRepository;

    private final RegisterService registerService;


    RegisterController(RegisterService registerService, AccountRepository accountRepository) {
        this.registerService = registerService;
        this.accountRepository = accountRepository;
    }


	@ModelAttribute("registration")
	public Registration setup() {
		return new Registration();
	}
	
	
	@GetMapping("/input")
	public String getInput(Model model) {
		return "register/input";
	}
	
	@PostMapping("/input")
	public String postInput(@Valid Registration registration, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
			
		if (accountRepository.existsByUsernameAndDeletedFalse(registration.getAccount().getUsername())) {
	        // account.username フィールドにエラーを追加
	        bindingResult.rejectValue(
	                "account.username", // フィールド名
	                "username.allready.exists",  // エラーコード（任意）
	                "このユーザー名はすでに使われています" // 表示するメッセージ
	        );
	    }
		
		if(bindingResult.hasErrors()) {
			return "register/input";
		}
				
		redirectAttributes.addFlashAttribute("registration", registration);		
		return "redirect:/register/confirm";
	
	}
	
	@GetMapping("/confirm")
	public String getConfirm(Registration registration) {
		
		if(registration.getAccount() == null) {
			return "redirect:/register/input";
		}
		return "register/confirm";
	}
	
	@PostMapping("/confirm")
	public String postConfirm(Registration registration, RedirectAttributes redirectAttributes) {
		
		try{
			registerService.register(registration);
		}catch(RuntimeException e) {
			redirectAttributes.addFlashAttribute("errMsg", "*登録処理時にエラーが発生しました。恐れ入りますがもう一度お試しください。");
			return "redirect:/register/input";
		}
		
		return "redirect:/register/complete";
	}
	
	@GetMapping("/complete")
	public String getComplete() {
		return "register/complete";
	}




}
