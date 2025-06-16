package com.example.phones_scraper.service.scrapers;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NeptunScraper implements PhoneScraper{
    private final PhoneRepository phoneRepository;
    private static final String BASE_URL =
            "https://www.neptun.mk/mobilni_telefoni.nspx?page=";
    @Autowired
    public NeptunScraper(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public void scrapePhones() {
        List<Phone> phones = new ArrayList<>();
        int page = 1;
        while (true) {
            Elements items = getItemsFromPage(page);
            if (items.isEmpty()) break;
            for (Element item : items) {
                Phone newPhone = createPhone(item);
                if (!phoneRepository.existsByUrl(newPhone.getUrl()))
                    phones.add(newPhone);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            page++;
        }
        if(!phones.isEmpty()) {
            phoneRepository.saveAll(phones);
        }
        else{
            System.out.println("no phones to save");
        }
    }
    @Override
    public Elements getItemsFromPage(int pageNumber){
        String url = BASE_URL+pageNumber;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver webDriver = new ChromeDriver(options);

        try {
            webDriver.get(url);
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            try{
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.col-lg-9.col-md-9.col-sm-8.col-fix-main")));
            }catch(Exception e){
                System.out.println("No products found on page " + pageNumber);
                return new Elements();
            }

            String pageSource = webDriver.getPageSource();

            Document doc = Jsoup.parse(pageSource);

            return doc.select("div.col-lg-9.col-md-9.col-sm-8.col-fix-main > div.product-list-item-grid");
        }finally{
            webDriver.quit();
        }
    }
    @Override
    public Phone createPhone(Element item){

        String name = item.selectFirst("h2.product-list-item__content--title").text();
        String  priceText = item.selectFirst("span.product-price__amount--value").text().trim();
        int price = Integer.parseInt(priceText.replace(".", ""));

        String relativeLink = item.selectFirst("div.white-box > a[href]").attr("href").trim();
        String url = "https://www.neptun.mk/ "+ relativeLink;
        return new Phone(name, price, url, "Neptun");
    }
}
