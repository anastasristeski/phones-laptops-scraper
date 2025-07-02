package com.example.phones_scraper.repository;


import com.example.phones_scraper.model.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneModelRepository extends JpaRepository<PhoneModel,Long> {
    Optional<PhoneModel> findByBrandAndModelName(String brand,String modelName);
}
