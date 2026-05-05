package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
	
	List<Log> findByFromAccountIdOrToAccountIdOrderByTimeDesc(Long fromId, Long toId);
	
	List<Log> findAllByOrderByTimeDesc();

}
