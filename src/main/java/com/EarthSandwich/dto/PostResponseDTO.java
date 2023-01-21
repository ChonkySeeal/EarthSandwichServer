package com.EarthSandwich.dto;

import java.time.LocalDateTime;

public class PostResponseDTO {

	private int id;

	private String title;

	private String writer;

	private String content;

	private String picture;

	private int latitude;

	private int longitude;

	private PostResponseDTO linkedPost;

	private LocalDateTime date;

	public PostResponseDTO(int id, String title, String writer, String content, String picture, int latitude,
			int longitude, PostResponseDTO linkedPost, LocalDateTime date) {
		this.id = id;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.picture = picture;
		this.latitude = latitude;
		this.longitude = longitude;
		this.linkedPost = linkedPost;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public String getPicture() {
		return picture;
	}

	public int getLatitude() {
		return latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public PostResponseDTO getLinkedPost() {
		return linkedPost;
	}

	public LocalDateTime getDate() {
		return date;
	}

}
