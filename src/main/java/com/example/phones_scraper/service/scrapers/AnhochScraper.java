package com.example.phones_scraper.service.scrapers;

import com.example.phones_scraper.model.Phone;
import com.example.phones_scraper.repository.PhoneRepository;

import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AnhochScraper extends PhoneScraper{

    protected static final String BASE_URL =
            "https://www.anhoch.com/categories/telefoni/products?brand=&attribute=&toPrice=324980&inStockOnly=2&sort=latest&perPage=50&page=";
    protected static final String CSS_SELECTOR ="div.grid-view-products > div.col";
    protected static final String WAIT_CSS_SELECTOR ="div.col";
    @Autowired
    public AnhochScraper(PhoneRepository phoneRepository) {
        super(phoneRepository);

    }

    @Override
    public Phone createPhone(Element item){
         String name = item.selectFirst("a.product-name > h6").text().trim();
         String priceText=item.selectFirst("div.product-price").text();
         int price = Integer.parseInt(priceText.replace(".", "").split(",")[0]);
         String productLink = item.selectFirst("a.product-name").attr("href").trim();
         return new Phone(name, price, productLink, "Anhoch");
    }

    @Override
    String getCSS_SELECTOR() {
        return CSS_SELECTOR;
    }

    @Override
    String getWAIT_CSS_SELECTOR() {
        return WAIT_CSS_SELECTOR;
    }
    @Override
    public String getBaseUrl(){
        return BASE_URL;
    }

    @Override
    String getScraper() {
        return "Anhoch";
    }
}
