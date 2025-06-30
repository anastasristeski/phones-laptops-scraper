package com.example.phones_scraper.Controller;

import com.example.phones_scraper.model.User;
import com.example.phones_scraper.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthorizationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/register")
    public String registerForm(){
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam String username,@RequestParam String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ADMIN");
        userRepository.save(user);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
