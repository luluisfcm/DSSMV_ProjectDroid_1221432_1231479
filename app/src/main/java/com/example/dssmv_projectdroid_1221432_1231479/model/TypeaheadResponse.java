package com.example.dssmv_projectdroid_1221432_1231479.model;

import java.util.List;

public class TypeaheadResponse {
    private List<String> authors;
    private List<String> subjects;
    private List<String> titles;

    // Construtor
    public TypeaheadResponse(List<String> authors, List<String> subjects, List<String> titles) {
        this.authors = authors;
        this.subjects = subjects;
        this.titles = titles;
    }

    // Getters e Setters
    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    // Método toString para exibição
    @Override
    public String toString() {
        return "TypeaheadResponse{" +
                "authors=" + authors +
                ", subjects=" + subjects +
                ", titles=" + titles +
                '}';
    }
}
