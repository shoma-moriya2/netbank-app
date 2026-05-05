package com.example.demo.controller.user;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user/withdrawal")
public class WithdrawalController {

    private final AccountRepository accountRepository;


    WithdrawalController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
	

	@GetMapping("/confirm")
	public String withdrawalPage() {
		return "user/withdrawal/confirm";
	}
	
	@PostMapping("/confirm")
	public String postWithdrawal(@AuthenticationPrincipal Account account, 
			HttpServletRequest req, HttpServletResponse res, Authentication auth) {
		
		accountRepository.withdraw(account.getId());
		
		new SecurityContextLogoutHandler().logout(req, res, auth);
		
		return "redirect:/withdrawal/complete";
	}
	
	


}
