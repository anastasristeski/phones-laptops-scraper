package com.example.phones_scraper.service;


import com.example.phones_scraper.model.Phone;
import com.example.phones_scraper.repository.PhoneRepository;
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


    private static final String BASE_URL =
            "https://www.anhoch.com/categories/telefoni/products?brand=&attribute=&toPrice=324980&inStockOnly=2&sort=latest&perPage=50&page=";
    private final PhoneRepository phoneRepository;
    public PhoneScraperService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public void scrapeAndSavePhones() throws InterruptedException {
        List<Phone> phones = new ArrayList<>();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver webDriver = new ChromeDriver(options);
        int page = 1;
        try{
            while (true) {
                try {
                    String url = BASE_URL + page;
                    webDriver.get(url);
                    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.grid-view-products > div.col")));

                    String pageSource = webDriver.getPageSource();
                    Document doc = Jsoup.parse(pageSource);
                    Elements items = doc.select("div.grid-view-products > div.col");
//            System.out.println(doc.outerHtml());
                    if (items.isEmpty()) break;
                    for (Element item : items) {
                        //get name
                        Element nameElement = item.selectFirst("a.product-name > h6");
                        String name = nameElement != null ? nameElement.text().trim() : "";

                        //get price
                        Element priceElement = item.selectFirst("div.product-price");
                        String priceText = priceElement.text();
                        if(priceText.split("д").length>1){
                            priceText = priceText.split("\\.")[0];
                        }

                        //get store

                        String productStore = (BASE_URL.split("/")[2]).split("\\.")[1];
                        //get url
                        Element linkElement = item.selectFirst("a.product-name");
                        String productLink = linkElement.attr("href").trim();
                        if (phoneRepository.existsByUrl(productLink)) continue;
                        phones.add(new Phone(name, priceText, productLink, productStore));
                    }
                    Thread.sleep(10000);
                    page++;
                } catch (Exception e) {
                    System.out.println("Scraping failed on page" + page + ": " + e.getMessage());
                    break;
                }

            }
        }finally {
            webDriver.quit();
        }

        if(!phones.isEmpty()) {
            phoneRepository.saveAll(phones);
        }
        else{
            System.out.println("no phones to save");
        }
    }

}
