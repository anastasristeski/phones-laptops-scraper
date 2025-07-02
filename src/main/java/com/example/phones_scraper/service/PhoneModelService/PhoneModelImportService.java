package com.example.phones_scraper.service.PhoneModelService;

import com.example.phones_scraper.model.PhoneModel;
import com.example.phones_scraper.repository.PhoneModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class PhoneModelImportService {
    private final PhoneModelMapper phoneModelMapper;
    private final PhoneModelRepository phoneModelRepository;
    @Autowired
    public PhoneModelImportService(PhoneModelRepository phoneModelRepository, PhoneModelMapper phoneModelMapper) {
        this.phoneModelRepository = phoneModelRepository;
        this.phoneModelMapper = phoneModelMapper;
    }
    public void importFromCsv() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("smartphone_cleaned_v5.csv").getInputStream(), StandardCharsets.UTF_8));
        String headerLine = reader.readLine();//Skip first line with argument names.
        String line;
        while((line = reader.readLine())!=null){
            String [] cols = line.split(",",-1);
            if(cols.length<24){
                System.out.println("Bad line format");
                continue;
            }
            PhoneModel phoneModel =  phoneModelMapper.createPhoneModel(cols);
            phoneModelRepository.save(phoneModel);
        }
    }
}
