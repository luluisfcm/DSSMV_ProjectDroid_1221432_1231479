package com.example.dssmv_projectdroid_1221432_1231479.model;

import java.time.LocalTime;
import java.util.UUID;

public class Library {

    private String address;
    private LocalTime closeTime;
    private UUID id;
    private String name;
    private boolean open;
    private String openDays;
    private String openStatement;
    private LocalTime openTime;

    public Library(String address, LocalTime closeTime, String name, boolean open, String openDays, String openStatement, LocalTime openTime) {
        this.address = address;
        this.closeTime = closeTime;
        this.id = UUID.randomUUID(); // Gera um UUID automaticamente
        this.name = name;
        this.open = open;
        this.openDays = openDays;
        this.openStatement = openStatement;
        this.openTime = openTime;
    }

    // Getters e Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getOpenDays() {
        return openDays;
    }

    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }

    public String getOpenStatement() {
        return openStatement;
    }

    public void setOpenStatement(String openStatement) {
        this.openStatement = openStatement;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    @Override
    public String toString() {
        return "Library{" +
                "address='" + address + '\'' +
                ", closeTime=" + closeTime +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", openDays='" + openDays + '\'' +
                ", openStatement='" + openStatement + '\'' +
                ", openTime=" + openTime +
                '}';
    }
}