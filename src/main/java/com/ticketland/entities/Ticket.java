package com.ticketland.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "userAccountId")
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;


    public Ticket() {
    }

    public Ticket(UserAccount userAccount, Event event) {
        this.id = generateRandomTicketNumber();
        this.userAccount = userAccount;
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public UserAccount getUser() {
        return userAccount;
    }

    public Event getEvent() {
        return event;
    }


    private String generateRandomTicketNumber() {
        return "TICKET_" + UUID.randomUUID();
    }
}
