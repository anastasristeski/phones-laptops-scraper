package com.example.phones_scraper.Controller;

import com.example.phones_scraper.model.Phone;
import com.example.phones_scraper.repository.PhoneRepository;
import com.example.phones_scraper.service.PhoneScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PhoneController {
    private final PhoneRepository phoneRepository;

    private final PhoneScraperService phoneScraperService;
    @Autowired
    public PhoneController(PhoneRepository phoneRepository, PhoneScraperService phoneScraperService) {
        this.phoneRepository = phoneRepository;
        this.phoneScraperService = phoneScraperService;
    }

    @GetMapping("/phones")
    public String getPhones(Model model) {
        List<Phone> phones = phoneRepository.findAll();
        model.addAttribute("phones", phones);
        return "phones";
    }
    @GetMapping("/scrape")
    @ResponseBody
    public String scrapePhones(){
        phoneScraperService.scrapeNewPhones();
        return "Scraping phones finished";
    }
    @GetMapping("/clean")
    @ResponseBody
    public String cleanDb(){
        phoneScraperService.initiateDatabaseCleanup();
        return "finishedCleaning";
    }

}
