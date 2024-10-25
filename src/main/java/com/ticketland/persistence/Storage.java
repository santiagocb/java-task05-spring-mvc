package com.ticketland.persistence;

import com.ticketland.persistence.util.impl.CSVUserDataReader;
import com.ticketland.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:application.properties")
public class Storage {

    @Autowired
    private UserRepository userRepository;

    @Value("${users.file.path}")
    private String userFilePath;

    @Autowired
    private CSVUserDataReader csvUserDataReader;


    @PostConstruct
    private void init() {
        csvUserDataReader.getDataFromCSV(userFilePath).forEach(u -> userRepository.save(u));
    }
}
