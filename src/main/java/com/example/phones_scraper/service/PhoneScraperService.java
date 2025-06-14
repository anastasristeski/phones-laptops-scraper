package com.example.phones_scraper.service;

import com.example.phones_scraper.model.Phone;
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

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Service
public class PhoneScraperService {
    private static final String BASE_URL =
            "https://www.anhoch.com/categories/telefoni/products?brand=&attribute=&toPrice=324980&inStockOnly=2&sort=latest&perPage=50&page=";
    public List<Phone> scrapePhones()  {
        List<Phone> phones = new ArrayList<>();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver webDriver = new ChromeDriver();

        int page = 1;
        while(true){
            String url = BASE_URL  + page;
            webDriver.get(url);
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.grid-view-products > div.col")));

            String pageSource = webDriver.getPageSource();
            Document doc = Jsoup.parse(pageSource);
            Elements items = doc.select("div.grid-view-products > div.col");
//            System.out.println(doc.outerHtml());
//            System.out.println("AAAAAAAA");
            if(items.isEmpty()) break ;
            for(Element item : items){
                //get name
                Element nameElement = item.selectFirst("a.product-name > h6");
                String name = nameElement != null ? nameElement.text().trim() : "";

                //get price
                Element priceElement = item.selectFirst("div.product-price");
                String priceText = priceElement.text();
                String productUrl = "https://www.anhoch.com";

                //get url
                Element linkElement = item.selectFirst("a.product-name");
                String productLink = linkElement.attr("href").trim();
                phones.add(new Phone(name, priceText, productLink));
            }
            break;
            //page++;
        }
        webDriver.quit();

        return phones;
    }

}
