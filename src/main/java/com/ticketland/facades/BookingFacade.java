package com.ticketland.facades;

import com.ticketland.entities.Event;
import com.ticketland.entities.Ticket;
import com.ticketland.entities.User;
import com.ticketland.entities.UserAccount;
import com.ticketland.exceptions.InsufficientFundsException;
import com.ticketland.services.EventService;
import com.ticketland.services.TicketService;
import com.ticketland.services.UserAccountService;
import com.ticketland.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookingFacade {

    public static final Logger logger = LoggerFactory.getLogger(BookingFacade.class);

    private final UserService userService;
    private final UserAccountService userAccountService;
    private final EventService eventService;
    private final TicketService ticketService;

    public BookingFacade(UserService userService, UserAccountService userAccountService, EventService eventService, TicketService ticketService) {
        this.userAccountService = userAccountService;
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public void createUser(User user) {
        userService.register(user);
        userAccountService.createAccount(user.getId());
    }

    public void bookTicket(String userId, String eventId) throws InsufficientFundsException {
        UserAccount account = userAccountService.findByUserId(userId);
        Event event = eventService.findByEventId(eventId);

        if (account.getBalance() < event.getTicketPrice()) {
            throw new InsufficientFundsException("Insufficient funds to book the ticket.");
        }
        userAccountService.refillBalance(userId, event.getTicketPrice() * -1);

        ticketService.generate(new Ticket(account, event));
    }

    public void createAccount(String userId) {
        userAccountService.createAccount(userId);
    }

    public void refillAccount(String userId, double amount) {
        userAccountService.refillBalance(userId, amount);
    }

    public void showAllUserAccounts() {
        logger.info("All user accounts: [{}] {}",
                userAccountService.findAll().size(),
                userAccountService.findAll().stream().map(u -> u.getId() + " Balance:" + u.getBalance() + " User:" + u.getUser().getName()).toList()
        );
    }

    public void showAllTickets() {
        logger.info("All tickets: [{}] {}",
                ticketService.findAll().size(),
                ticketService.findAll().stream().map(t -> t.getId() + " User:" + t.getUser().getId() + " Event:" + t.getEvent().getId()).toList()
        );
    }

    public void showAllEvents() {
        logger.info("All events: [{}] {}",
                eventService.findAll().size(),
                eventService.findAll().stream().map(e -> e.getId() + " " + e.getName() + " " + e.getTicketPrice()).toList()
        );
    }

    public void showTicketsByUserAccountId(String userAccountId) {
        logger.info("All tickets of User with ID {}: [{}] {}",
                userAccountService,
                ticketService.findTicketsByAccountUserId(userAccountId).size(),
                ticketService.findTicketsByAccountUserId(userAccountId).stream().map(t -> t.getId() + " User:" + t.getUser().getId() + " Event:" + t.getEvent().getId()).toList()
        );
    }
}
