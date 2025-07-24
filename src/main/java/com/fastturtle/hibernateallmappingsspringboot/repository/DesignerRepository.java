package com.fastturtle.hibernateallmappingsspringboot.repository;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DesignerRepository extends JpaRepository<Designer, Integer> {

    @Query("SELECT d.booksReferred FROM Designer d WHERE d.id = :dId")
    List<BookReferred> findAllBooksForDesigner(@Param("dId") int dId);

    Optional<Designer> findByEmail(String email);
}
