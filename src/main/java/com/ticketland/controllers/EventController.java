package com.ticketland.controllers;

import com.ticketland.entities.Event;
import com.ticketland.facades.BookingFacade;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EventController {

    private final BookingFacade bookingFacade;


    public EventController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/events")
    public String renderEventForm(Model model) {
        List<Event> events = bookingFacade.showAllEvents();
        model.addAttribute("event", new Event());
        model.addAttribute("events", events);
        return "events";
    }

    @PostMapping("/events")
    public String createEvent(HttpServletRequest request) {
        String eventId = request.getParameter("id");
        String eventName = request.getParameter("name");
        String eventPlace = request.getParameter("place");
        String eventDate = request.getParameter("date");
        String eventPrice = request.getParameter("ticketPrice");

        Event event = new Event(eventId, eventName, eventPlace, LocalDate.parse(eventDate), Integer.parseInt(eventPrice));

        bookingFacade.createEvent(event);
        return "redirect:/events?success";
    }
}
