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

@Component
public abstract class PhoneScraper {
     protected final PhoneRepository phoneRepository;
     @Autowired
     protected PhoneScraper(PhoneRepository phoneRepository) {
          this.phoneRepository = phoneRepository;
     }
     public List<Phone> scrapePhones(){
          List<Phone> newPhones = new ArrayList<>();
          WebDriver webDriver = null;
          try{
                webDriver = initializeWebDriver();
                int page = 1;
               while (true) {
                    if(page==11){
                         System.out.println("Repeating pages error");
                         break;
                    }
                    Document document = loadPage(webDriver,page);
                    if(document == null) break;
                    newPhones.addAll(processPageItems(document,page));
                    introduceDelay();
                    page++;
               }


          }catch(Exception e){
               System.err.println("Error occurred during scraping: "+ e.getMessage());
               e.printStackTrace();
          }finally{
               System.out.println("Quitting webDriver");
               assert webDriver != null;
               webDriver.quit();
          }
          return newPhones;
     }

     private List<Phone> processPageItems(Document doc,int pageNumber){
          List<Phone> phonesForSaving = new ArrayList<>();
          Elements items = doc.select(getCSS_SELECTOR());
          for(Element item:items){
                    try{
                         Phone newPhone = createPhone(item);
                              phonesForSaving.add(newPhone);
                    }catch(Exception e){
                         System.err.println("Error processing a phone on page: "+pageNumber);
                    }
               }

          return phonesForSaving;
     }
     private Document loadPage(WebDriver webDriver, int pageNumber){
          String url = getBaseUrl() + pageNumber;
          System.out.println("Scraping page: " + pageNumber + " from URL: " + url);
          webDriver.get(url);
          WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
          try {
               webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(getWAIT_CSS_SELECTOR())));
               System.out.println("Page " + pageNumber + " loaded successfully.");
               String pageSource = webDriver.getPageSource();
               return Jsoup.parse(pageSource);
          } catch (Exception e) {
               System.out.println("No products found on page " + pageNumber);
               return null;
          }
     }
     private WebDriver initializeWebDriver(){
          ChromeOptions options = new ChromeOptions();
          options.addArguments("--headless");
          options.addArguments("--disable-gpu");
          return new ChromeDriver(options);
     }
     public void introduceDelay(){
          try{
               Thread.sleep(2000);
          }catch(InterruptedException e){
               Thread.currentThread().interrupt();
               System.err.println("Scraping interrupted"+e.getMessage());
          }
     }
     abstract Phone createPhone(Element item);
     abstract String getCSS_SELECTOR();
     abstract String getWAIT_CSS_SELECTOR();
     abstract String getBaseUrl();
     abstract String getScraper();
}
