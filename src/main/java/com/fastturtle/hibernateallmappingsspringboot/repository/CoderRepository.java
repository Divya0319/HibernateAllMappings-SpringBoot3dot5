package com.fastturtle.hibernateallmappingsspringboot.repository;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CoderRepository extends JpaRepository<Coder, Integer> {

    @Query("SELECT c.booksReferred FROM Coder c WHERE c.id = :coderId")
    List<BookReferred> findBooksReferredByCoder(@Param("coderId") int coderId);

    @Query("SELECT c.coderDetail FROM Coder c WHERE c.id = :coderId")
    CoderDetail findCoderDetailForCoder(@Param("coderId") int coderId);

    Optional<Coder> findByEmail(String email);

}
