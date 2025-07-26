package com.fastturtle.hibernateallmappingsspringboot.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.*;

@Entity
@JsonInclude(Include.NON_NULL)
public class Designer implements Reviewer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;

	private String password;
	
	@ManyToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="bookreferred_designer",
				joinColumns=@JoinColumn(name="designer_id"),
				inverseJoinColumns=@JoinColumn(name="book_id")
	)
	private List<BookReferred> booksReferred;

	private String profilePicUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	public List<BookReferred> getBooksReferred() {
		return booksReferred;
	}

	public void setBooksReferred(List<BookReferred> booksReferred) {
		this.booksReferred = booksReferred;
	}
	
	public void addBook(BookReferred bookReferred) {
		
		if(booksReferred == null) {
			
			booksReferred = new ArrayList<>();
		}
		
		booksReferred.add(bookReferred);
		

	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	@Override
	public String toString() {
		return "\nDesigner [\nid=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "\n]";
	}
	
	

}
