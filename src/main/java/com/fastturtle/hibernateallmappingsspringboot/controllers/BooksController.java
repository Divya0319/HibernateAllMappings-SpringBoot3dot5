package com.fastturtle.hibernateallmappingsspringboot.controllers;

import com.fastturtle.hibernateallmappingsspringboot.dtos.BookDTO;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.service.BooksReferredService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    private BookDTO from(BookReferred bookReferred) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookReferred.getId());
        bookDTO.setTitle(bookReferred.getTitle());
        bookDTO.setThumbnailUrl(bookReferred.getThumbnailUrl());
        return bookDTO;
    }
}
