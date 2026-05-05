package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final AccountRepository accountRepository;

	public CustomUserDetailsService(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		Account account = accountRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Account not found"));
		
		return account;
		/*
		return new User(
				account.getUsername(),
				account.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(account.getRole()))
			);*/
	}

}
