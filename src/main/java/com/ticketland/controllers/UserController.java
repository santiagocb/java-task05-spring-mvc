package com.ticketland.controllers;

import com.ticketland.entities.User;
import com.ticketland.entities.UserAccount;
import com.ticketland.facades.BookingFacade;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final BookingFacade bookingFacade;

    public UserController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/users")
    public String renderUserForm(Model model) {
        List<UserAccount> users = bookingFacade.getAllUserAccounts();
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

    @PostMapping("/accounts/refill")
    public String refillUser(@RequestParam String userId, @RequestParam double amount) {
        bookingFacade.refillAccount(userId, amount);
        return "redirect:/users?success";
    }
}
