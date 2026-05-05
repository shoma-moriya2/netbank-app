package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Profile {
	
	@Id
	private long id;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "id")
	private Account account;
	
	@NotBlank
	@Pattern(regexp="\\d{7}", message="郵便番号は７桁で記入してください")
	private String zipcode;
	
	@NotBlank
	private String address;

	@NotBlank
	private String chou;
	
	@Email
	@NotBlank
	private String email;
	
	@Pattern(regexp = "0\\d{9,10}", message="電話番号を正しく入力してください")
	private String phone;
	

	public String getChou() {
		return chou;
	}

	public void setChou(String chou) {
		this.chou = chou;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



}
