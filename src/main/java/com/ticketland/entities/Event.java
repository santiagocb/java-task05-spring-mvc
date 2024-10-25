package com.ticketland.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "events")
public class Event {
    @Id
    private String id;

    private String name;

    private String place;

    private LocalDate date;

    private double ticketPrice;

    public Event() {
    }

    public Event(String id, String name, String place, LocalDate date, double ticketPrice) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.date = date;
        this.ticketPrice = ticketPrice;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getPlace() {
        return place;
    }


    public LocalDate getDate() {
        return date;
    }


    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}

