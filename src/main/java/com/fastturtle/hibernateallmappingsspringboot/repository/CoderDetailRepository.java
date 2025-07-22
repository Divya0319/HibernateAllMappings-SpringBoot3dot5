package com.fastturtle.hibernateallmappingsspringboot.repository;

import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoderDetailRepository extends JpaRepository<CoderDetail, Integer> {

    @Query("SELECT cd.coder FROM CoderDetail cd WHERE cd.id = :coderDetailId")
    Coder findCoderForCoderDetailById(@Param("coderDetailId") int coderDetailId);

}
