package com.example.phones_scraper.service;

import com.example.phones_scraper.model.Phone;
import com.example.phones_scraper.service.scrapers.AnhochScraper;
import com.example.phones_scraper.service.scrapers.NeptunScraper;
import com.example.phones_scraper.service.scrapers.PhoneScraper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneScraperService {
    List<PhoneScraper> phoneScraperList;

    public PhoneScraperService(List<PhoneScraper>phoneScraperList) {
        this.phoneScraperList = phoneScraperList;
    }

//    public void scrapeAndSavePhones()  {
//        phoneScraperList.forEach(PhoneScraper::scrapePhones);
//    }

}
