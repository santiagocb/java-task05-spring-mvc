package com.ticketland.persistence;

import com.ticketland.facades.BookingFacade;
import com.ticketland.persistence.util.impl.CSVEventDataReader;
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

    @Value("${events.file.path}")
    private String eventsFilePath;

    @Autowired
    private CSVEventDataReader csvEventDataReader;

    @PostConstruct
    private void init() {
        csvEventDataReader.getDataFromCSV(eventsFilePath).forEach(e -> bookingFacade.createEvent(e));
    }
}
