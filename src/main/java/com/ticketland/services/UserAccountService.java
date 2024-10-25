package com.ticketland.services;

import com.ticketland.entities.UserAccount;
import com.ticketland.repositories.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserAccountService {

    public static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);


    private UserAccountRepository userAccountRepository;

    private UserService userService;

    public UserAccountService(UserAccountRepository userAccountRepository, UserService userService) {
        this.userAccountRepository = userAccountRepository;
        this.userService = userService;
    }

    public UserAccount createAccount(String userId) {
        var user = userService.getById(userId);
        return userAccountRepository.save(new UserAccount(userId, 0, user));
    }

    public void refillBalance(String userId, double amount) {
        UserAccount account = userAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User account not found."));
        logger.info("Added {} to the balance of User {}", amount, userId);
        account.setBalance(account.getBalance() + amount);
        userAccountRepository.save(account);
    }

    public UserAccount findByUserId(String userId) {
        return userAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User account not found."));
    }

    public List<UserAccount> findAll() {
        return StreamSupport.stream(userAccountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
