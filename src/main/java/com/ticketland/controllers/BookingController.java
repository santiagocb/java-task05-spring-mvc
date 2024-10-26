package com.ticketland.controllers;

import com.ticketland.entities.Event;
import com.ticketland.entities.Ticket;
import com.ticketland.facades.BookingFacade;
import com.ticketland.services.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookingController {

    private final BookingFacade bookingFacade;

    public BookingController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/tickets")
    public String renderBookingForm(Model model) {
        List<Ticket> tickets = bookingFacade.getAllTickets();
        model.addAttribute("event", new Event());
        model.addAttribute("tickets", tickets);
        return "tickets";
    }

    @PostMapping("/tickets/booking")
    public String bookTicket(@RequestParam String userId, @RequestParam String eventId) {
        bookingFacade.bookTicket(userId, eventId);
        return "redirect:/tickets?success";
    }
}
