package com.example.dssmv_projectdroid_1221432_1231479.model;

public class CheckedOutBook {
    private boolean active;                 // Indicates if the checkout is active
    private Book book;                      // The book that is checked out
    private String bookId;                  // Unique identifier for the book
    private String createTimestamp;         // Timestamp when the book was checked out
    private String dueDate;                 // Due date for returning the book
    private String id;                      // Unique identifier for the checked-out book record
    private String libraryAddress;          // Address of the library
    private LocalTime libraryCloseTime;     // Library closing time
    private String libraryId;               // Unique identifier for the library
    private String libraryName;             // Name of the library
    private LocalTime libraryOpenTime;      // Library opening time
    private String updateTimestamp;         // Timestamp when the record was last updated
    private String userId;                  // Unique identifier for the user who checked out the book

    // Constructor
    public CheckedOutBook(boolean active, Book book, String bookId, String createTimestamp,
                          String dueDate, String id, String libraryAddress,
                          LocalTime libraryCloseTime, String libraryId,
                          String libraryName, LocalTime libraryOpenTime,
                          String updateTimestamp, String userId) {
        this.active = active;
        this.book = book;
        this.bookId = bookId;
        this.createTimestamp = createTimestamp;
        this.dueDate = dueDate;
        this.id = id;
        this.libraryAddress = libraryAddress;
        this.libraryCloseTime = libraryCloseTime;
        this.libraryId = libraryId;
        this.libraryName = libraryName;
        this.libraryOpenTime = libraryOpenTime;
        this.updateTimestamp = updateTimestamp;
        this.userId = userId;
    }

    // Getters
    public boolean isActive() {
        return active;
    }

    public Book getBook() {
        return book;
    }

    public String getBookId() {
        return bookId;
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

    public String getLibraryAddress() {
        return libraryAddress;
    }

    public LocalTime getLibraryCloseTime() {
        return libraryCloseTime;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public LocalTime getLibraryOpenTime() {
        return libraryOpenTime;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    // Optional: You can also add setters if you need to modify these fields
}
