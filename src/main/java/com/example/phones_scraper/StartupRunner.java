//package com.example.phones_scraper;
//
//import com.example.phones_scraper.service.PhoneModelService.PhoneModelImportService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class StartupRunner {
//    @Bean
//    public CommandLineRunner run(PhoneModelImportService phoneModelImportService){
//        return args ->{
//            phoneModelImportService.importFromCsv();
//            System.out.println("Phone models imported on startup");
//        };
//    }
//}
