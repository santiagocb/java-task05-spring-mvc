package com.ticketland.service;

import com.ticketland.entities.Event;
import com.ticketland.entities.Ticket;
import com.ticketland.entities.User;
import com.ticketland.entities.UserAccount;
import com.ticketland.exceptions.InsufficientFundsException;
import com.ticketland.facades.BookingFacade;
import com.ticketland.services.EventService;
import com.ticketland.services.TicketService;
import com.ticketland.services.UserAccountService;
import com.ticketland.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookingFacadeTest {

    @Autowired
    private BookingFacade bookingFacade;

    @MockBean
    private UserService userService;

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private EventService eventService;

    @MockBean
    private TicketService ticketService;

    @Test
    void testCreateUser() {
        User user = new User("user123", "John Doe", "john@example.com");
        doNothing().when(userService).register(user);
        when(userAccountService.createAccount(user.getId())).thenReturn(new UserAccount(user.getId(), 0L, user));

        bookingFacade.createUser(user);

        verify(userService).register(user);
        verify(userAccountService).createAccount(user.getId());
    }

    @Test
    void testGetUserAccount() {
        UserAccount userAccount = new UserAccount("123", 100.0, new User("user123", "John Doe", "john@example.com"));
        when(userAccountService.findByUserId("user123")).thenReturn(userAccount);

        UserAccount foundAccount = bookingFacade.getUserAccount("user123");

        assertNotNull(foundAccount);
        assertEquals("123", foundAccount.getId());
        verify(userAccountService).findByUserId("user123");
    }

    @Test
    void testBookTicket() throws InsufficientFundsException {
        User user = new User("user123", "John Doe", "john@example.com");
        UserAccount userAccount = new UserAccount("123", 200.0, user);
        Event event = new Event("event123", "Concert", "Stadium", LocalDate.now(), 150.0);
        Ticket ticket = new Ticket(userAccount, event);

        when(userAccountService.findByUserId("user123")).thenReturn(userAccount);
        when(eventService.findByEventId("event123")).thenReturn(event);
        when(ticketService.generate(any(Ticket.class))).thenReturn(ticket);

        Ticket bookedTicket = bookingFacade.bookTicket("user123", "event123");

        assertNotNull(bookedTicket);
        verify(userAccountService).findByUserId("user123");
        verify(eventService).findByEventId("event123");
        verify(userAccountService).refillBalance("user123", -150.0);
        verify(ticketService).generate(any(Ticket.class));
    }

    @Test
    void testBookTicket_InsufficientFunds() {
        User user = new User("user123", "John Doe", "john@example.com");
        UserAccount userAccount = new UserAccount("123", 100.0, user);
        Event event = new Event("event123", "Concert", "Stadium", LocalDate.now(), 150.0);

        when(userAccountService.findByUserId("user123")).thenReturn(userAccount);
        when(eventService.findByEventId("event123")).thenReturn(event);

        assertThrows(InsufficientFundsException.class, () -> bookingFacade.bookTicket("user123", "event123"));
    }
}