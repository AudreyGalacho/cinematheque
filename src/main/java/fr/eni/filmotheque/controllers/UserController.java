package fr.eni.filmotheque.controllers;

import fr.eni.filmotheque.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }
    @PostMapping("/register")
    public String handleRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }
}
