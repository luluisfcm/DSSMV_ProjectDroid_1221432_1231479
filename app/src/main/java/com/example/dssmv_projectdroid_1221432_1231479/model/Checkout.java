package com.example.dssmv_projectdroid_1221432_1231479.model;

public class Checkout {
    private boolean active;                   // Indicates if the checkout is active
    private LibraryBook book;                 // The library book being checked out
    private String createTimestamp;           // Timestamp when the checkout was created
    private String dueDate;                   // Due date for returning the book
    private String id;                        // Unique identifier for the checkout record
    private boolean overdue;                  // Indicates if the checkout is overdue
    private String updateTimestamp;           // Timestamp of the last update
    private String userId;                    // Unique identifier for the user who checked out the book

    // Constructor
    public Checkout(boolean active, LibraryBook book, String createTimestamp, String dueDate,
                    String id, boolean overdue, String updateTimestamp, String userId) {
        this.active = active;
        this.book = book;
        this.createTimestamp = createTimestamp;
        this.dueDate = dueDate;
        this.id = id;
        this.overdue = overdue;
        this.updateTimestamp = updateTimestamp;
        this.userId = userId;
    }

    // Getters
    public boolean isActive() {
        return active;
    }

    public LibraryBook getBook() {
        return book;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getId() {
        return id;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    // Optional: You can also add setters if you need to modify these fields
}
