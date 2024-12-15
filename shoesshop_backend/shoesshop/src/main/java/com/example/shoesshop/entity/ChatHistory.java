package com.example.shoesshop.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ChatHistory")
public class ChatHistory implements Serializable {
    @Column(name = "chatId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatId;

    @Lob
    @Column(name = "message", nullable = true)
    private String message;

    @Lob
    @Column(name = "response", nullable = true)
    private String response;

    @Column(name = "createDate", updatable = false)
    @CreationTimestamp
    private LocalDate createDate;

    @ManyToOne()
    @JoinColumn(name = "userId", nullable = true)
    private Account customer;

    public ChatHistory() {
    }

    public ChatHistory(int chatId, String message, String response, LocalDate createDate, Account customer) {
        this.chatId = chatId;
        this.message = message;
        this.response = response;
        this.createDate = createDate;
        this.customer = customer;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }
}
