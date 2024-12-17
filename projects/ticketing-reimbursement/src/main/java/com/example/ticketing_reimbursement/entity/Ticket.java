package com.example.ticketing_reimbursement.entity;

import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;

@Entity
@Table
public class Ticket {
     /**
     * An id for this message which will be automatically generated by the database.
     */
     @Column (name="ticketId")
     @Id 
     @GeneratedValue
    private Integer ticketId;
    /**
     * The id for the user who has posted this message. We will assume that this is provided by the front-end of this
     * application.
     */
    
    private Integer postedBy;
    /**
     * The text for this message- eg "this is my first post!". Must be not blank and under 255 characters
     */
  
    private String description;

    private String status;
    private Integer amount;

    public Ticket(){
    }


    public Ticket(Integer postedBy, String description,Integer amount)  {
        this.postedBy = postedBy;
        this.description = description;
        this.amount = amount;
        this.status = "Pending";

        
    }
    /**
     * Whem retrieving a message from the database, all fields will be needed. In that case, a constructor with all
     * fields is needed.
     * @param messageId
     * @param postedBy
     * @param messageText
     * @param timePostedEpoch
     */
    public Ticket(Integer ticketId, Integer postedBy, String description,Integer amount,String status) {
        this.ticketId = ticketId;
        this.postedBy = postedBy;
        this.description = description;
        this.amount = amount;
        this.status = status;

    }

    public Integer getTicketId() {
        return ticketId;
    }


    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return postedBy
     */
    public Integer getPostedBy() {
        return postedBy;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param postedBy
     */
    public void setPostedBy(Integer postedBy) {
        this.postedBy = postedBy;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return messageText
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String descriptionText) {
        this.description = descriptionText;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;


    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;


    }
    
}
