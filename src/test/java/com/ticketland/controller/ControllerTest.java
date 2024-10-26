package com.ticketland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketland.entities.Event;
import com.ticketland.entities.Ticket;
import com.ticketland.entities.User;
import com.ticketland.entities.UserAccount;
import com.ticketland.facades.BookingFacade;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingFacade bookingFacade;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllUsers() throws Exception {

        UserAccount userAcc1 = new UserAccount("123", 10L, new User("123", "Luci Dancer", "luci@email.com"));
        UserAccount userAcc2 = new UserAccount("124", 10L, new User("124", "Rogerio Seni", "rogerio@email.com"));
        List<UserAccount> users = Arrays.asList(userAcc1, userAcc2);

        when(bookingFacade.getAllUserAccounts()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    public void testBookTicket() throws Exception {
        UserAccount userAcc1 = bookingFacade.getUserAccount("123");
        Event event1 = bookingFacade.createEvent(new Event("01", "Champions Final", "Santiago Bernabeu", LocalDate.parse("2025-08-17"), 10L));

        Ticket ticket = new Ticket(userAcc1, event1);

        when(bookingFacade.bookTicket(anyString(), anyString())).thenReturn(ticket);

        mockMvc.perform(post("/tickets/booking?userId=123&eventId=01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tickets?success"));
    }

    @Test
    public void testGetBookedTickets() throws Exception {
        UserAccount userAcc = new UserAccount("123", 10L, new User("123", "Luci Dancer", "luci@email.com"));

        bookingFacade.refillAccount(userAcc.getId(), 50L);

        Event event1 = new Event("01", "Champions Final", "Santiago Bernabeu", LocalDate.parse("2025-08-17"), 10L);
        Event event2 = new Event("02", "EngX", "EPAMConf", LocalDate.parse("2025-10-17"), 20L);

        Ticket ticket1 = new Ticket(userAcc, event1);
        Ticket ticket2 = new Ticket(userAcc, event2);
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(bookingFacade.getUserAccount(eq(userAcc.getId()))).thenReturn(userAcc);
        when(bookingFacade.getTicketsByUserAccountId(anyString())).thenReturn(tickets);

        mockMvc.perform(get("/tickets/search/user?userId=123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("searchTickets"))
                .andExpect(model().attribute("userId", userAcc.getId()))
                .andExpect(model().attribute("tickets", tickets))
                .andExpect(model().attribute("userName", userAcc.getUser().getName()));
    }

    @Test
    public void testGetBookedTicketsAccountNotFound() throws Exception {
        UserAccount userAcc = new UserAccount("123", 10L, new User("123", "Luci Dancer", "luci@email.com"));

        bookingFacade.refillAccount(userAcc.getId(), 50L);

        Event event1 = new Event("01", "Champions Final", "Santiago Bernabeu", LocalDate.parse("2025-08-17"), 10L);
        Event event2 = new Event("02", "EngX", "EPAMConf", LocalDate.parse("2025-10-17"), 20L);

        Ticket ticket1 = new Ticket(userAcc, event1);
        Ticket ticket2 = new Ticket(userAcc, event2);
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(bookingFacade.getUserAccount(anyString())).thenThrow(new EntityNotFoundException("User account not found."));
        when(bookingFacade.getTicketsByUserAccountId(anyString())).thenReturn(tickets);

        mockMvc.perform(get("/tickets/search/user?userId=1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("searchTickets"))
                .andExpect(model().attribute("message", "User doesn't exists."));
    }
}