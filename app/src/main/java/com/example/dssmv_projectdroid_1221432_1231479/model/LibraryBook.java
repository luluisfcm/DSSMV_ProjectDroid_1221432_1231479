package com.example.dssmv_projectdroid_1221432_1231479.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LibraryBook {
    @SerializedName("id")
    private String id;

    @SerializedName("bookId")
    private String isbn_book;

    @SerializedName("dueDate")
    private String dueDate;

    @SerializedName("isbn")
    private String isbn;

    @SerializedName("book")
    private Book book;

    @SerializedName("stock")
    private int stock;

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getIsbn_book() {
        return isbn_book;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getStock() {
        return stock;
    }
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

}
