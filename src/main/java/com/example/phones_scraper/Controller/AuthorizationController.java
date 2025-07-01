package com.example.phones_scraper.Controller;

import com.example.phones_scraper.model.User;
import com.example.phones_scraper.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
public class AuthorizationController {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);
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
    public String registerForm(Model model){
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam String username,@RequestParam String password){

        if(username == null || username.trim().isEmpty() || password ==null || password.trim().isEmpty()){
            log.warn("You must fill username and password to continue");
            return "redirect:/register?error=empty";
        }
        if(userRepository.findByUsername(username).isPresent()){
            log.warn("Username {} already exists",username);
            return "redirect:/register?error=exists";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);
        log.info("Registration successful");
        return "redirect:/login?registered=true";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
