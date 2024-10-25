package com.ticketland.controllers;

import com.ticketland.entities.User;
import com.ticketland.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "index";
    }
}
