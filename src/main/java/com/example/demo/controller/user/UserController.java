package com.example.demo.controller.user;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Account;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Log;
import com.example.demo.entity.Profile;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.LogRepository;
import com.example.demo.repository.ProfileRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    private final LogRepository logRepository;

    private final ProfileRepository profileRepository;

    private final BankAccountRepository bankAccountRepository;

    private final AccountRepository accountRepository;


    UserController(AccountRepository accountRepository, BankAccountRepository bankAccountRepository, ProfileRepository profileRepository, LogRepository logRepository) {
        this.accountRepository = accountRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.profileRepository = profileRepository;
        this.logRepository = logRepository;
    }
	

	@GetMapping("/home")
	public String getHome(Model model, @AuthenticationPrincipal Account account) {
		
		BankAccount bankAccount = bankAccountRepository.findById(account.getId()).orElseThrow();
		
		model.addAttribute("bankAccount", bankAccount);
		return "user/home";
	}
	
	@GetMapping("/profile")
	public String getProfile(Model model, @AuthenticationPrincipal Account account) {
		
		Profile profile = account.getProfile();
				
		model.addAttribute("profile", profile);
		return "user/profile";
	}

	@GetMapping("/log")
	public String logPage(Model model, @AuthenticationPrincipal Account account) {
		
		long id = account.getId();
		List<Log> log = logRepository.findByFromAccountIdOrToAccountIdOrderByTimeDesc(id, id);
				
		model.addAttribute("log", log);
		return "user/log";
	}

	

}
