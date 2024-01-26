package com.example.demo22;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

import static com.example.demo22.SecurityConfig.passwordEncoder;


@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/")
    public String greeting(Model model) {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByEmail(user.getEmail());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder().encode(user.getPassword())); // Кодирование пароля
        userRepo.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

}
