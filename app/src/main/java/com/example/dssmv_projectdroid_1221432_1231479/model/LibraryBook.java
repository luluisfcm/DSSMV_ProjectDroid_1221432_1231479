package com.example.dssmv_projectdroid_1221432_1231479.model;

import com.google.gson.annotations.SerializedName;

public class LibraryBook {
    @SerializedName("isbn")
    private String isbn;

    @SerializedName("book")
    private Book book;

    @SerializedName("stock")
    private int stock;

    public String getIsbn() {
        return isbn;
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

    public void setStock(int stock) {
        this.stock = stock;
    }
}
