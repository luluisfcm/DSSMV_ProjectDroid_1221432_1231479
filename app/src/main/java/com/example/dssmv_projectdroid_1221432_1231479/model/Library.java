package com.example.dssmv_projectdroid_1221432_1231479.model;

import java.time.LocalTime;
import java.util.List;

public class Library {

    private String id;
    private String address;
    private String name;
    private boolean open;
    private String openDays;
    private String openStatement;
    private String openTime;
    private String closeTime;
    private List<Book> books;

    public Library(String address, String name, boolean open, String openDays, String openStatement) {
        this.address = address;
        this.name = name;
        this.open = open;
        this.openDays = openDays;
        this.openStatement = openStatement;
        this.openTime = openTime;
    }

     //Getters e Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCloseTime() {
        return closeTime;
    }


    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getId() {
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

    public String getOpenTime() {
        return openTime;
    }

    public List<Book> getBooks() {
        return books;
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