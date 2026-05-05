package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Log;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.LogRepository;
import com.example.demo.service.editService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final LogRepository logRepository;

	private final editService editServiceImpl;

    private final BankAccountRepository bankAccountRepository;

    private final AccountRepository accountRepository;

    AdminController(AccountRepository accountRepository, BankAccountRepository bankAccountRepository, editService editServiceImpl, LogRepository logRepository) {
		this.editServiceImpl = editServiceImpl;
		this.accountRepository = accountRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.logRepository = logRepository;
    }

	@GetMapping("/home")
	public String homePage() {
		return "admin/home";
	}

    
	@GetMapping("/accounts")
	public String accountsPage(Model model) {
		
		List<Account> accountsList = accountRepository.findByRole("ROLE_USER");
			
		model.addAttribute("accountsList", accountsList);
		return "admin/accounts";
	}
	
	@GetMapping("/editDeposit")
	public String editDepositPage(@RequestParam("id") long id, Model model) {
		
		BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow();
		model.addAttribute("bankAccount", bankAccount);
		
		return "admin/editDeposit";
	}
	
	@PostMapping("/editDeposit")
	public String editDepositPage(@RequestParam("id") long id, @RequestParam("amount") BigDecimal amount, @AuthenticationPrincipal Account account) {
		
		editServiceImpl.addDeposit(id, account.getId(), amount);
		
		return "redirect:/admin/editDeposit?id=" + id;
	}
	
	@GetMapping("/logs")
	public String logsPage(Model model) {
		
		List<Log> logsList = logRepository.findAllByOrderByTimeDesc();
			
		model.addAttribute("logsList", logsList);
		return "admin/logs";
	}



}
