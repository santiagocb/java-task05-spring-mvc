package com.ticketland.controllers;

import com.ticketland.entities.Event;
import com.ticketland.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/register-event")
    public String registerEvent(@ModelAttribute Event event) {
        eventService.create(event);
        return "register-event";
    }
}
