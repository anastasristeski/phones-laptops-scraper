package com.example.phones_scraper.repository;


import com.example.phones_scraper.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    boolean existsByUrl(String url);
    Phone findByUrl(String url);
    void removeAllByUrl(String url);
}
