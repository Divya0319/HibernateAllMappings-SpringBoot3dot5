package com.fastturtle.hibernateallmappingsspringboot.service;

import java.util.List;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReview;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;
import com.fastturtle.hibernateallmappingsspringboot.entity.Designer;

public interface CoderService {
	
	public void addCoder(Coder theCoder);
	
	public boolean addCoderDetailToCoder(CoderDetail coderDetail, int coderId);

	public boolean addBookReferredToCoder(BookReferred booksReferred, int coderId);
	
	public List<Coder> fetchAllCoders();
	
	public Coder findCoderById(int theId);

	public List<BookReferred> findAllBooks();

	public boolean deleteCoderById(int coderId);

	public boolean deleteBookById(int bookId);
	
	public List<BookReferred> findBooksReferredByCoder(int coderId);

	public Coder findCoderOfBookById(int bookId);

	public boolean deleteCoderDetailById(int coderDetailId);

	public boolean updateCoder(Coder coder);

	public Coder findCoderForCoderDetailById(int coderDetailId);

	public CoderDetail fetchCoderDetailForCoder(int coderId);

	public BookReferred findBookById(int bookId);

	public boolean addBookReview(BookReview bookReview, int bookId);

	public List<BookReview> findReviewsForBook(int bookId);

	public void addDesigner(Designer designer);
	
	public boolean addBookToDesigner(BookReferred bookReferred, int designerId);

	public List<BookReferred> findAllBooksForDesigner(int dId);

	public List<Designer> findAllDesignersForBook(int bId);

	public boolean deleteDesignerById(int dId);
	
	public List<Designer> fetchAllDesigners();

	public Designer findDesignerById(int designerId);


}
