package com.example.phones_scraper.Controller;

import com.example.phones_scraper.model.Phone;
import com.example.phones_scraper.service.PhoneScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class PhoneController {
    @Autowired
    private PhoneScraperService phoneScraperService;

    @GetMapping("/phones")
    public String getPhones(Model model) {
        List<Phone> phones = phoneScraperService.scrapePhones();
        model.addAttribute("phones", phones);
        return "phones";
    }
}
