package com.example.dssmv_projectdroid_1221432_1231479.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {
    private LocalDateTime createdDate;
    private UUID id;
    private String isbn;
    private boolean recommended;
    private String review;
    private String reviewer;

    // Constructor
    public Review(LocalDateTime createdDate, UUID id, String isbn, boolean recommended, String review, String reviewer) {
        this.createdDate = createdDate;
        this.id = id;
        this.isbn = isbn;
        this.recommended = recommended;
        this.review = review;
        this.reviewer = reviewer;
    }

    // Getters and Setters
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    // toString Method for Display
    @Override
    public String toString() {
        return "Review{" +
                "createdDate=" + createdDate +
                ", id=" + id +
                ", isbn='" + isbn + '\'' +
                ", recommended=" + recommended +
                ", review='" + review + '\'' +
                ", reviewer='" + reviewer + '\'' +
                '}';
    }
}
