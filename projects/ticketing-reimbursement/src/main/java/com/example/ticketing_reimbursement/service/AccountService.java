package com.example.ticketing_reimbursement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketing_reimbursement.entity.Account;
import com.example.ticketing_reimbursement.entity.Ticket;
import com.example.ticketing_reimbursement.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) throws Exception {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new Exception("Username cannot be blank");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new Exception("Password must be at least 4 characters long");
        }
        if (accountRepository.findAccountByUsername(account.getUsername()).isPresent()) {
            throw new Exception("Account with this username already exists");
        }
        account.setRole("employee");
        return accountRepository.save(account);
    }

    public Account verifyAccount(Account account) throws Exception {
        Optional<Account> existingAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (existingAccount.isPresent()) {
            String pass = existingAccount.get().getPassword();
            if (pass.equals(account.getPassword())) {
                return existingAccount.get();
            } else {
                throw new Exception("Wrong Credentials");
            }

        } else {
            throw new Exception("Account Not Found");
        }

    }
     public List<Account> getAllAccountsfromRole(String role) throws Exception{
      
        return accountRepository.findByRole(role);
    }

    public Account promoteToManager(int  accountId) throws Exception {
        Optional<Account> existingAccount = accountRepository.findById(accountId);
        if (existingAccount.isPresent()) {
            existingAccount.get().setRole("manager");
            return accountRepository.save(existingAccount.get());
        } else {
            throw new Exception("Account Not Found");
        }

    }

}
