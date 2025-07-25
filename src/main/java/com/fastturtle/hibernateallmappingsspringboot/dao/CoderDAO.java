package com.fastturtle.hibernateallmappingsspringboot.dao;

import com.fastturtle.hibernateallmappingsspringboot.entity.*;

import java.util.List;

public interface CoderDAO {
	
	public void addCoder(Coder theCoder);
	
	public boolean addCoderDetailToCoder(CoderDetail coderDetail, int coderId);

	public void addBookReferredToCoder(BookReferred booksReferred, int coderId);
	
	public List<Coder> fetchAllCoders();
	
	public Coder findCoderById(int theId);

	public List<BookReferred> findAllBooks();

	public boolean deleteCoderById(int coderId);

	public boolean deleteBookById(int bookId);
	
	public List<BookReferred> findBooksReferredByCoder(int coderId);

	public Coder findCoderOfBookById(int bookId);

	public boolean updateCoder(Coder coder);

	public Coder findCoderForCoderDetailById(int coderDetailId);

	public CoderDetail fetchCoderDetailForCoder(int coderId);

	public boolean deleteCoderDetailById(int coderDetailId);

	public BookReferred findBookById(int bookId);

	public boolean addBookReview(BookReview bookReview, int bookId);

	public List<BookReview> findReviewsForBook(int bookId);

	public void addDesigner(Designer designer);

	public boolean addBookToDesigner(BookReferred bookReferred, int designerId);
	
	public List<Designer> fetchAllDesigners();

	public Designer findDesignerById(int designerId);

	public List<BookReferred> findAllBooksForDesigner(int dId);

	public List<Designer> findAllDesignersForBook(int bId);

	public boolean deleteDesignerById(int dId);

}
