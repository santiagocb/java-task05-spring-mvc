package com.ticketland.services;

import com.ticketland.entities.User;
import com.ticketland.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user) {
        System.out.println(user.getId());
        System.out.println(user.getName());
        userRepository.save(user);
    }

    public User getById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public List<User> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
    }
}
