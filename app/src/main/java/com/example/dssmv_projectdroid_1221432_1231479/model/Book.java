package com.example.dssmv_projectdroid_1221432_1231479.model;

import java.util.List;

public class Book {
    private String title;
    private List<Author> authors;
    private String isbn;

    // Getters e setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() { // Add this getter
        return authors;
    }

    public void setAuthors(List<Author> authors) { // Add this setter
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
