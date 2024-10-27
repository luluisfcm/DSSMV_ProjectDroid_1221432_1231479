package com.example.dssmv_projectdroid_1221432_1231479.model;

public class LibraryBook {
    private int available;
    private Book book;          // Supondo que você tenha uma classe Book
    private int checkedOut;
    private String isbn;
    private Library library;     // Supondo que você tenha uma classe Library
    private int stock;

    // Construtor
    public LibraryBook(int available, Book book, int checkedOut, String isbn, Library library, int stock) {
        this.available = available;
        this.book = book;
        this.checkedOut = checkedOut;
        this.isbn = isbn;
        this.library = library;
        this.stock = stock;
    }

    // Getters e Setters
    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(int checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Método toString para exibir informações
    @Override
    public String toString() {
        return "LibraryBook{" +
                "available=" + available +
                ", book=" + book +
                ", checkedOut=" + checkedOut +
                ", isbn='" + isbn + '\'' +
                ", library=" + library +
                ", stock=" + stock +
                '}';
    }
}
