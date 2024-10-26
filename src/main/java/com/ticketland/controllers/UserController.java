package com.ticketland.controllers;

import com.ticketland.entities.User;
import com.ticketland.facades.BookingFacade;
import com.ticketland.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final BookingFacade bookingFacade;

    public UserController(UserService userService, BookingFacade bookingFacade) {
        this.userService = userService;
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/users")
    public String showUserForm(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("user", new User());
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/users")
    public String createUser(HttpServletRequest request) {
        String userId = request.getParameter("id");
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");

        User user = new User(userId, userName, userEmail);

        bookingFacade.createUser(user);
        return "redirect:/users?success";
    }
}
