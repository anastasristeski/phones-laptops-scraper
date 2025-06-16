package com.example.phones_scraper.service;

import com.example.phones_scraper.service.scrapers.AnhochScraper;
import com.example.phones_scraper.service.scrapers.NeptunScraper;
import org.springframework.stereotype.Service;

@Service
public class PhoneScraperService {
    AnhochScraper anhochScraper;
    NeptunScraper neptunScraper;

    public PhoneScraperService(AnhochScraper anhochScraper,NeptunScraper neptunScraper) {
        this.anhochScraper = anhochScraper;
        this.neptunScraper = neptunScraper;
    }

    public void scrapeAndSavePhones()  {
        neptunScraper.scrapePhones();
        //anhochScraper.scrapePhones();
    }

}
