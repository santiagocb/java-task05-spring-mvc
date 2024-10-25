package com.ticketland.persistence.util.impl;

import com.ticketland.entities.User;
import com.ticketland.persistence.util.CSVDataReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class CSVUserDataReader implements CSVDataReader<User> {

    private final List<User> users;

    public CSVUserDataReader() {
        users = new ArrayList<>();
    }

    @Override
    public List<User> getDataFromCSV(String filePath) {

        ClassPathResource resource = new ClassPathResource(filePath);

        try (InputStream inputStream = resource.getInputStream();
             Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] raw = data.split(",");
                users.add(new User(raw[0], raw[1], raw[2]));
            }

            return users;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
