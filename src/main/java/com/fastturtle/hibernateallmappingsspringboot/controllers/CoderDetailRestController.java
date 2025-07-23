package com.fastturtle.hibernateallmappingsspringboot.controllers;

import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.ResponseObject;
import com.fastturtle.hibernateallmappingsspringboot.service.CoderDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CoderDetailRestController {

    private final CoderDetailService coderDetailService;

    public CoderDetailRestController(CoderDetailService coderDetailService) {
        this.coderDetailService = coderDetailService;
    }

    @GetMapping("/coderDetail/{coderDetailId}/coders")
    public List<?> fetchCoderForGivenCoderDetail(@PathVariable int coderDetailId, HttpServletResponse response) {
        ResponseObject res = new ResponseObject();

        Coder coder = coderDetailService.findCoderForCoderDetailById(coderDetailId);


        if(coder == null) {

            List<ResponseObject> result = new ArrayList<>();

            response.setStatus(HttpStatus.NOT_FOUND.value());
            res.setMessage("No coder available with given id");

            result.add(res);

            return result;


        }

        List<Coder> result = new ArrayList<>();

        response.setStatus(HttpStatus.OK.value());

        coder.setCoderDetail(null);

        coder.setBooksReferred(null);

        result.add(coder);

        return result;


    }

    @DeleteMapping("/coderDetail/{coderDetailId}")
    public List<?> deleteCoderDetailById(@PathVariable int coderDetailId, HttpServletResponse response) {

        ResponseObject res = new ResponseObject();

        boolean coderDetailFound = coderDetailService.deleteCoderDetailById(coderDetailId);

        List<ResponseObject> responseToSend = new ArrayList<>();

        if(!coderDetailFound) {

            response.setStatus(HttpStatus.NOT_FOUND.value());
            res.setMessage("CoderDetail with this id not found");

            responseToSend.add(res);

            return responseToSend;
        }

        response.setStatus(HttpStatus.OK.value());
        res.setMessage("CoderDetail with this id : " + coderDetailId + " deleted successfully");

        responseToSend.add(res);

        return responseToSend;
    }
}
