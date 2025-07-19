package com.fastturtle.hibernateallmappingsspringboot.repository;

import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoderRepository extends JpaRepository<Coder, Integer> {

}
