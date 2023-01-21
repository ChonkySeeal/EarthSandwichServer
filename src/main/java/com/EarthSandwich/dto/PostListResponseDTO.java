package com.EarthSandwich.dto;

import java.util.List;

public class PostListResponseDTO {
	private int totalPageNumber;

	private List<PostResponseDTO> postLists;

	public PostListResponseDTO(int totalPageNumber, List<PostResponseDTO> postLists) {

		this.totalPageNumber = totalPageNumber;
		this.postLists = postLists;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public List<PostResponseDTO> getPostLists() {
		return postLists;
	}

}
