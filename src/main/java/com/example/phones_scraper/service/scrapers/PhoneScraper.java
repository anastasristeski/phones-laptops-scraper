package com.example.phones_scraper.service.scrapers;

import com.example.phones_scraper.model.Phone;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public interface PhoneScraper {
     void scrapePhones();
     Elements getItemsFromPage(int pageNumber);
     Phone createPhone(Element item);
}
