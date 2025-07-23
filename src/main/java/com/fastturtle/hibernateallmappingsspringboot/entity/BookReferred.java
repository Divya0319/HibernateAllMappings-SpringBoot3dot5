package com.fastturtle.hibernateallmappingsspringboot.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.*;

@Entity
@JsonInclude(Include.NON_NULL)
public class BookReferred {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="coder_id")
	private Coder coder;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="book_id")
	private List<BookReview> bookReviews;
	
	
	@ManyToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="bookreferred_designer",
				joinColumns=@JoinColumn(name="book_id"),
				inverseJoinColumns=@JoinColumn(name="designer_id")
	)
	private List<Designer> designers;

	private String thumbnailUrl;
	
	public BookReferred() {
		
	}

	public BookReferred(String title, String thumbnailUrl) {
		this.title = title;
		this.thumbnailUrl = thumbnailUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Coder getCoder() {
		return coder;
	}

	public void setCoder(Coder coder) {
		this.coder = coder;
	}
	

	public List<BookReview> getBookReviews() {
		return bookReviews;
	}

	public void setBookReviews(List<BookReview> bookReviews) {
		this.bookReviews = bookReviews;
	}
	
	public void addBookReview(BookReview tempReview) {
		
		if(bookReviews == null) {
			
			bookReviews = new ArrayList<BookReview>();
		}
		
		bookReviews.add(tempReview);
		
	}

	public List<Designer> getDesigners() {
		return designers;
	}

	public void setDesigners(List<Designer> designers) {
		this.designers = designers;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	@Override
	public String toString() {
		return "BooksReferred \n[\nid=" + id + ", title=" + title +  "\n]";
	}
	
	

}
