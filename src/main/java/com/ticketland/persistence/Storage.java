package com.ticketland.persistence;

import com.ticketland.facades.BookingFacade;
import com.ticketland.persistence.util.impl.CSVEventDataReader;
import com.ticketland.persistence.util.impl.CSVUserDataReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:application.properties")
public class Storage {

    @Autowired
    private BookingFacade bookingFacade;

    @Value("${users.file.path}")
    private String userFilePath;

    @Value("${events.file.path}")
    private String eventsFilePath;

    @Autowired
    private CSVUserDataReader csvUserDataReader;

    @Autowired
    private CSVEventDataReader csvEventDataReader;

    @PostConstruct
    private void init() {
        csvUserDataReader.getDataFromCSV(userFilePath).forEach(u -> bookingFacade.createUser(u));
        csvEventDataReader.getDataFromCSV(eventsFilePath).forEach(e -> bookingFacade.createEvent(e));
    }
}
