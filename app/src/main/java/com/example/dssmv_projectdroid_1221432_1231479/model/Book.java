package com.example.dssmv_projectdroid_1221432_1231479.model;

import java.util.List;

public class Book {
    private List<Author> authors;          // List of authors for the book
    private String byStatement;             // Statement indicating who the book is by
    private CoverUrls cover;                // Cover URLs for different sizes
    private String description;              // Description of the book
    private String isbn;                    // ISBN of the book
    private int numberOfPages;              // Number of pages in the book
    private String publishDate;             // Publish date of the book
    private List<String> subjectPeople;     // List of subject people
    private List<String> subjectPlaces;     // List of subject places
    private List<String> subjectTimes;      // List of subject times
    private List<String> subjects;          // List of subjects
    private String title;                   // Title of the book

    // Constructor
    public Book(List<Author> authors, String byStatement, CoverUrls cover, String description,
                String isbn, int numberOfPages, String publishDate, List<String> subjectPeople,
                List<String> subjectPlaces, List<String> subjectTimes, List<String> subjects,
                String title) {
        this.authors = authors;
        this.byStatement = byStatement;
        this.cover = cover;
        this.description = description;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
        this.publishDate = publishDate;
        this.subjectPeople = subjectPeople;
        this.subjectPlaces = subjectPlaces;
        this.subjectTimes = subjectTimes;
        this.subjects = subjects;
        this.title = title;
    }

    // Getters
    public List<Author> getAuthors() {
        return authors;
    }

    public String getByStatement() {
        return byStatement;
    }

    public CoverUrls getCover() {
        return cover;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public List<String> getSubjectPeople() {
        return subjectPeople;
    }

    public List<String> getSubjectPlaces() {
        return subjectPlaces;
    }

    public List<String> getSubjectTimes() {
        return subjectTimes;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public String getTitle() {
        return title;
    }

    // Optional: You can also add setters if you need to modify these fields
}
