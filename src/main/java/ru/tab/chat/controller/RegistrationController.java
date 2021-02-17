package ru.tab.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.tab.chat.domain.Role;
import ru.tab.chat.domain.User;
import ru.tab.chat.repo.UserDetailsRepo;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserDetailsRepo userDetailsRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user) {
        User userFromDb = userDetailsRepo.findByUsername(user.getUsername());

        if (userFromDb == null) {
            user.setId(user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userDetailsRepo.save(user);
        }

        return "redirect:/loginPage";
    }
}
