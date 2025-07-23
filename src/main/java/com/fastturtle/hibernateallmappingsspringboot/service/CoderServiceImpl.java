package com.fastturtle.hibernateallmappingsspringboot.service;

import java.util.List;
import java.util.Optional;

import com.fastturtle.hibernateallmappingsspringboot.repository.BookReferredRepository;
import com.fastturtle.hibernateallmappingsspringboot.repository.CoderDetailRepository;
import com.fastturtle.hibernateallmappingsspringboot.repository.CoderRepository;
import com.fastturtle.hibernateallmappingsspringboot.repository.DesignerRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.BookReview;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;
import com.fastturtle.hibernateallmappingsspringboot.entity.Designer;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoderServiceImpl implements CoderService {

	private final CoderRepository coderRepository;
	private final BookReferredRepository bookReferredRepository;
	private final CoderDetailRepository coderDetailRepository;
	private final DesignerRepository designerRepository;

	@Autowired
	public CoderServiceImpl(CoderRepository coderRepository, BookReferredRepository bookReferredRepository, CoderDetailRepository coderDetailRepository, DesignerRepository designerRepository) {
        this.coderRepository = coderRepository;
        this.bookReferredRepository = bookReferredRepository;
        this.coderDetailRepository = coderDetailRepository;
        this.designerRepository = designerRepository;
    }

	@Override
	public void addCoder(Coder theCoder) {
		coderRepository.save(theCoder);
	}

	@Override
	public boolean addCoderDetailToCoder(CoderDetail coderDetail, int coderId) {
		Optional<Coder> coderOptional = coderRepository.findById(coderId);

		if(coderOptional.isEmpty()) {
			return false;
		}

		Coder coder = coderOptional.get();
		coder.setCoderDetail(coderDetail);
		coderRepository.save(coder);
		return true;

	}

	@Override
	public boolean addBookReferredToCoder(BookReferred booksReferred, int coderId) {
		Optional<Coder> coderOptional = coderRepository.findById(coderId);

		if(coderOptional.isEmpty()) {
			return false;
		}

		Coder coder = coderOptional.get();
		coder.addBook(booksReferred);
		coderRepository.save(coder);
		return true;
	}
	
	@Override
//	@PreAuthorize("hasAuthority('SCOPE_access.coders')")
	public List<Coder> fetchAllCoders() {
		return coderRepository.findAll();
	}

	@Override
	public List<BookReferred> findAllBooks() {
		return bookReferredRepository.findAll();
	}
	
	@Override
	public Coder findCoderById(int theId) {
		Optional<Coder> coderOptional = coderRepository.findById(theId);
        return coderOptional.orElse(null);
    }

	@Override
	public boolean deleteCoderById(int coderId) {

		Optional<Coder> coderOptional = coderRepository.findById(coderId);

		if(coderOptional.isEmpty()) {
			return false;
		}

		coderRepository.deleteById(coderId);
		return true;
	}

	@Override
	public boolean deleteBookById(int bookId) {
		Optional<BookReferred> bookReferredOptional = bookReferredRepository.findById(bookId);

		if(bookReferredOptional.isEmpty()) {
			return false;
		}

		bookReferredRepository.deleteById(bookId);
		return true;
	}

	@Override
	public List<BookReferred> findBooksReferredByCoder(int coderId) {
		return coderRepository.findBooksReferredByCoder(coderId);
	}

	@Override
	public Coder findCoderOfBookById(int bookId) {
		return bookReferredRepository.findCoderOfBookById(bookId);
	}

	@Override
	@Transactional
	public boolean deleteCoderDetailById(int coderDetailId) {
		Optional<CoderDetail> coderDetailOptional = coderDetailRepository.findById(coderDetailId);

		if(coderDetailOptional.isEmpty()) {
			return false;
		}

		CoderDetail coderDetail = coderDetailOptional.get();
		Coder coder = coderDetail.getCoder();

		if(coder != null) {
			coder.setCoderDetail(null);    // this is required to remove backward association from coderDetail to coder
		}

		coderDetailRepository.delete(coderDetail);

		return true;
	}

	@Override
	public boolean updateCoder(Coder coder) {

		Optional<Coder> existingOpt = coderRepository.findById(coder.getId());

		if(existingOpt.isEmpty()) {
			return false;
		}

		Coder existing = existingOpt.get();

		existing.setFirstName(coder.getFirstName());
		existing.setLastName(coder.getLastName());
		existing.setAge(coder.getAge());
		existing.setEmail(coder.getEmail());

		// Keep existing associations intact unless updated
		// existing.setCoderDetail(...); only if changing coderDetail
		// existing.setBooksReferred(...); only if updating references

		coderRepository.save(coder);
		return true;
	}

	@Override
	public Coder findCoderForCoderDetailById(int coderDetailId) {
		return coderDetailRepository.findCoderForCoderDetailById(coderDetailId);
	}

	@Override
	public CoderDetail fetchCoderDetailForCoder(int coderId) {
		return coderRepository.findCoderDetailForCoder(coderId);
	}

	@Override
	public BookReferred findBookById(int bookId) {
		Optional<BookReferred> bookReferredOptional = bookReferredRepository.findById(bookId);
        return bookReferredOptional.orElse(null);
    }

	@Override
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

	@Override
	public List<BookReview> findReviewsForBook(int bookId) {
		return bookReferredRepository.findBookReviewsOfBookById(bookId);
	}

	@Override
	public void addDesigner(Designer designer) {
		designerRepository.save(designer);
		
	}

	@Override
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

	@Override
	public List<BookReferred> findAllBooksForDesigner(int dId) {
		return designerRepository.findAllBooksForDesigner(dId);
	}
	
	@Override
	public List<Designer> findAllDesignersForBook(int bId) {
		return bookReferredRepository.findDesignersOfBookById(bId);
	}

	@Override
	public boolean deleteDesignerById(int dId) {
		Optional<Designer> designerOptional = designerRepository.findById(dId);

		if(designerOptional.isEmpty()) {
			return false;
		}

		designerRepository.deleteById(dId);
		return true;
	}
	
	@Override
	public List<Designer> fetchAllDesigners() {
		return designerRepository.findAll();
	}

	@Override
	public Designer findDesignerById(int designerId) {
		Optional<Designer> designerOptional = designerRepository.findById(designerId);
        return designerOptional.orElse(null);
    }

}
