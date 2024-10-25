package com.ticketland.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "user_accounts")
public class UserAccount {
    @Id
    private String id;

    private double balance;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public UserAccount() {
    }

    public UserAccount(String id, double balance, User user) {
        this.id = id;
        this.balance = balance;
        this.user = user;
    }

    public String getId() {
        return id;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

}
