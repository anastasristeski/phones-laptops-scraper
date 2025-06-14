package com.example.phones_scraper.service;


import com.example.phones_scraper.model.Phone;
import com.example.phones_scraper.repository.PhoneRepository;
import com.example.phones_scraper.service.scrapers.AnhochScraper;
import com.example.phones_scraper.service.scrapers.NeptunScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


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
