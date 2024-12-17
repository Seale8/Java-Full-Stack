package com.example.ticketing_reimbursement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table
public class Account {

    /**
     * Unique identifier for the Account entity.
     * It is the primary key in the database and is generated automatically.
     */
    @Column(name="accountId")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;

    /**
     * The username for this account.
     * It must be unique and not blank.
     *
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * The password for this account.
     * It must have more than 6 characters for security purposes.
     */
    private String password;

     /**
     * The role of the account, which specifies user access level.
     * For example: "employee" or "manager".
     */
    private String role;

     /**
     * Default constructor required for JPA.
     * Initializes an empty Account object.
     */
    public Account(){

    }

    /**
     * Constructor for creating a new Account object when accountId is not specified.
     * This constructor is useful when posting a new Account where the ID is generated by the database.
     *
     * @param username The username for the new account.
     * @param password The password for the new account.
     */
    public Account(String username, String password){
        this.username = username;
        this.password = password;

    }

    /**
     * Constructor for creating an Account object with all fields.
     * This constructor is useful when retrieving an account from the database.
     *
     * @param accountId The unique ID for the account.
     * @param username  The username of the account.
     * @param password  The password for the account.
     */
    public Account(Integer accountId, String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
    }

     /**
     * Gets the unique ID for this account.
     *
     * @return The accountId.
     */
    public Integer getAccountId() {
        return accountId;
    }

  /**
     * Sets the unique ID for this account.
     *
     * @param accountId The new account ID to be set.
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    /**
     * Gets the username for this account.
     *
     * @return The username of the account.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the username for this account.
     *
     * @param username The new username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Gets the password for this account.
     *
     * @return The password of the account.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password for this account.
     *
     * @param password The new password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
     /**
     * Gets the role for this account.
     *
     * @return The role of the account.
     */
    public String getRole(){
        return role;
    }
    /**
     * Sets the role for this account.
     *
     * @param role The new role to be set (e.g., "employee" or "manager").
     */
    public void setRole(String role){
        this.role = role;
    }

     
    /** 
     *  @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    
}
