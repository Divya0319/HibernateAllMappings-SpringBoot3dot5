package com.fastturtle.hibernateallmappingsspringboot.controllers;

import com.fastturtle.hibernateallmappingsspringboot.dtos.BookDTO;
import com.fastturtle.hibernateallmappingsspringboot.dtos.CreateBookReviewRequestDTO;
import com.fastturtle.hibernateallmappingsspringboot.dtos.ReviewDTO;
import com.fastturtle.hibernateallmappingsspringboot.entity.*;
import com.fastturtle.hibernateallmappingsspringboot.service.BooksReferredService;
import com.fastturtle.hibernateallmappingsspringboot.service.CoderServiceImpl;
import com.fastturtle.hibernateallmappingsspringboot.service.DesignerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BooksController {

    private final BooksReferredService booksReferredService;
    private final CoderServiceImpl coderService;
    private final DesignerService designerService;

    public BooksController(BooksReferredService booksReferredService, CoderServiceImpl coderService, DesignerService designerService) {
        this.booksReferredService = booksReferredService;
        this.coderService = coderService;
        this.designerService = designerService;
    }

    @GetMapping("/books")
    public String showAllBooks(Model model) {
        List<BookReferred> books = booksReferredService.findAllBooks();

        List<BookDTO> bookDTOS = new ArrayList<>();
        for(BookReferred b : books) {
            bookDTOS.add(from(b));
        }

        model.addAttribute("books", bookDTOS);
        return "books-list";
    }

    @GetMapping("/books/{bookId}/reviews")
    public String showReviewsOfBook(@PathVariable int bookId, Model model, Principal principal) {
        BookReferred book = booksReferredService.findBookById(bookId);
        List<BookReview> bookReviews = booksReferredService.findReviewsForBook(bookId);

        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for(BookReview b : bookReviews) {
            reviewDTOS.add(from(b));
        }

        if (principal != null) {
            Coder coder = coderService.fetchCoderByEmail(principal.getName());
            if(coder != null) {
                model.addAttribute("loggedInUserName", coder.getFullName());
            } else {
                Designer designer = designerService.fetchDesignerByEmail(principal.getName());
                if (designer != null) {
                    model.addAttribute("loggedInUserName", designer.getFullName());
                }
            }
        }

        model.addAttribute("bookTitle", book.getTitle());
        model.addAttribute("reviews", reviewDTOS);

        return "book-reviews";
    }

    @PostMapping("/books/{bookId}/reviews")
    public String addBookReview(@RequestParam("comment") String comment,
                                @PathVariable int bookId,
                                Principal principal,
                                Model model,
                                HttpServletResponse response) {

        if (principal == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        BookReview review = new BookReview(comment, LocalDateTime.now());

        String email = principal.getName();

        Coder coderFound = coderService.fetchCoderByEmail(email);
        if (coderFound != null) {
            review.setReviewer(coderFound);
        } else {
            Designer designerFound = designerService.fetchDesignerByEmail(email);
            review.setReviewer(designerFound);
        }

        boolean bookFound = booksReferredService.addBookReview(review, bookId);

        if (!bookFound) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        ReviewDTO reviewDTO = from(review);

        // Assuming you have a fragment that renders a single review
        model.addAttribute("review", reviewDTO);
        return "fragments/review-item :: review"; // return partial HTML
    }

    private BookDTO from(BookReferred bookReferred) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookReferred.getId());
        bookDTO.setTitle(bookReferred.getTitle());
        bookDTO.setThumbnailUrl(bookReferred.getThumbnailUrl());
        return bookDTO;
    }

    private ReviewDTO from(BookReview bookReview) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(bookReview.getId());
        reviewDTO.setComment(bookReview.getComment());
        reviewDTO.setReviewer(bookReview.getReviewer());

        LocalDateTime createdAt = bookReview.getCreatedAt();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy hh:mm a");
        String formatted = createdAt.format(formatter);

        reviewDTO.setCreatedAtFormatted(formatted);
        return reviewDTO;
    }
}
