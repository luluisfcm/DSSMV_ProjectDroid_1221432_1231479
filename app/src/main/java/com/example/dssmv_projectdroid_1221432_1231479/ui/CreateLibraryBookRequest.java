package com.example.dssmv_projectdroid_1221432_1231479.ui;

public class CreateLibraryBookRequest {
    private String title; // Assuming title is required
    private String author; // Add other fields as necessary

    // Constructor
    public CreateLibraryBookRequest(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
