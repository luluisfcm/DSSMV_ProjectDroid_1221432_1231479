package com.example.dssmv_projectdroid_1221432_1231479.model;

public class CreateReviewRequest {
    private boolean recommended;
    private String review;

    // Construtor
    public CreateReviewRequest(boolean recommended, String review) {
        this.recommended = recommended;
        this.review = review;
    }

    // Getters e Setters
    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    // Método toString para exibição
    @Override
    public String toString() {
        return "CreateReviewRequest{" +
                "recommended=" + recommended +
                ", review='" + review + '\'' +
                '}';
    }
}
