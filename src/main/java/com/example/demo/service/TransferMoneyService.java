package com.example.demo.service;

import java.math.BigDecimal;

public interface TransferMoneyService {
	
	public void transfer(BigDecimal amount, String fromNumber, String toNumber);

}
