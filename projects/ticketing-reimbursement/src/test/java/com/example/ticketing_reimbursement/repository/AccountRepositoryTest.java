package com.example.ticketing_reimbursement.repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.ticketing_reimbursement.entity.Account;

@DataJpaTest
public class AccountRepositoryTest {
     @Autowired
    private AccountRepository accountRepository;

    /**
     * Test saving an account to the database.
     */
    @Test
    public void testSaveAccount() {
        // Arrange
        Account account = new Account("testUser", "password123");
        account.setRole("employee");

        // Act
        Account savedAccount = accountRepository.save(account);

        // Assert
        assertNotNull(savedAccount.getAccountId(), "Account ID should be generated");
        assertEquals("testUser", savedAccount.getUsername(), "Username should match");
        assertEquals("password123", savedAccount.getPassword(), "Password should match");
        assertEquals("employee", savedAccount.getRole(), "Role should match");
    }

    /**
     * Test finding an account by username when it exists.
     */
    @Test
    public void testFindAccountByUsername() {
        // Arrange
        Account account = new Account("uniqueUser", "securePass");
        account.setRole("manager");
        accountRepository.save(account);

        // Act
        Optional<Account> retrievedAccount = accountRepository.findAccountByUsername("uniqueUser");

        // Assert
        assertTrue(retrievedAccount.isPresent(), "Account should be found");
        assertEquals("uniqueUser", retrievedAccount.get().getUsername(), "Username should match");
        assertEquals("securePass", retrievedAccount.get().getPassword(), "Password should match");
        assertEquals("manager", retrievedAccount.get().getRole(), "Role should match");
    }

    /**
     * Test finding an account by username when it does not exist.
     */
    @Test
    public void testFindAccountByUsername_NotFound() {
        // Act
        Optional<Account> retrievedAccount = accountRepository.findAccountByUsername("nonExistentUser");

        // Assert
        assertFalse(retrievedAccount.isPresent(), "Account should not be found");
    }

    /**
     * Test saving an account with a duplicate username, expecting an exception.
     */
    @Test
    public void testSaveAccount_DuplicateUsername() {
        // Arrange
        Account account1 = new Account("duplicateUser", "pass123");
        Account account2 = new Account("duplicateUser", "pass456");

        // Act
        accountRepository.save(account1);
        accountRepository.flush();

        // Assert
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            accountRepository.save(account2);
            accountRepository.flush();
        });

        assertTrue(exception.getMessage().contains("could not execute statement"), 
        "Exception message should indicate a database constraint violation");
    }
    
}
