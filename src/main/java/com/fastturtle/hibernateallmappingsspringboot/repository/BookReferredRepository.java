package com.fastturtle.hibernateallmappingsspringboot.repository;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReview;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookReferredRepository extends JpaRepository<BookReferred, Integer> {

    @Query("SELECT b.coder FROM BookReferred b WHERE b.id = :bookId")
    Coder findCoderOfBookById(@Param("bookId") int bookId);

    @Query("SELECT b.bookReviews FROM BookReferred b WHERE b.id = :bookId")
    List<BookReview> findBookReviewsOfBookById(@Param("bookId") int bookId);

    @Query("SELECT b.designers FROM BookReferred b WHERE b.id = :bookId")
    List<Designer> findDesignersOfBookById(@Param("bookId") int bookId);

}
