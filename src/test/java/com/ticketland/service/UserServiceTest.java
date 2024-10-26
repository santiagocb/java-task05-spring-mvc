package com.ticketland.service;

import com.ticketland.entities.User;
import com.ticketland.repositories.UserRepository;
import com.ticketland.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testRegisterUser() {
        User user = new User("123", "John Doe", "john@example.com");
        userService.register(user);
        verify(userRepository).save(user);
    }

    @Test
    void testGetById() {
        User user = new User("123", "John Doe", "john@example.com");
        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        User foundUser = userService.getById("123");

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        verify(userRepository).findById("123");
    }

    @Test
    void testGetById_NotFound() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getById("999"));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(
                new User("123", "John Doe", "john@example.com"),
                new User("124", "Jane Doe", "jane@example.com")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> foundUsers = userService.getAll();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        assertTrue(foundUsers.stream().anyMatch(u -> u.getName().equals("John Doe")));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getName().equals("Jane Doe")));
        verify(userRepository).findAll();
    }
}