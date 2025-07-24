package com.fastturtle.hibernateallmappingsspringboot.dtos;

import com.fastturtle.hibernateallmappingsspringboot.entity.Reviewer;

public class ReviewDTO {

    private int id;

    private String comment;

    private Reviewer reviewer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }
}
