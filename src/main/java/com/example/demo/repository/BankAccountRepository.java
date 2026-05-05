package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
	
	boolean existsByNumberAndAccount_DeletedFalse(String number);
	
	Optional<BankAccount> findByNumber(String number);

}
