package com.ticketland.service;

import com.ticketland.entities.Event;
import com.ticketland.entities.Ticket;
import com.ticketland.entities.User;
import com.ticketland.entities.UserAccount;
import com.ticketland.repositories.TicketRepository;
import com.ticketland.services.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    void testGenerateTicket() {
        User user = new User("user123", "John Doe", "john@example.com");
        UserAccount userAccount = new UserAccount("123", 100.0, user);
        Event event = new Event("event123", "Concert", "Stadium", LocalDate.now(), 150.0);
        Ticket ticket = new Ticket(userAccount, event);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket generatedTicket = ticketService.generate(ticket);

        assertNotNull(generatedTicket);
        assertEquals("Concert", generatedTicket.getEvent().getName());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testFindTicketsByAccountUserId() {
        User user = new User("user123", "John Doe", "john@example.com");
        UserAccount userAccount = new UserAccount("123", 100.0, user);
        Event event = new Event("event123", "Concert", "Stadium", LocalDate.now(), 150.0);
        List<Ticket> tickets = Arrays.asList(new Ticket(userAccount, event));
        when(ticketRepository.findTicketsByUserAccountId("123")).thenReturn(tickets);

        List<Ticket> foundTickets = ticketService.findTicketsByAccountUserId("123");

        assertNotNull(foundTickets);
        assertFalse(foundTickets.isEmpty());
        assertEquals("Concert", foundTickets.get(0).getEvent().getName());
        verify(ticketRepository).findTicketsByUserAccountId("123");
    }

    @Test
    void testFindAllTickets() {
        User user = new User("user123", "John Doe", "john@example.com");
        UserAccount userAccount = new UserAccount("123", 100.0, user);
        Event event = new Event("event123", "Concert", "Stadium", LocalDate.now(), 150.0);
        List<Ticket> tickets = Arrays.asList(new Ticket(userAccount, event));
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> foundTickets = ticketService.findAll();

        assertNotNull(foundTickets);
        assertEquals(1, foundTickets.size());
        verify(ticketRepository).findAll();
    }

    @Test
    void testGetBookedTickets() {
        User user = new User("user123", "John Doe", "john@example.com");
        UserAccount userAccount = new UserAccount("123", 100.0, user);
        Event event = new Event("event123", "Concert", "Stadium", LocalDate.now(), 150.0);
        List<Ticket> tickets = Arrays.asList(new Ticket(userAccount, event));
        Page<Ticket> page = new PageImpl<>(tickets);
        when(ticketRepository.findAllByUserAccount(eq(userAccount), any(PageRequest.class))).thenReturn(page);

        List<Ticket> bookedTickets = ticketService.getBookedTickets(userAccount, 10, 1);

        assertNotNull(bookedTickets);
        assertFalse(bookedTickets.isEmpty());
        assertEquals("Concert", bookedTickets.get(0).getEvent().getName());
        verify(ticketRepository).findAllByUserAccount(eq(userAccount), any(PageRequest.class));
    }

    @Test
    void testPreloadTickets() {
        User user = new User("user123", "John Doe", "john@example.com");
        UserAccount userAccount = new UserAccount("123", 100.0, user);
        Event event = new Event("event123", "Concert", "Stadium", LocalDate.now(), 150.0);
        List<Ticket> tickets = Arrays.asList(new Ticket(userAccount, event));

        ticketService.preloadTickets(tickets);
        verify(ticketRepository, times(tickets.size())).save(any(Ticket.class));
    }
}