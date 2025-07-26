package com.fastturtle.hibernateallmappingsspringboot.dtos;

import java.util.List;

public class ReviewerDTO {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String designation;
    private String githubProfileUrl;
    private int soReputation;
    private List<String> booksReferred;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGithubProfileUrl() {
        return githubProfileUrl;
    }

    public void setGithubProfileUrl(String githubProfileUrl) {
        this.githubProfileUrl = githubProfileUrl;
    }

    public int getSoReputation() {
        return soReputation;
    }

    public void setSoReputation(int soReputation) {
        this.soReputation = soReputation;
    }

    public List<String> getBooksReferred() {
        return booksReferred;
    }

    public void setBooksReferred(List<String> booksReferred) {
        this.booksReferred = booksReferred;
    }
}
