package com.example.phones_scraper.service.scrapersService;

import com.example.phones_scraper.model.Phone;
import com.example.phones_scraper.repository.PhoneRepository;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class NeptunScraper extends PhoneScraper{
    protected static final String BASE_URL =
            "https://www.neptun.mk/mobilni_telefoni.nspx?page=";
    protected static final String CSS_SELECTOR = "div.col-lg-9.col-md-9.col-sm-8.col-fix-main > div.product-list-item-grid";
    protected static final String WAIT_CSS_SELECTOR="div.product-list-item-grid";
    @Autowired
    public NeptunScraper(PhoneRepository phoneRepository) {
        super(phoneRepository);
    }

    @Override
    public Phone createPhone(Element item){

        String name = item.selectFirst("h2.product-list-item__content--title").text();
        String  priceText = item.selectFirst("span.product-price__amount--value").text().trim();
        int price = Integer.parseInt(priceText.replace(".", ""));

        String relativeLink = item.selectFirst("div.white-box > a[href]").attr("href").trim();
        String url = "https://www.neptun.mk"+ relativeLink;
        return new Phone(name, price, url, "Neptun");
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
        return "Neptun";
    }

}
