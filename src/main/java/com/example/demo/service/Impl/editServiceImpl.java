package com.example.demo.service.Impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Account;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Log;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.LogRepository;
import com.example.demo.service.editService;

@Service
@Transactional
public class editServiceImpl implements editService {

    private final LogRepository logRepository;

    private final BankAccountRepository bankAccountRepository;


    editServiceImpl(BankAccountRepository bankAccountRepository, LogRepository logRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.logRepository = logRepository;
    }
	

	@Override
	public void addDeposit(long userId, long adminId, BigDecimal amount){
		// TODO Auto-generated method stub
		
		BankAccount bankAccount = bankAccountRepository.findById(userId).orElseThrow();
		bankAccount.setDeposit(bankAccount.getDeposit().add(amount));
		bankAccountRepository.save(bankAccount);
		
		Log log = new Log(amount, new Account(adminId), new Account(userId));
		logRepository.save(log);

	}

}
