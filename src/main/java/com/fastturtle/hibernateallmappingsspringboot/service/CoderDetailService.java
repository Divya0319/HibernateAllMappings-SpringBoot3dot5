package com.fastturtle.hibernateallmappingsspringboot.service;

import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;
import com.fastturtle.hibernateallmappingsspringboot.repository.CoderDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CoderDetailService {

    private final CoderDetailRepository coderDetailRepository;

    public CoderDetailService(CoderDetailRepository coderDetailRepository) {
        this.coderDetailRepository = coderDetailRepository;
    }

    public Coder findCoderForCoderDetailById(int coderDetailId) {
        return coderDetailRepository.findCoderForCoderDetailById(coderDetailId);
    }

    @Transactional
    public boolean deleteCoderDetailById(int coderDetailId) {
        Optional<CoderDetail> coderDetailOptional = coderDetailRepository.findById(coderDetailId);

        if(coderDetailOptional.isEmpty()) {
            return false;
        }

        CoderDetail coderDetail = coderDetailOptional.get();
        Coder coder = coderDetail.getCoder();

        if(coder != null) {
            coder.setCoderDetail(null);    // this is required to remove backward association from coderDetail to coder
        }

        coderDetailRepository.delete(coderDetail);

        return true;
    }
}
