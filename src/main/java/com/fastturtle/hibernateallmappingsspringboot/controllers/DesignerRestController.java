package com.fastturtle.hibernateallmappingsspringboot.controllers;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.Designer;
import com.fastturtle.hibernateallmappingsspringboot.entity.ResponseObject;
import com.fastturtle.hibernateallmappingsspringboot.service.DesignerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DesignerRestController {

    private final DesignerService designerService;

    public DesignerRestController(DesignerService designerService) {
        this.designerService = designerService;
    }

    @GetMapping("/designers")
    public List<?> fetchAllDesigners(HttpServletResponse response) {

        List<Designer> designers = designerService.fetchAllDesigners();

        List<Designer> tempDesigners = designers;

        if(designers.size() == 0) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            ResponseObject res = new ResponseObject();
            res.setMessage("No designers available");

            List<ResponseObject> resObj = new ArrayList<>();
            resObj.add(res);

            return resObj;

        }

        for(Designer designer : tempDesigners) {

            designer.setBooksReferred(null);
        }

        response.setStatus(HttpStatus.OK.value());

        return tempDesigners;
    }

    @GetMapping("/designers/{dId}/booksReferred")
    public List<?> fetchAllBooksForDesigner(@PathVariable int dId,
                                            HttpServletResponse response) {

        ResponseObject resp = new ResponseObject();

        List<BookReferred> booksReferred = designerService.findAllBooksForDesigner(dId);

        List<BookReferred> tempBooks = booksReferred;

        if(tempBooks.size() == 0) {

            List<ResponseObject> resObj = new ArrayList<>();

            response.setStatus(HttpStatus.NOT_FOUND.value());

            resp.setMessage("Books not found for designer with id : " + dId );

            resObj.add(resp);

            return resObj;

        }

        for(BookReferred tempBook : tempBooks) {

            tempBook.setCoder(null);
            tempBook.setDesigners(null);
        }

        response.setStatus(HttpStatus.OK.value());

        return tempBooks;

    }

    @PostMapping("/designers")
    public Designer addDesigner(@RequestBody Designer designer, HttpServletResponse response) {

        designer.setId(0);

        coderService.addDesigner(designer);

        return designer;

    }

    @PostMapping("/designers/{designerId}/booksReferred")
    public ResponseObject addBookToDesigner(@RequestBody BookReferred bookReferred,
                                            @PathVariable int designerId,
                                            HttpServletResponse response) {

        ResponseObject resObj = new ResponseObject();

        Designer designer = designerService.findDesignerById(designerId);

        if(designer == null) {

            response.setStatus(HttpStatus.NOT_FOUND.value());
            resObj.setMessage("Designer not found with id : " + designerId);

            return resObj;
        }

        List<BookReferred> books = designerService.findAllBooks();

        for(BookReferred tempBook : books) {


            if(tempBook.getTitle().equals(bookReferred.getTitle())) {

                response.setStatus(HttpStatus.BAD_REQUEST.value());
                resObj.setMessage("Duplicate title not allowed");

                return resObj;

            }

        }

        designerService.addBookToDesigner(bookReferred, designerId);

        response.setStatus(HttpStatus.OK.value());
        resObj.setMessage("Book added successfully to designer with id : " + designerId);

        return resObj;
    }

    @DeleteMapping("/designers/{dId}")
    public ResponseObject deleteDesignerById(@PathVariable int dId, HttpServletResponse response) {

        ResponseObject res = new ResponseObject();

        boolean designerDeleted = designerService.deleteDesignerById(dId);

        if(!designerDeleted) {

            response.setStatus(HttpStatus.NOT_FOUND.value());
            res.setMessage("Designer with id: " + dId + " not found");


            return res;
        }

        response.setStatus(HttpStatus.OK.value());
        res.setMessage("Designer with this id : " + dId + " deleted successfully");


        return res;

    }
}
