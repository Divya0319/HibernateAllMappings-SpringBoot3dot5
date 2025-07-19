package com.fastturtle.hibernateallmappingsspringboot.repository;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {

}
