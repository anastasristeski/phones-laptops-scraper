package com.example.phones_scraper.service;

import com.example.phones_scraper.model.Phone;
import com.example.phones_scraper.repository.PhoneRepository;
import com.example.phones_scraper.service.scrapers.PhoneScraper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhoneScraperService {
    List<PhoneScraper> phoneScraperList;
    private final PhoneRepository phoneRepository;

    public PhoneScraperService(List<PhoneScraper>phoneScraperList,PhoneRepository phoneRepository) {
        this.phoneScraperList = phoneScraperList;
        this.phoneRepository = phoneRepository;
    }
    public void scrapeNewPhones(){
        for(PhoneScraper phoneScraper:phoneScraperList){
            List<Phone> scraped = phoneScraper.scrapePhones();
            List<Phone> newPhones = scraped.stream()
                    .filter(x->!phoneRepository.existsByUrl(x.getUrl()))
                    .toList();
            if(!newPhones.isEmpty())
                phoneRepository.saveAll(newPhones);
        }
    }
    public void updatePrices(){
        for(PhoneScraper phoneScraper : phoneScraperList){
            List<Phone> scrapedPhonesList = phoneScraper.scrapePhones();
            for(Phone scraped:scrapedPhonesList){
               Phone temp = phoneRepository.findByUrl(scraped.getUrl());
               if(temp!=null && !Objects.equals(temp.getPrice(),scraped.getPrice())){
                   temp.setPrice(scraped.getPrice());
                   phoneRepository.save(temp);
               }
            }
        }
    }
    public void cleanDataBase(){
        Set<String> scrapedUrls = phoneScraperList.stream()
                .flatMap(s->s.scrapePhones().stream())
                .map(Phone::getUrl)
                .collect(Collectors.toSet());
        phoneRepository.findAll().stream()
                .filter(x->!scrapedUrls.contains(x.getUrl()))
                .forEach(phoneRepository::delete);

        }
    }
//    public void scrapeAndSavePhones()  {
//        phoneScraperList.forEach(PhoneScraper::scrapePhones);
//    }


