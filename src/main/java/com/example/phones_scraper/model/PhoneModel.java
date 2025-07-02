package com.example.phones_scraper.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="phoneModels")

public class PhoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long phone_model_id;
    private String brand;
    private String modelName;
    private boolean has5G;
    private String processorBrand;
    private int numCores;
    private double processorSpeed;
    private double batteryCapacity;
    private double internalMemory;
    private double ramCapacity;
    private double screenSize;
    private String resolution;
    private int refreshRate;
    private double rearCameraMP;
    private double frontCameraMP;
    @OneToMany(mappedBy = "phoneModel", cascade = CascadeType.ALL)
    private List<Phone> phoneListing;

    public PhoneModel(String brand, String modelName, boolean has5G, String processorBrand, int numCores, double processorSpeed, double batteryCapacity, double internalMemory, double ramCapacity, double screenSize, String resolution, int refreshRate, double rearCameraMP, double frontCameraMP) {
        this.brand = brand;
        this.modelName = modelName;
        this.has5G = has5G;
        this.processorBrand = processorBrand;
        this.numCores = numCores;
        this.processorSpeed = processorSpeed;
        this.batteryCapacity = batteryCapacity;
        this.internalMemory = internalMemory;
        this.ramCapacity = ramCapacity;
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.rearCameraMP = rearCameraMP;
        this.frontCameraMP = frontCameraMP;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setPhone_model_id(long phone_model_id) {
        this.phone_model_id = phone_model_id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setHas5G(boolean has5G) {
        this.has5G = has5G;
    }

    public void setProcessorBrand(String processorBrand) {
        this.processorBrand = processorBrand;
    }

    public void setNumCores(int numCores) {
        this.numCores = numCores;
    }

    public void setProcessorSpeed(double processorSpeed) {
        this.processorSpeed = processorSpeed;
    }

    public void setBatteryCapacity(double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public void setInternalMemory(double internalMemory) {
        this.internalMemory = internalMemory;
    }

    public void setRamCapacity(double ramCapacity) {
        this.ramCapacity = ramCapacity;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public void setRearCameraMP(double rearCameraMP) {
        this.rearCameraMP = rearCameraMP;
    }

    public void setFrontCameraMP(double frontCameraMP) {
        this.frontCameraMP = frontCameraMP;
    }

    public void setPhoneListing(List<Phone> phoneListing) {
        this.phoneListing = phoneListing;
    }

    public long getPhone_model_id() {
        return phone_model_id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }

    public boolean isHas5G() {
        return has5G;
    }

    public String getProcessorBrand() {
        return processorBrand;
    }

    public int getNumCores() {
        return numCores;
    }

    public double getProcessorSpeed() {
        return processorSpeed;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public double getInternalMemory() {
        return internalMemory;
    }

    public double getRamCapacity() {
        return ramCapacity;
    }

    public String getResolution() {
        return resolution;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public double getRearCameraMP() {
        return rearCameraMP;
    }

    public double getFrontCameraMP() {
        return frontCameraMP;
    }

    public List<Phone> getPhoneListing() {
        return phoneListing;
    }
}
