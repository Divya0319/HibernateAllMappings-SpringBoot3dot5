package com.fastturtle.hibernateallmappingsspringboot.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="book_review")
public class BookReview {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="comment")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "coder_id")
	private Coder coder;

	@ManyToOne
	@JoinColumn(name = "designer_id")
	private Designer designer;

	@Column(name = "created_at")
	private final LocalDateTime createdAt = LocalDateTime.now();
	
	public BookReview() {
		
	}
	
	public BookReview(String comment) {
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Transient
	public Reviewer getReviewer() {
		return coder != null ? coder : designer;
	}

	public void setReviewer(Reviewer reviewer) {
		if (reviewer instanceof Coder) {
			this.coder = (Coder) reviewer;
			this.designer = null; // ensure mutual exclusivity
		} else if (reviewer instanceof Designer) {
			this.designer = (Designer) reviewer;
			this.coder = null;
		} else {
			throw new IllegalArgumentException("Reviewer must be either a Coder or Designer");
		}
	}

	@Override
	public String toString() {
		return "\nBookReview \n[id=" + id + ", comment=" + comment + "]\n";
	}
	

}
