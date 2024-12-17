package com.example.ticketing_reimbursement.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class AccountTest {
     /**
     * Test the no-argument constructor and default field values.
     */
    @Test
    public void testNoArgConstructor() {
        // Arrange & Act
        Account account = new Account();

        // Assert
        assertNull(account.getAccountId(), "Account ID should be null");
        assertNull(account.getUsername(), "Username should be null");
        assertNull(account.getPassword(), "Password should be null");
        assertNull(account.getRole(), "Role should be null");
    }

    /**
     * Test the constructor with username and password parameters.
     */
    @Test
    public void testConstructorWithUsernameAndPassword() {
        // Arrange
        String username = "testUser";
        String password = "password123";

        // Act
        Account account = new Account(username, password);

        // Assert
        assertNull(account.getAccountId(), "Account ID should be null");
        assertEquals(username, account.getUsername(), "Username should match");
        assertEquals(password, account.getPassword(), "Password should match");
    }

    /**
     * Test the constructor with all fields.
     */
    @Test
    public void testConstructorWithAllFields() {
        // Arrange
        Integer accountId = 1;
        String username = "testUser";
        String password = "password123";

        // Act
        Account account = new Account(accountId, username, password);

        // Assert
        assertEquals(accountId, account.getAccountId(), "Account ID should match");
        assertEquals(username, account.getUsername(), "Username should match");
        assertEquals(password, account.getPassword(), "Password should match");
    }

    /**
     * Test the setters and getters for all fields.
     */
    @Test
    public void testSettersAndGetters() {
        // Arrange
        Account account = new Account();

        Integer accountId = 10;
        String username = "newUser";
        String password = "newPass123";
        String role = "manager";

        // Act
        account.setAccountId(accountId);
        account.setUsername(username);
        account.setPassword(password);
        account.setRole(role);

        // Assert
        assertEquals(accountId, account.getAccountId(), "Account ID should match");
        assertEquals(username, account.getUsername(), "Username should match");
        assertEquals(password, account.getPassword(), "Password should match");
        assertEquals(role, account.getRole(), "Role should match");
    }

    /**
     * Test the toString method to ensure it returns the correct format.
     */
    @Test
    public void testToString() {
        // Arrange
        Integer accountId = 5;
        String username = "toStringTest";
        String password = "testPass";
        String role = "employee";
        
        Account account = new Account(accountId, username, password);
        account.setRole(role);

        // Act
        String expected = "Account{accountId=5, username='toStringTest', password='testPass', role='employee'}";
        String actual = account.toString();

        // Assert
        assertEquals(expected, actual, "toString output should match");
    }
    
}
