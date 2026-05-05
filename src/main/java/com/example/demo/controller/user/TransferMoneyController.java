package com.example.demo.controller.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.TransferInfo;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.service.TransferMoneyService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/transferMoney")
public class TransferMoneyController {

    private final TransferMoneyService transferMoneyServiceImpl;

    private final BankAccountRepository bankAccountRepository;

    private final AccountRepository accountRepository;

    TransferMoneyController(AccountRepository accountRepository, BankAccountRepository bankAccountRepository, TransferMoneyService transferMoneyServiceImpl) {
        this.accountRepository = accountRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.transferMoneyServiceImpl = transferMoneyServiceImpl;
    }
   
	@ModelAttribute("transferInfo")
	public TransferInfo setup() {
		return new TransferInfo();
	}
	
	@GetMapping("/input")
	public String transferMoneyPage(Model model) {
		return "user/transferMoney/input";
	}

	@PostMapping("/input")
	public String postTransferInfo(@Valid TransferInfo info, BindingResult bindingResult, Model model, @AuthenticationPrincipal Account account, RedirectAttributes red) {

		if (account.getBankAccount().getNumber().equals(info.getToNumber())) {
	        
	        bindingResult.rejectValue(
	                "toNumber", // フィールド名
	                "to.is.myself",  // エラーコード（任意）
	                "ご自身の口座への送金はご遠慮ください。" // 表示するメッセージ
	        );
	    }
		
		if (!bankAccountRepository.existsByNumberAndAccount_DeletedFalse(info.getToNumber())) {
	        
	        bindingResult.rejectValue(
	                "toNumber", // フィールド名
	                "to.not.exists",  // エラーコード（任意）
	                "ご指定の口座番号は現在利用されておりません。" // 表示するメッセージ
	        );
	    }
		
		long id = accountRepository.findByUsername(account.getUsername()).orElseThrow().getId();
		
		if (info.getAmount().compareTo(bankAccountRepository.findById(id).orElseThrow().getDeposit()) > 0) {
	        
	        bindingResult.rejectValue(
	                "amount", // フィールド名
	                "not.enough.deposit",  // エラーコード（任意）
	                "残高が不足しております。" // 表示するメッセージ
	        );
	    }

		if(bindingResult.hasErrors()) {
			return "user/transferMoney/input";
		}
		
		String toName = bankAccountRepository.findByNumber(info.getToNumber()).orElseThrow().getAccount().getUsername();
		info.setToName(toName);
		
		red.addFlashAttribute("transferInfo", info);
		return "redirect:/user/transferMoney/confirm";
	}

	@GetMapping("/confirm")
	public String transferMoneyConfirmPage() {
		
		return "user/transferMoney/confirm";
	}
	
	@PostMapping("/confirm")
	public String transferMoneyExecute(TransferInfo info, @AuthenticationPrincipal Account account) {
		
		info.setFromNumber(account.getBankAccount().getNumber());

		transferMoneyServiceImpl.transfer(info.getAmount(), info.getFromNumber(), info.getToNumber());
		
		return "redirect:/user/transferMoney/complete";
	}

	@GetMapping("/complete")
	public String transferMoneyCompletePage() {
		

		
		return "user/transferMoney/complete";
	}


}
