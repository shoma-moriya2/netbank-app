package com.example.demo.service.Impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.demo.repository.BankAccountRepository;
import com.example.demo.service.GenerateNumberService;

@Service
public class GenerateNumberServiceImpl implements GenerateNumberService {

	private final BankAccountRepository bankAccountRepository;
	
	public GenerateNumberServiceImpl(BankAccountRepository bankAccountRepository) {
		super();
		this.bankAccountRepository = bankAccountRepository;
	}

	@Override
	public String generateBankAccountNumber() {
		// TODO Auto-generated method stub
		String number = null;
		
		do{number = String.format("%04d", new Random().nextInt(10000));}
				
		while(bankAccountRepository.existsByNumberAndAccount_DeletedFalse(number));
		
		return number;
	}

}
