package com.fastturtle.hibernateallmappingsspringboot.dtos;

import java.util.List;

public record ReviewerDTO(
        int id,
        String firstName,
        String lastName,
        int age,
        String email,
        String designation,
        String githubProfileUrl,
        String profilePicUrl,
        int soReputation,
        List<String> booksReferred)
{

}
