package com.fastturtle.hibernateallmappingsspringboot.controllers;

import com.fastturtle.hibernateallmappingsspringboot.dtos.ReviewerDTO;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;
import com.fastturtle.hibernateallmappingsspringboot.entity.Designer;
import com.fastturtle.hibernateallmappingsspringboot.service.CoderServiceImpl;
import com.fastturtle.hibernateallmappingsspringboot.service.DesignerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewerController {

    private final CoderServiceImpl coderService;
    private final DesignerService designerService;

    public ReviewerController(CoderServiceImpl coderService, DesignerService designerService) {
        this.coderService = coderService;
        this.designerService = designerService;
    }

    @GetMapping("/reviewer-dashboard")
    public String reviewerDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        ReviewerDTO reviewerDTO = new ReviewerDTO();
        boolean isCoder = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CODER"));

        if(isCoder) {
            Coder coder = coderService.fetchCoderByEmail(userDetails.getUsername());
            CoderDetail coderDetail = coder.getCoderDetail();
            reviewerDTO.setId(coder.getId());
            reviewerDTO.setFirstName(coder.getFirstName());
            reviewerDTO.setLastName(coder.getLastName());
            reviewerDTO.setAge(coder.getAge());
            reviewerDTO.setEmail(coder.getEmail());
            reviewerDTO.setDesignation("Coder");
            reviewerDTO.setGithubProfileUrl(coderDetail.getGithubProfileUrl());
            reviewerDTO.setSoReputation(coderDetail.getSoRep());

            List<String> bookNames = new ArrayList<>();

            for(BookReferred b : coder.getBooksReferred()) {
                bookNames.add(b.getTitle());
            }

            reviewerDTO.setBooksReferred(bookNames);
        } else {
            Designer designer = designerService.fetchDesignerByEmail(userDetails.getUsername());
            reviewerDTO.setId(designer.getId());
            reviewerDTO.setFirstName(designer.getFirstName());
            reviewerDTO.setLastName(designer.getLastName());
            reviewerDTO.setEmail(designer.getEmail());
            reviewerDTO.setDesignation("Designer");

            reviewerDTO.setAge(0);
            reviewerDTO.setGithubProfileUrl(null);
            reviewerDTO.setSoReputation(0);

            List<String> bookNames = new ArrayList<>();

            for(BookReferred b : designer.getBooksReferred()) {
                bookNames.add(b.getTitle());
            }

            reviewerDTO.setBooksReferred(bookNames);
        }

        model.addAttribute("reviewer", reviewerDTO);

        return "reviewer-dashboard";
    }
}
