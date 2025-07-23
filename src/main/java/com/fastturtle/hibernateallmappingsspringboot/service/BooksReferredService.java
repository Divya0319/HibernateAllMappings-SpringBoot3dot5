package com.fastturtle.hibernateallmappingsspringboot.service;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReview;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.Designer;
import com.fastturtle.hibernateallmappingsspringboot.repository.BookReferredRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BooksReferredService {

    private final BookReferredRepository bookReferredRepository;

    public BooksReferredService(BookReferredRepository bookReferredRepository) {
        this.bookReferredRepository = bookReferredRepository;
    }

    public List<BookReferred> findAllBooks() {
        return bookReferredRepository.findAll();
    }

    public Coder findCoderOfBookById(int bookId) {
        return bookReferredRepository.findCoderOfBookById(bookId);
    }

    public List<BookReview> findReviewsForBook(int bookId) {
        return bookReferredRepository.findBookReviewsOfBookById(bookId);
    }

    public List<Designer> findAllDesignersForBook(int bId) {
        return bookReferredRepository.findDesignersOfBookById(bId);
    }

    @Transactional
    public boolean addBookReview(BookReview bookReview, int bookId) {

        Optional<BookReferred> bookReferredOptional = bookReferredRepository.findById(bookId);

        if(bookReferredOptional.isEmpty()) {
            return false;
        }

        BookReferred bookReferred = bookReferredOptional.get();

        bookReferred.addBookReview(bookReview);
        bookReferredRepository.save(bookReferred);
        return true;
    }

    public boolean deleteBookById(int bookId) {
        Optional<BookReferred> bookReferredOptional = bookReferredRepository.findById(bookId);

        if(bookReferredOptional.isEmpty()) {
            return false;
        }

        bookReferredRepository.deleteById(bookId);
        return true;
    }
}
