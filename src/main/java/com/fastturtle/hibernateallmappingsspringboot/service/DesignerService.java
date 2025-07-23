package com.fastturtle.hibernateallmappingsspringboot.service;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.Designer;
import com.fastturtle.hibernateallmappingsspringboot.repository.BookReferredRepository;
import com.fastturtle.hibernateallmappingsspringboot.repository.DesignerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DesignerService {

    private final DesignerRepository designerRepository;
    private final BookReferredRepository bookReferredRepository;

    public DesignerService(DesignerRepository designerRepository, BookReferredRepository bookReferredRepository) {
        this.designerRepository = designerRepository;
        this.bookReferredRepository = bookReferredRepository;
    }

    public List<Designer> fetchAllDesigners() {
        return designerRepository.findAll();
    }

    public List<BookReferred> findAllBooksForDesigner(int dId) {
        return designerRepository.findAllBooksForDesigner(dId);
    }

    public void addDesigner(Designer designer) {
        designerRepository.save(designer);

    }

    public Designer findDesignerById(int designerId) {
        Optional<Designer> designerOptional = designerRepository.findById(designerId);
        return designerOptional.orElse(null);
    }

    public List<BookReferred> findAllBooks() {
        return bookReferredRepository.findAll();
    }

    @Transactional
    public boolean addBookToDesigner(BookReferred bookReferred, int designerId) {

        Optional<Designer> designerOptional = designerRepository.findById(designerId);

        if(designerOptional.isEmpty()) {
            return false;
        }

        Designer designer = designerOptional.get();
        designer.addBook(bookReferred);

        designerRepository.save(designer);
        return true;
    }

    public boolean deleteDesignerById(int dId) {
        Optional<Designer> designerOptional = designerRepository.findById(dId);

        if(designerOptional.isEmpty()) {
            return false;
        }

        designerRepository.deleteById(dId);
        return true;
    }
}
