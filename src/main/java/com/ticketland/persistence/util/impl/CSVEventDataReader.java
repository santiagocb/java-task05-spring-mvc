package com.ticketland.persistence.util.impl;

import com.ticketland.entities.Event;
import com.ticketland.persistence.util.CSVDataReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class CSVEventDataReader implements CSVDataReader<Event> {

    private final List<Event> events;

    public CSVEventDataReader() {
        events = new ArrayList<>();
    }

    @Override
    public List<Event> getDataFromCSV(String filePath) {

        ClassPathResource resource = new ClassPathResource(filePath);

        try (InputStream inputStream = resource.getInputStream();
             Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] raw = data.split(",");
                events.add(new Event(raw[0], raw[1], raw[2], LocalDate.parse(raw[3]), Double.parseDouble(raw[4])));
            }
            return events;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
