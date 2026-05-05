package com.example.demo.service.Impl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Account;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Profile;
import com.example.demo.entity.Registration;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.service.GenerateNumberService;
import com.example.demo.service.RegisterService;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private final BankAccountRepository bankAccountRepository;
    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenerateNumberService generateNumberService;

	public RegisterServiceImpl(BankAccountRepository bankAccountRepository, ProfileRepository profileRepository,
			AccountRepository accountRepository, PasswordEncoder passwordEncoder,
			GenerateNumberService generateNumberService) {
		super();
		this.bankAccountRepository = bankAccountRepository;
		this.profileRepository = profileRepository;
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
		this.generateNumberService = generateNumberService;
	}

	@Override
	public void register(Registration registration) {
		// TODO Auto-generated method stub
		
		Account account = registration.getAccount();
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setRole("ROLE_USER");
		accountRepository.save(account);
		
		Profile profile = registration.getProfile();
		profile.setAccount(account);
		profileRepository.save(profile);
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setAccount(account);
		bankAccount.setNumber(generateNumberService.generateBankAccountNumber());
		bankAccountRepository.save(bankAccount);
		

		
	}

}
