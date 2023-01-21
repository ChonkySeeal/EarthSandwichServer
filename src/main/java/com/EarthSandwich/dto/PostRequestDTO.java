package com.EarthSandwich.dto;

import org.springframework.web.multipart.MultipartFile;

public class PostRequestDTO {

	private String title;
	private String content;
	private MultipartFile picture;
	private int latitude;
	private int longitude;
	private int linkedPost;

	public PostRequestDTO(String title, String content, MultipartFile picture, int latitude, int longitude,
			int linkedPost) {
		this.title = title;
		this.content = content;
		this.picture = picture;
		this.latitude = latitude;
		this.longitude = longitude;
		this.linkedPost = linkedPost;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public MultipartFile getPicture() {
		return picture;
	}

	public int getLatitude() {
		return latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public int getLinkedPost() {
		return linkedPost;
	}

}
