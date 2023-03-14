package com.EarthSandwich.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotNull(message = "comment is required")
	@Size(max = 1000, min = 1, message = "comment should be between 1 to 1000 characters")
	@Column(name = "comment")
	private String comment;

	@NotNull
	@Column(name = "writer")
	private String writer;

	@NotNull
	@Column(name = "date")
	private LocalDateTime date;

	@NotNull
	@Column(name = "modified_date")
	private LocalDateTime m_date;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public int getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public String getWriter() {
		return writer;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public LocalDateTime getM_date() {
		return m_date;
	}

	public Post getPost() {
		return post;
	}

	public User getUser() {
		return user;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setM_date(LocalDateTime m_date) {
		this.m_date = m_date;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
