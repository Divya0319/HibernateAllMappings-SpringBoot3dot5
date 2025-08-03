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

        ReviewerDTO reviewerDTO;
        boolean isCoder = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CODER"));

        if(isCoder) {
            Coder coder = coderService.fetchCoderByEmail(userDetails.getUsername());
            CoderDetail coderDetail = coder.getCoderDetail();

            List<String> bookNames = coder.getBooksReferred().stream()
                    .map(BookReferred::getTitle)
                    .toList();

            reviewerDTO = new ReviewerDTO(
                    coder.getId(),
                    coder.getFirstName(),
                    coder.getLastName(),
                    coder.getAge(),
                    coder.getEmail(),
                    "Coder",
                    coderDetail.getGithubProfileUrl(),
                    coder.getProfilePicUrl(),
                    coderDetail.getSoRep(),
                    bookNames
            );
        } else {
            Designer designer = designerService.fetchDesignerByEmail(userDetails.getUsername());

            List<String> bookNames = designer.getBooksReferred().stream()
                    .map(BookReferred::getTitle)
                    .toList();

            reviewerDTO = new ReviewerDTO(
                    designer.getId(),
                    designer.getFirstName(),
                    designer.getLastName(),
                    0,
                    designer.getEmail(),
                    "Designer",
                    null,
                    designer.getProfilePicUrl(),
                    0,
                    bookNames
            );
        }

        model.addAttribute("reviewer", reviewerDTO);

        return "reviewer-dashboard";
    }
}
