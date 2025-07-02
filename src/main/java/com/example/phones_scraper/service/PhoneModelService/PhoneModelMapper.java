package com.example.phones_scraper.service.PhoneModelService;

import com.example.phones_scraper.model.PhoneModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.lang.Boolean.parseBoolean;

@Component
public class PhoneModelMapper {
    private double DEFAULT_VALUE=0.0;
    public PhoneModel createPhoneModel(String []cols){
        String brand = cols[0].trim();
        String modelName = cols[1];

        boolean has5g = parseBoolean(cols[4]);
        String processorBrand = cols[7];
        int numCores = (int)parseDoubleOrDefault(cols[8]);
//        if(Objects.equals(cols[9], "")){
//
//        }
        double processorSpeed = parseDoubleOrDefault(cols[9]);
        double batteryCapacity = parseDoubleOrDefault(cols[10]);
        double ramCapacity = parseDoubleOrDefault(cols[13]);
        double internalMemory = parseDoubleOrDefault(cols[14]);
        double screenSize = parseDoubleOrDefault(cols[15]);
        int refreshRate = Integer.parseInt(cols[16]);
        String resolution= cols[17];
        double rearCameraMP = parseDoubleOrDefault(cols[21]);
        double frontCameraMP = parseDoubleOrDefault(cols[22]);
        return new PhoneModel(brand,modelName,has5g
                ,processorBrand,numCores
                ,processorSpeed,batteryCapacity,internalMemory,
                ramCapacity,screenSize,resolution,refreshRate,rearCameraMP,frontCameraMP);

    }
    public double parseDoubleOrDefault(String s){
        if(s == null || s.trim().isEmpty()){
            return DEFAULT_VALUE;
        }else{
            return Double.parseDouble(s);
        }
    }
}
