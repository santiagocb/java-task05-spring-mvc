package com.ticketland.controllers;

import com.ticketland.entities.Ticket;
import com.ticketland.entities.User;
import com.ticketland.facades.BookingFacade;
import com.ticketland.services.TicketService;
import com.ticketland.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookingController {

    private final BookingFacade bookingFacade;
    private final TicketService ticketService;

    public BookingController(BookingFacade bookingFacade, TicketService ticketService) {
        this.bookingFacade = bookingFacade;
        this.ticketService = ticketService;
    }


    @PostMapping("/book-ticket")
    public String bookTicket(@RequestParam String userId, @RequestParam String eventId, Model model) {
        bookingFacade.bookTicket(userId, eventId);
        List<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);
        return "book-ticket";
    }

    @PostMapping("/refill-account")
    public String refillAccount(@RequestParam String userId, @RequestParam double amount) {
        bookingFacade.refillAccount(userId, amount);
        return "refill-account";
    }
}
