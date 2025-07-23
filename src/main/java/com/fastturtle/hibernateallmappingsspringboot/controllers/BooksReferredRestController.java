package com.fastturtle.hibernateallmappingsspringboot.controllers;

import com.fastturtle.hibernateallmappingsspringboot.entity.*;
import com.fastturtle.hibernateallmappingsspringboot.service.BooksReferredService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BooksReferredRestController {

    private final BooksReferredService booksReferredService;

    public BooksReferredRestController(BooksReferredService booksReferredService) {
        this.booksReferredService = booksReferredService;
    }

    @GetMapping("/booksReferred")
    public List<BookReferred> findAllBooks() {

        List<BookReferred> allBooks = booksReferredService.findAllBooks();

        if(!allBooks.isEmpty()) {

            for(BookReferred tempBook : allBooks) {

                tempBook.setCoder(null);
                tempBook.setDesigners(null);
            }
        }


        return allBooks;
    }

    @GetMapping("/booksReferred/coders")
    public List<?> fetchCoderOfBook(@RequestParam("bookId") int bookId, HttpServletResponse response) {

        ResponseObject resObj = new ResponseObject();

        Coder coder = booksReferredService.findCoderOfBookById(bookId);


        if(coder == null) {

            List<ResponseObject> res = new ArrayList<>();

            response.setStatus(HttpStatus.NOT_FOUND.value());
            resObj.setMessage("No coder found for given book with id : " + bookId);
            res.add(resObj);

            return res;
        }

        List<Coder> coders = new ArrayList<>();

        coder.setBooksReferred(null);
        coder.setCoderDetail(null);

        coders.add(coder);

        response.setStatus(HttpStatus.OK.value());

        return coders;

    }

    @GetMapping("/booksReferred/{bookId}/bookReviews")
    public List<?> fetchAllReviewsForABook(@PathVariable int bookId,
                                           HttpServletResponse response) {

        ResponseObject resp = new ResponseObject();

        List<BookReview> bookReviews = booksReferredService.findReviewsForBook(bookId);


        if(bookReviews.isEmpty()) {

            List<ResponseObject> resObj = new ArrayList<>();

            response.setStatus(HttpStatus.NOT_FOUND.value());

            resp.setMessage("Book not found with id : " + bookId + " or there are no reviews available");

            resObj.add(resp);

            return resObj;

        }

        response.setStatus(HttpStatus.OK.value());

        return bookReviews;
    }

    @GetMapping("/booksReferred/{bId}/designers")
    public List<?> fetchAllDesignersForBook(@PathVariable int bId,
                                            HttpServletResponse response) {

        ResponseObject resp = new ResponseObject();

        List<Designer> designers = booksReferredService.findAllDesignersForBook(bId);


        if(designers.isEmpty()) {

            List<ResponseObject> resObj = new ArrayList<>();

            response.setStatus(HttpStatus.NOT_FOUND.value());

            resp.setMessage("Designer not found for book with id : " + bId );

            resObj.add(resp);

            return resObj;

        }

        for(Designer tempDesigner : designers) {

            tempDesigner.setBooksReferred(null);

        }

        response.setStatus(HttpStatus.OK.value());

        return designers;

    }

    @PostMapping("/booksReferred/{bookId}/bookReviews")
    public ResponseObject addBookReview(@RequestBody BookReview bookReview,
                                        @PathVariable int bookId,
                                        HttpServletResponse response) {

        ResponseObject resObj = new ResponseObject();

        boolean bookFound = booksReferredService.addBookReview(bookReview, bookId);

        if(!bookFound) {

            response.setStatus(HttpStatus.NOT_FOUND.value());
            resObj.setMessage("Book not found with id : " + bookId);

            return resObj;
        }


        response.setStatus(HttpStatus.OK.value());
        resObj.setMessage("Book review added successfully to book with id : " + bookId);

        return resObj;
    }

    @DeleteMapping("/booksReferred/{bookId}")
    public ResponseObject deleteBookById(@PathVariable int bookId, HttpServletResponse response) {

        ResponseObject resObj = new ResponseObject();

        boolean bookDeleted = booksReferredService.deleteBookById(bookId);

        if(!bookDeleted) {

            response.setStatus(HttpStatus.NOT_FOUND.value());

            resObj.setMessage("Book not found with id : " + bookId);

            return resObj;

        }

        response.setStatus(HttpStatus.OK.value());

        resObj.setMessage("Book with id : " + bookId + " deleted successfully");

        return resObj;
    }


}
