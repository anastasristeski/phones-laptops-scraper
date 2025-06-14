package com.example.phones_scraper.model;

public class Phone {
    private String name;
    private String price;
    private String url;

    public Phone(String name, String price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }
}
