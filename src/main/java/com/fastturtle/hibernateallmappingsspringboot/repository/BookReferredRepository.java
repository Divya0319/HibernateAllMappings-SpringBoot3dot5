package com.fastturtle.hibernateallmappingsspringboot.repository;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookReferredRepository extends JpaRepository<BookReferred, Integer> {

}
