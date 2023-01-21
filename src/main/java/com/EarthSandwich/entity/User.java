package com.EarthSandwich.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotNull(message = "username is required")
	@Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$", message = "username must start with letter and needs to be between 5-12 characters long")
	@Column(name = "username")
	private String username;

	@NotNull(message = "password is required")
	@Column(name = "password")
	private String password;

	@NotNull(message = "email is required")
	@Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}", message = "must follow proper email format")
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "date")
	private LocalDateTime date;

	@NotNull
	@Column(name = "modified_date")
	private LocalDateTime modified_date;

	@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private Set<Post> posts;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Comment> comments;

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalDateTime getModified_date() {
		return modified_date;
	}

	public void setModified_date(LocalDateTime modified_date) {
		this.modified_date = modified_date;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void addPost(Post tempPost) {

		if (posts == null) {
			posts = new HashSet<>();
		}

		posts.add(tempPost);

		tempPost.setUser(this);
	}

	public void addComment(Comment tempComment) {
		if (comments == null)
			comments = new HashSet<>();

		comments.add(tempComment);

		tempComment.setUser(this);

	}

}
