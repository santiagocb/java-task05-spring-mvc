package com.ticketland.service;

import com.ticketland.entities.Event;
import com.ticketland.repositories.EventRepository;
import com.ticketland.services.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    @Test
    void testCreateEvent() {
        Event event = new Event("01", "Champions Final", "Stadium", LocalDate.now(), 100.0);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.create(event);

        assertNotNull(createdEvent);
        assertEquals("Champions Final", createdEvent.getName());
        verify(eventRepository).save(event);
    }

    @Test
    void testUpdateTicketPrice() {
        Event event = new Event("01", "Champions Final", "Stadium", LocalDate.now(), 100.0);
        when(eventRepository.findById("01")).thenReturn(Optional.of(event));

        eventService.updateTicketPrice("01", 150.0);

        assertEquals(150.0, event.getTicketPrice());
        verify(eventRepository).findById("01");
        verify(eventRepository).save(event);
    }

    @Test
    void testUpdateTicketPrice_EventNotFound() {
        when(eventRepository.findById("02")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.updateTicketPrice("02", 150.0));
    }

    @Test
    void testFindByEventId() {
        Event event = new Event("01", "Champions Final", "Stadium", LocalDate.now(), 100.0);
        when(eventRepository.findById("01")).thenReturn(Optional.of(event));

        Event foundEvent = eventService.findByEventId("01");

        assertNotNull(foundEvent);
        assertEquals("01", foundEvent.getId());
        verify(eventRepository).findById("01");
    }

    @Test
    void testFindByEventId_NotFound() {
        when(eventRepository.findById("02")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.findByEventId("02"));
    }

    @Test
    void testFindAll() {
        List<Event> events = Arrays.asList(
                new Event("01", "Champions Final", "Stadium", LocalDate.now(), 100.0),
                new Event("02", "Local Concert", "Local Arena", LocalDate.now().plusDays(1), 50.0)
        );
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> foundEvents = eventService.findAll();

        assertNotNull(foundEvents);
        assertEquals(2, foundEvents.size());
        verify(eventRepository).findAll();
    }
}