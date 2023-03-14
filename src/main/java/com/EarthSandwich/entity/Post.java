package com.EarthSandwich.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotNull(message = "title is required")
	@Size(max = 100, min = 2, message = "title should be beween 2 to 100 characters")
	@Column(name = "title")
	private String title;

	@Size(max = 1000, message = "content should not exceed 1000 characters")
	@Column(name = "content")
	private String content;

	@NotNull
	@Column(name = "writer")
	private String writer;

	@NotNull(message = "picture is required")
	@Column(name = "picture")
	private String picture;

	@NotNull(message = "coordinate is required")
	@Column(name = "latitude")
	private int latitude;

	@NotNull(message = "coordinate is required")
	@Column(name = "longitude")
	private int longitude;

	@NotNull
	@Column(name = "date")
	private LocalDateTime date;

	@NotNull
	@Column(name = "modified_date")
	private LocalDateTime m_date;

	@NotNull
	@Column(name = "isparent")
	private boolean isParent;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "linkedpost", referencedColumnName = "id", nullable = true)
	private Post linkedPost;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private Set<Comment> comments;

	public Post() {

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalDateTime getM_date() {
		return m_date;
	}

	public void setM_date(LocalDateTime m_date) {
		this.m_date = m_date;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean b) {
		this.isParent = b;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getLinkedPost() {
		return linkedPost;
	}

	public void setLinkedPost(Post linkedPost) {
		this.linkedPost = linkedPost;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment tempComment) {
		if (comments == null)
			comments = new HashSet<>();

		comments.add(tempComment);

		tempComment.setPost(this);

	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + ", writer=" + writer + ", picture="
				+ picture + ", latitude=" + latitude + ", longitude=" + longitude + ", date=" + date + ", m_date="
				+ m_date + ", isParent=" + isParent + ", user=" + user + ", linkedPost=" + linkedPost + ", comments="
				+ comments + "]";
	}

}
