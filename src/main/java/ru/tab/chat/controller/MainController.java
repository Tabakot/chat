package ru.tab.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tab.chat.domain.User;
import ru.tab.chat.repo.MessageRepo;
import ru.tab.chat.repo.UserDetailsRepo;
import ru.tab.chat.services.UserService;

import java.security.Principal;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageRepo messageRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private  UserDetailsRepo userDetailsRepo;

    @Autowired
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public String main(Model model, Principal principal, @AuthenticationPrincipal User authUser) {

        if (principal == null)
            return "redirect:/loginPage";

        User user = new User();
        if (authUser == null){
            user = userService.loadUserByUsername(principal.getName());
        } else {
            user = authUser;
        }
        user.setPassword("[PRIVATE]");

        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", user);

        data.put("messages", messageRepo.findAll());

        model.addAttribute("frontendData", data);
        return "index";
    }
}
