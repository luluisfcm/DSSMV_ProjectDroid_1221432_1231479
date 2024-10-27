package com.example.dssmv_projectdroid_1221432_1231479.model;
import java.util.List;

public class Author {
    private List<String> alternateNames; // List of alternate names for the author
    private String bio;                   // Biography of the author
    private String birthDate;             // Birth date of the author
    private String deathDate;             // Death date of the author (nullable)
    private String id;                    // Unique identifier for the author
    private String name;                  // Name of the author

    // Constructor
    public Author(List<String> alternateNames, String bio, String birthDate, String deathDate, String id, String name) {
        this.alternateNames = alternateNames;
        this.bio = bio;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.id = id;
        this.name = name;
    }

    // Getters
    public List<String> getAlternateNames() {
        return alternateNames;
    }

    public String getBio() {
        return bio;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Optional: You can also add setters if you need to modify these fields
}
