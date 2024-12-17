package com.example.ticketing_reimbursement.service;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.ticketing_reimbursement.entity.Account;
import com.example.ticketing_reimbursement.repository.AccountRepository;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        account = new Account();
        account.setUsername("testUser");
        account.setPassword("password123");
    }

    /*** Tests for registerAccount ***/

    @Test
    public void testRegisterAccount_Success() throws Exception {
        // Arrange
        when(accountRepository.findAccountByUsername(account.getUsername())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Act
        Account savedAccount = accountService.registerAccount(account);

        // Assert
        assertNotNull(savedAccount);
        assertEquals("employee", savedAccount.getRole());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testRegisterAccount_BlankUsername() {
        // Arrange
        account.setUsername("");

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> accountService.registerAccount(account));
        assertEquals("Username cannot be blank", exception.getMessage());
    }

    @Test
    public void testRegisterAccount_ShortPassword() {
        // Arrange
        account.setPassword("123");

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> accountService.registerAccount(account));
        assertEquals("Password must be at least 6 characters long", exception.getMessage());
    }

    @Test
    public void testRegisterAccount_UsernameAlreadyExists() {
        // Arrange
        when(accountRepository.findAccountByUsername(account.getUsername())).thenReturn(Optional.of(account));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> accountService.registerAccount(account));
        assertEquals("Account with this username already exists", exception.getMessage());
    }

    /*** Tests for verifyAccount ***/

    @Test
    public void testVerifyAccount_Success() throws Exception {
        // Arrange
        when(accountRepository.findAccountByUsername(account.getUsername())).thenReturn(Optional.of(account));

        // Act
        Account verifiedAccount = accountService.verifyAccount(account);

        // Assert
        assertNotNull(verifiedAccount);
        assertEquals(account.getUsername(), verifiedAccount.getUsername());
        verify(accountRepository, times(1)).findAccountByUsername(account.getUsername());
    }

    @Test
    public void testVerifyAccount_WrongPassword() {
        // Arrange
        Account storedAccount = new Account();
        storedAccount.setUsername("testUser");
        storedAccount.setPassword("correctPassword");
        when(accountRepository.findAccountByUsername(account.getUsername())).thenReturn(Optional.of(storedAccount));

        account.setPassword("wrongPassword");

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> accountService.verifyAccount(account));
        assertEquals("Wrong Credentials", exception.getMessage());
    }

    @Test
    public void testVerifyAccount_AccountNotFound() {
        // Arrange
        when(accountRepository.findAccountByUsername(account.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> accountService.verifyAccount(account));
        assertEquals("Account Not Found", exception.getMessage());
    }
}