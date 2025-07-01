package com.example.phones_scraper.service.scrapersService;

import com.example.phones_scraper.model.Phone;
import com.example.phones_scraper.repository.PhoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhoneScraperService {
    List<PhoneScraper> phoneScraperList;
    private final PhoneRepository phoneRepository;

    public PhoneScraperService(List<PhoneScraper> phoneScraperList, PhoneRepository phoneRepository) {
        this.phoneScraperList = phoneScraperList;
        this.phoneRepository = phoneRepository;
    }
    @Transactional
    public void scrapeNewPhones() {
        for (PhoneScraper phoneScraper : phoneScraperList) {
            List<Phone> scraped = phoneScraper.scrapePhones();
            List<Phone> newPhones = scraped.stream()
                    .filter(x -> !phoneRepository.existsByUrl(x.getUrl()))
                    .toList();
            if (!newPhones.isEmpty()) {
                phoneRepository.saveAll(newPhones);
            }
        }
    }
    @Transactional
    public void initiateDatabaseCleanup() {
        List<String> allUrls = new ArrayList<>();
        List<String> urlsFromDB = phoneRepository.findAll().stream().map(Phone::getUrl).toList();
        List<String> urlsForDeleting = new ArrayList<>();
        for (PhoneScraper phoneScraper : phoneScraperList) {
            List<Phone> scraped = phoneScraper.scrapePhones();
            allUrls.addAll(scraped.stream().map(Phone::getUrl).toList());
        }
        urlsForDeleting=urlsFromDB.stream().filter(x->!allUrls.contains(x)).toList();
        System.out.println(urlsForDeleting);
        phoneRepository.deleteByUrlIn(urlsForDeleting);
    }

}

