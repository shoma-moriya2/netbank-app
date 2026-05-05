package com.example.demo.entity;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class TransferInfo {
	
	@DecimalMin(value="1.0", message="金額は１以上を指定してください")
	@NotNull
	private BigDecimal amount;
	
	@Pattern(regexp = "\\d+", message="口座を正しく入力してください")
	@NotBlank
	private String fromNumber ="0";
	
	private String fromName;
	
	@Pattern(regexp = "\\d+", message="口座を正しく入力してください")
	@NotBlank
	private String toNumber;

	private String toName;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getFromNumber() {
		return fromNumber;
	}

	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToNumber() {
		return toNumber;
	}

	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}



}
