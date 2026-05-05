package com.example.demo.entity;

import jakarta.validation.Valid;

public class Registration {
	
	@Valid
	private Account account;
	@Valid
	private Profile profile;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	

}
