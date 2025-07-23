package com.fastturtle.hibernateallmappingsspringboot.service;

import java.util.List;
import java.util.Optional;

import com.fastturtle.hibernateallmappingsspringboot.repository.CoderRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;

@Service
public class CoderServiceImpl implements CoderService {

	private final CoderRepository coderRepository;

	@Autowired
	public CoderServiceImpl(CoderRepository coderRepository) {
        this.coderRepository = coderRepository;
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
	public List<BookReferred> findBooksReferredByCoder(int coderId) {
		return coderRepository.findBooksReferredByCoder(coderId);
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
	public CoderDetail fetchCoderDetailForCoder(int coderId) {
		return coderRepository.findCoderDetailForCoder(coderId);
	}

}
