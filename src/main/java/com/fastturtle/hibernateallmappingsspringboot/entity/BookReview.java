package com.fastturtle.hibernateallmappingsspringboot.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
	private LocalDateTime createdAt;

	@Transient
	private ZonedDateTime createdAtWithZone;
	
	public BookReview() {
		
	}
	
	public BookReview(String comment, LocalDateTime createdAt) {
		this.comment = comment;
		this.createdAt = createdAt;
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

	public ZonedDateTime getCreatedAtWithZone() {
		return createdAtWithZone;
	}

	@PostLoad
	private void initTimezoneFields() {
		if (createdAt != null) {
			this.createdAtWithZone = createdAt
					.atZone(ZoneId.of("UTC"))
					.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
		}
	}

	@Override
	public String toString() {
		return "\nBookReview \n[id=" + id + ", comment=" + comment + "]\n";
	}
	

}
