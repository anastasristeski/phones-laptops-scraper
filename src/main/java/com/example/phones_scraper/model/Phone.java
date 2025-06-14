package com.example.phones_scraper.model;

import jakarta.persistence.*;

@Entity
@Table(name = "phones", uniqueConstraints = {
        @UniqueConstraint(columnNames = "url")
})
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int price;
    @Column(name ="url",unique = true)
    private String url;
    private String store;
    private String brand;
    public Phone(){}
    public Phone(String name, int price, String url,String store) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.store = store;
        this.brand = name.split("\\s+")[0];
    }

    public String getBrand() {
        return brand;
    }

    public long getId() {
        return id;
    }

    public String getStore(){
        return store;
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
