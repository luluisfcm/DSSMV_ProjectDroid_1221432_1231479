package com.example.dssmv_projectdroid_1221432_1231479.model;

public class CreateLibraryBookRequest {
    private int stock;  // Total number of copies in stock

    // Constructor
    public CreateLibraryBookRequest(int stock) {
        this.stock = stock;
    }

    // Getter
    public int getStock() {
        return stock;
    }

    // Optional: You can also add a setter if you need to modify this field
    public void setStock(int stock) {
        this.stock = stock;
    }
}
