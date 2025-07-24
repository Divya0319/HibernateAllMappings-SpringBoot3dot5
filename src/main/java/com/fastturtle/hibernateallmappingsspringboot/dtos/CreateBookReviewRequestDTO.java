package com.fastturtle.hibernateallmappingsspringboot.dtos;

public class CreateBookReviewRequestDTO {

    private String comment;

    private String reviewerEmail;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }
}
