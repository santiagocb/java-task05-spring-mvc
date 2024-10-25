package com.ticketland.controllers;

import com.ticketland.entities.User;
import com.ticketland.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.StreamSupport;

@Controller
public class UserController {

    private UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String home(Model model) {

        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
        System.out.println(users);

        model.addAttribute("users", users);
        return "index";
    }
}
