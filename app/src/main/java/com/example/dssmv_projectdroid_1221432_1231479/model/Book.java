package com.example.dssmv_projectdroid_1221432_1231479.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Book {
    @SerializedName("title")
    private String title;

    @SerializedName("authors")
    private List<Author> authors;

    @SerializedName("isbn")
    private String isbn;

    @SerializedName("cover_urls")
    private CoverUrls coverUrls;

    public CoverUrls getCoverUrls() {
        return coverUrls;
    }

    public void setCoverUrls(CoverUrls coverUrls) {
        this.coverUrls = coverUrls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


}
