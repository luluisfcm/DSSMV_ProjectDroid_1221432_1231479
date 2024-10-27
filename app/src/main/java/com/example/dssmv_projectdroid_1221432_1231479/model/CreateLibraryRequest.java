package com.example.dssmv_projectdroid_1221432_1231479.model;

public class CreateLibraryRequest {
    private String address;        // Address of the library
    private LocalTime closeTime;   // Closing time of the library
    private String name;           // Name of the library
    private String openDays;       // Days the library is open
    private LocalTime openTime;    // Opening time of the library

    // Constructor
    public CreateLibraryRequest(String address, LocalTime closeTime, String name,
                                String openDays, LocalTime openTime) {
        this.address = address;
        this.closeTime = closeTime;
        this.name = name;
        this.openDays = openDays;
        this.openTime = openTime;
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public String getName() {
        return name;
    }

    public String getOpenDays() {
        return openDays;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    // Optional: You can also add setters if you need to modify these fields
    public void setAddress(String address) {
        this.address = address;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }
}
