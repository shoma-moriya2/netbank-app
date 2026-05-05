package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Optional<Account> findByUsername(String username);
	
	List<Account> findByRole(String role);
	
	boolean existsByUsernameAndDeletedFalse(String username);
	
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.deleted = true WHERE a.id = :id")
    int withdraw(@Param("id") Long id);

}
