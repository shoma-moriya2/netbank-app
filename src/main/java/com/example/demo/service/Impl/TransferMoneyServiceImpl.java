package com.example.demo.service.Impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Log;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.LogRepository;
import com.example.demo.service.TransferMoneyService;

@Service
@Transactional
public class TransferMoneyServiceImpl implements TransferMoneyService {

    private final LogRepository logRepository;

    private final BankAccountRepository bankAccountRepository;

    TransferMoneyServiceImpl(BankAccountRepository bankAccountRepository, LogRepository logRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.logRepository = logRepository;
    }

	@Override
	public void transfer(BigDecimal amount, String fromNumber, String toNumber){
		// TODO Auto-generated method stub
		BankAccount fromAccount = bankAccountRepository.findByNumber(fromNumber).orElseThrow();
		fromAccount.setDeposit(fromAccount.getDeposit().subtract(amount));
		bankAccountRepository.save(fromAccount);

		BankAccount toAccount = bankAccountRepository.findByNumber(toNumber).orElseThrow();
		toAccount.setDeposit(toAccount.getDeposit().add(amount));
		bankAccountRepository.save(toAccount);
		
		Log log = new Log(amount, fromAccount.getAccount(), toAccount.getAccount());
		logRepository.save(log);

	}

}
