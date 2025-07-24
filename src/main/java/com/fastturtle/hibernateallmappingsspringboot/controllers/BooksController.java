package com.fastturtle.hibernateallmappingsspringboot.controllers;

import com.fastturtle.hibernateallmappingsspringboot.dtos.BookDTO;
import com.fastturtle.hibernateallmappingsspringboot.dtos.ReviewDTO;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReview;
import com.fastturtle.hibernateallmappingsspringboot.service.BooksReferredService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BooksController {

    private final BooksReferredService booksReferredService;

    public BooksController(BooksReferredService booksReferredService) {
        this.booksReferredService = booksReferredService;
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
    public String showReviewsOfBook(@PathVariable int bookId, Model model) {
        BookReferred book = booksReferredService.findBookById(bookId);
        List<BookReview> bookReviews = booksReferredService.findReviewsForBook(bookId);

        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for(BookReview b : bookReviews) {
            reviewDTOS.add(from(b));
        }

        model.addAttribute("bookTitle", book.getTitle());
        model.addAttribute("reviews", reviewDTOS);

        return "book-reviews";
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
