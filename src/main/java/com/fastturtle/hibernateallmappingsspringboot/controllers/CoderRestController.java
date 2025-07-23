package com.fastturtle.hibernateallmappingsspringboot.controllers;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;
import com.fastturtle.hibernateallmappingsspringboot.entity.ResponseObject;
import com.fastturtle.hibernateallmappingsspringboot.service.CoderService;

@RestController
@RequestMapping("/api")
public class CoderRestController {
	
	private final CoderService coderService;

	public CoderRestController(CoderService coderService) {
		this.coderService = coderService;
	}

	@GetMapping("/coders")
	public List<?> fetchAllCoders(HttpServletResponse response) {
		
		List<Coder> coders = coderService.fetchAllCoders();
		
		
		List<Coder> tempCoders = coders;

		if(coders.isEmpty()) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			ResponseObject res = new ResponseObject();
			res.setMessage("No coders available");
			
			List<ResponseObject> resObj = new ArrayList<>();
			resObj.add(res);
			
			return resObj;
	
		}
		
		for(Coder coder : tempCoders) {
			
			coder.setCoderDetail(null);
			coder.setBooksReferred(null);
		}
		
		response.setStatus(HttpStatus.OK.value());
		
		return tempCoders;
	}
	
	@GetMapping("/coders/{coderId}")
	public List<?> fetchCoderById(@PathVariable int coderId, HttpServletResponse response) {
		
		ResponseObject res = new ResponseObject();

		
		Coder coder = coderService.findCoderById(coderId); 
		Coder tempCoder = coder;
		
		if(tempCoder == null) {
			
			List<ResponseObject> result = new ArrayList<>();
			
			response.setStatus(HttpStatus.NOT_FOUND.value());
			res.setMessage("No coder available with given id");
			
			result.add(res);
			
			return result;
		}
		
		List<Coder> result = new ArrayList<>();
		
		response.setStatus(HttpStatus.OK.value());
		
		tempCoder.setCoderDetail(null);
		
		tempCoder.setBooksReferred(null);
		
		result.add(tempCoder);
		
		return result;
		
	}
	
	@GetMapping("/coders/{coderId}/coderDetail")
	public List<?> fetchCoderDetailForCoder(@PathVariable int coderId, HttpServletResponse response) {
		ResponseObject res = new ResponseObject();
		
		
		CoderDetail coderDetail = coderService.fetchCoderDetailForCoder(coderId);
		
		CoderDetail tempDetail = coderDetail;
		
		if(coderDetail == null) {
			
			List<ResponseObject> result = new ArrayList<>();
			
			response.setStatus(HttpStatus.NOT_FOUND.value());
			res.setMessage("No coderDetail available for coder with id : " + coderId);
			
			result.add(res);
			
			return result;
			
		}
						
		List<CoderDetail> result = new ArrayList<>();
		
		response.setStatus(HttpStatus.OK.value());
		
		tempDetail.setCoder(null);
		
		result.add(tempDetail);
		
		return result;
	}
	
	@GetMapping("/coders/{coderId}/booksReferred")
	public List<?> fetchAllBooksOfCoder(@PathVariable int coderId, HttpServletResponse response) {
		
		ResponseObject resObj = new ResponseObject();
		
		List<BookReferred> booksReferred = coderService.findBooksReferredByCoder(coderId);
		
		List<BookReferred> tempBooks = booksReferred;
		
		if(tempBooks.size() == 0) {
			
			List<ResponseObject> res = new ArrayList<>();
			
			response.setStatus(HttpStatus.NOT_FOUND.value());
			resObj.setMessage("No books found for given coder with id : " + coderId + " or no coder found with this id");
			res.add(resObj);
			
			return res;
		}
		
		for(BookReferred tempBook : tempBooks) {
			
			tempBook.setCoder(null);
			tempBook.setDesigners(null);
		}
		
		response.setStatus(HttpStatus.OK.value());
		
		return tempBooks;
		
	}
	

	@PostMapping("/coders")
	public Coder addCoder(@RequestBody Coder coder) {
		
		coder.setId(0);
		
		coderService.addCoder(coder);
		
		return coder;
		
	}
	
	@PostMapping("/coders/{coderId}/coderDetail")
	public ResponseObject addCoderDetail(@RequestBody CoderDetail coderDetail, @PathVariable int coderId,
											HttpServletResponse response) {
		ResponseObject resp = new ResponseObject();
		
		boolean coderFound = coderService.addCoderDetailToCoder(coderDetail, coderId);
		
		if(!coderFound) {
			
			response.setStatus(HttpStatus.NOT_FOUND.value());
			
			resp.setMessage("Coder not found with id : " + coderId );
			
			return resp;
		}
		
		response.setStatus(HttpStatus.OK.value());
		resp.setMessage("Coder Detail added successfully to coder with id : " + coderId);
				
		return resp;
	}
	
	@PostMapping("/coders/{coderId}/booksReferred")
	public ResponseObject addBookReferredToCoder(@RequestBody BookReferred booksReferred, @PathVariable int coderId,
											HttpServletResponse response) {
		
		ResponseObject resp = new ResponseObject();
		
		Coder coder = coderService.findCoderById(coderId);
		
		if(coder == null) {
			
			response.setStatus(HttpStatus.NOT_FOUND.value());
			resp.setMessage("Coder not found with id : " + coderId);
					
			return resp;
			
		}

		
		List<BookReferred> books = coderService.findAllBooks();
		
		
		for(BookReferred tempBook : books) {
						
			
			if(tempBook.getTitle().equals(booksReferred.getTitle())) {
				
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				resp.setMessage("Duplicate title not allowed");
						
				return resp;
				
			}
			
		}
		
		
				
		boolean isSaved = coderService.addBookReferredToCoder(booksReferred, coderId);
		
		if(isSaved) {
			response.setStatus(HttpStatus.OK.value());
			resp.setMessage("Book added successfully to coder with id : " + coderId);
		} else {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			resp.setMessage("No coder found with id : " + coderId);
		}
				
		return resp;
		
	}
	
	@PutMapping("/coders")
	public ResponseObject updateCoder(@RequestBody Coder coder, HttpServletResponse response) {
		
		boolean coderFound = coderService.updateCoder(coder);
		
		ResponseObject resp = new ResponseObject();
		
		if(!coderFound) {
			
			response.setStatus(HttpStatus.NOT_FOUND.value());
			
			resp.setMessage("Coder not found with id : " + coder.getId() );
			
			return resp;
		}
		
		response.setStatus(HttpStatus.OK.value());
		resp.setMessage("Coder with id : " + coder.getId() + " updated successfully");
				
		return resp;
	}
	
	@DeleteMapping("/coders/{coderId}")
	public ResponseObject deleteCoderById(@PathVariable int coderId, HttpServletResponse response) {
		
		ResponseObject resObj = new ResponseObject();
		
		boolean coderDeleted =  coderService.deleteCoderById(coderId);
		
		if(!coderDeleted) {
			
			response.setStatus(HttpStatus.NOT_FOUND.value());
			
			resObj.setMessage("Coder not found with id : " + coderId);
			
			return resObj;
			
		} 
		 
		response.setStatus(HttpStatus.OK.value());

		resObj.setMessage("Coder with id : " + coderId + " deleted successfully");
		
		return resObj;
	}

}
