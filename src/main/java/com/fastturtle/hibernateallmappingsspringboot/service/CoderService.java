package com.fastturtle.hibernateallmappingsspringboot.service;

import java.util.List;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;

public interface CoderService {
	
	void addCoder(Coder theCoder);
	
	boolean addCoderDetailToCoder(CoderDetail coderDetail, int coderId);

	boolean addBookReferredToCoder(BookReferred booksReferred, int coderId);
	
	List<Coder> fetchAllCoders();
	
	Coder findCoderById(int theId);

	boolean deleteCoderById(int coderId);

	List<BookReferred> findBooksReferredByCoder(int coderId);


	boolean updateCoder(Coder coder);

	CoderDetail fetchCoderDetailForCoder(int coderId);


}
