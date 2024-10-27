package com.example.dssmv_projectdroid_1221432_1231479.model;

public class CoverUrls {
    private String largeUrl;   // URL for the large cover image
    private String mediumUrl;  // URL for the medium cover image
    private String smallUrl;   // URL for the small cover image

    // Constructor
    public CoverUrls(String largeUrl, String mediumUrl, String smallUrl) {
        this.largeUrl = largeUrl;
        this.mediumUrl = mediumUrl;
        this.smallUrl = smallUrl;
    }

    // Getters
    public String getLargeUrl() {
        return largeUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    // Optional: You can also add setters if you need to modify these fields
}
