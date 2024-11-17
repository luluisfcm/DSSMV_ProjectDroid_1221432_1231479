package com.example.dssmv_projectdroid_1221432_1231479.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Book {
    @SerializedName("title")
    private String title;

    @SerializedName("isbn")
    private String isbn;

    @SerializedName("description")
    private String description; // Novo campo para a descrição

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


}
