package com.ticketland.integration;

import com.ticketland.entities.Event;
import com.ticketland.entities.Ticket;
import com.ticketland.entities.User;
import com.ticketland.entities.UserAccount;
import com.ticketland.exceptions.InsufficientFundsException;
import com.ticketland.facades.BookingFacade;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookingTest {

    @Autowired
    BookingFacade bookingFacade;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        // Clear the database tables
        entityManager.createNativeQuery("TRUNCATE TABLE user_accounts RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE users RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE events RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE tickets RESTART IDENTITY CASCADE").executeUpdate();
    }

    @Test
    @DisplayName("Create a user, two events and two tickets for each event. Then cancel one.")
    public void testBooking1() {
        bookingFacade.createUser(new User("123", "Luci Dancer", "luci@email.com"));
        UserAccount userAcc = bookingFacade.getUserAccount("123");
        bookingFacade.refillAccount(userAcc.getId(), 50L);

        Event event1 = bookingFacade.createEvent(new Event("01", "Champions Final", "Santiago Bernabeu", LocalDate.parse("2025-08-17"), 10L));
        Event event2 = bookingFacade.createEvent(new Event("02", "EngX", "EPAMConf", LocalDate.parse("2025-10-17"), 20L));

        Ticket ticket1 = bookingFacade.bookTicket(userAcc.getId(), event1.getId());
        Ticket ticket2 = bookingFacade.bookTicket(userAcc.getId(), event2.getId());

        var userTickets = bookingFacade.getBookedTickets(userAcc, 5, 1);
        assertEquals(2, userTickets.size());
        assertTrue(userTickets.containsAll(List.of(ticket2, ticket1)));
    }

    @Test
    @DisplayName("Create two users with two events and two tickets for each event and user.")
    public void testBooking2() {
        bookingFacade.createUser(new User("123", "Luci Dancer", "luci@email.com"));
        bookingFacade.createUser(new User("124", "Rogerio Seni", "rogerio@email.com"));

        UserAccount userAcc1 = bookingFacade.getUserAccount("123");
        UserAccount userAcc2 = bookingFacade.getUserAccount("124");

        bookingFacade.refillAccount(userAcc1.getId(), 50L);
        bookingFacade.refillAccount(userAcc2.getId(), 50L);

        Event event1 = bookingFacade.createEvent(new Event("01", "Champions Final", "Santiago Bernabeu", LocalDate.parse("2025-08-17"), 10L));
        Event event2 = bookingFacade.createEvent(new Event("02", "EngX", "EPAMConf", LocalDate.parse("2025-10-17"), 20L));

        Ticket ticket1 = bookingFacade.bookTicket(userAcc1.getId(), event1.getId());
        Ticket ticket2 = bookingFacade.bookTicket(userAcc1.getId(), event2.getId());
        Ticket ticket3 = bookingFacade.bookTicket(userAcc2.getId(), event1.getId());
        Ticket ticket4 = bookingFacade.bookTicket(userAcc2.getId(), event2.getId());

        var user1Tickets = bookingFacade.getBookedTickets(userAcc1, 5, 1);
        assertEquals(2, user1Tickets.size());
        assertTrue(user1Tickets.containsAll(List.of(ticket1,ticket2)));

        var user2Tickets = bookingFacade.getBookedTickets(userAcc2, 5, 1);
        assertEquals(2, user2Tickets.size());
        assertTrue(user2Tickets.containsAll(List.of(ticket3,ticket4)));
    }

    @Test
    @DisplayName("Create two tickets: Book one and get insufficient funds with the second one.")
    public void testBooking3() {
        bookingFacade.createUser(new User("123", "Luci Dancer", "luci@email.com"));
        UserAccount userAcc = bookingFacade.getUserAccount("123");
        bookingFacade.refillAccount(userAcc.getId(), 30L);

        Event event1 = bookingFacade.createEvent(new Event("01", "Champions Final", "Santiago Bernabeu", LocalDate.parse("2025-08-17"), 20L));
        Event event2 = bookingFacade.createEvent(new Event("02", "EngX", "EPAMConf", LocalDate.parse("2025-10-17"), 20L));

        Ticket ticket1 = bookingFacade.bookTicket(userAcc.getId(), event1.getId());

        var userTickets = bookingFacade.getBookedTickets(userAcc, 5, 1);
        assertEquals(1, userTickets.size());
        assertEquals(ticket1, userTickets.get(0));

        var exception = assertThrows(InsufficientFundsException.class , () -> bookingFacade.bookTicket(userAcc.getId(), event2.getId()));
        assertEquals("Insufficient funds to book the ticket.", exception.getMessage());

        assertEquals(10L, userAcc.getBalance());
        assertEquals(1, userTickets.size());
        assertEquals(ticket1, userTickets.get(0));
    }
}