package com.EarthSandwich.dto;

import java.time.LocalDateTime;

public class CommentResponseDTO {

	private int id;
	private String content;
	private String writer;
	private LocalDateTime date;
	private int postId;

	public CommentResponseDTO(int id, String content, String writer, LocalDateTime date, int postId) {

		this.id = id;
		this.content = content;
		this.writer = writer;
		this.date = date;
		this.postId = postId;
	}

	public int getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getWriter() {
		return writer;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public int getPostId() {
		return postId;
	}

}
