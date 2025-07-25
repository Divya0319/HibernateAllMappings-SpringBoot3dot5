package com.fastturtle.hibernateallmappingsspringboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastturtle.hibernateallmappingsspringboot.dao.CoderDAO;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReview;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;
import com.fastturtle.hibernateallmappingsspringboot.entity.Designer;

@Service
public class CoderServiceImpl implements CoderService {
	
	private CoderDAO coderDao;

	@Autowired
	public CoderServiceImpl(CoderDAO coderDao) {
		this.coderDao = coderDao;
	}

	@Override
	@Transactional
	public void addCoder(Coder theCoder) {
		coderDao.addCoder(theCoder);
	}

	@Override
	@Transactional
	public boolean addCoderDetailToCoder(CoderDetail coderDetail, int coderId) {
		return coderDao.addCoderDetailToCoder(coderDetail, coderId);
	}

	@Override
	@Transactional
	public void addBookReferredToCoder(BookReferred booksReferred, int coderId) {
		coderDao.addBookReferredToCoder(booksReferred, coderId);
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasAuthority('SCOPE_access.coders')")
	public List<Coder> fetchAllCoders() {
		return coderDao.fetchAllCoders();
	}

	@Override
	@Transactional
	public List<BookReferred> findAllBooks() {
		return coderDao.findAllBooks();
	}
	
	@Override
	@Transactional
	public Coder findCoderById(int theId) {
		return coderDao.findCoderById(theId);
	}

	@Override
	@Transactional
	public boolean deleteCoderById(int coderId) {

		return coderDao.deleteCoderById(coderId);
	}

	@Override
	@Transactional
	public boolean deleteBookById(int bookId) {
		return coderDao.deleteBookById(bookId);
	}

	@Override
	@Transactional
	public List<BookReferred> findBooksReferredByCoder(int coderId) {
		return coderDao.findBooksReferredByCoder(coderId);
	}

	@Override
	@Transactional
	public Coder findCoderOfBookById(int bookId) {
		return coderDao.findCoderOfBookById(bookId);
	}

	@Override
	@Transactional
	public boolean deleteCoderDetailById(int coderDetailId) {
		return coderDao.deleteCoderDetailById(coderDetailId);
	}

	@Override
	@Transactional
	public boolean updateCoder(Coder coder) {
		return coderDao.updateCoder(coder);
	}

	@Override
	@Transactional
	public Coder findCoderForCoderDetailById(int coderDetailId) {
		return coderDao.findCoderForCoderDetailById(coderDetailId);
	}

	@Override
	@Transactional
	public CoderDetail fetchCoderDetailForCoder(int coderId) {
		return coderDao.fetchCoderDetailForCoder(coderId);
	}

	@Override
	@Transactional
	public BookReferred findBookById(int bookId) {
		return coderDao.findBookById(bookId);
	}

	@Override
	@Transactional
	public boolean addBookReview(BookReview bookReview, int bookId) {
		return coderDao.addBookReview(bookReview, bookId);
	}

	@Override
	@Transactional
	public List<BookReview> findReviewsForBook(int bookId) {
		return coderDao.findReviewsForBook(bookId);
	}

	@Override
	@Transactional
	public void addDesigner(Designer designer) {
		coderDao.addDesigner(designer);
		
	}

	@Override
	@Transactional
	public boolean addBookToDesigner(BookReferred bookReferred, int designerId) {
		return coderDao.addBookToDesigner(bookReferred, designerId);
	}

	@Override
	@Transactional
	public List<BookReferred> findAllBooksForDesigner(int dId) {
		return coderDao.findAllBooksForDesigner(dId);
	}
	
	@Override
	@Transactional
	public List<Designer> findAllDesignersForBook(int bId) {
		return coderDao.findAllDesignersForBook(bId);
	}

	@Override
	@Transactional
	public boolean deleteDesignerById(int dId) {
		return coderDao.deleteDesignerById(dId);
	}
	
	@Override
	@Transactional
	public List<Designer> fetchAllDesigners() {
		return coderDao.fetchAllDesigners();
	}

	@Override
	@Transactional
	public Designer findDesignerById(int designerId) {
		return coderDao.findDesignerById(designerId);
	}

}
