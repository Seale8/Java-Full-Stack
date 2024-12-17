package com.example.ticketing_reimbursement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticketing_reimbursement.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    Optional<Account> findAccountByUsername(String username);
    
}
