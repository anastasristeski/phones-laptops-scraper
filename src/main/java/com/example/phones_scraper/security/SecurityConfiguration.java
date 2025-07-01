package com.example.phones_scraper.security;

import com.example.phones_scraper.service.userService.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/","/register","/register**","/login","/css/**").permitAll()
                        .requestMatchers("/scrape","/clean").hasRole("ADMIN")
                        .requestMatchers("/phones").hasAnyRole("USER","ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form->form
                        .loginPage("/login")
                        .defaultSuccessUrl("/phones",true)
                        .permitAll()
                )
                .logout(logout->logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
