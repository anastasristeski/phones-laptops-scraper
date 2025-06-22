package com.example.phones_scraper.repository;


import com.example.phones_scraper.model.Phone;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    boolean existsByUrl(String url);
    Phone findByUrl(String url);
    @Modifying
    @Transactional
    @Query("DELETE FROM Phone p WHERE p.url IN :urls")
    void deleteByUrlIn(Collection<String> urls);
}
