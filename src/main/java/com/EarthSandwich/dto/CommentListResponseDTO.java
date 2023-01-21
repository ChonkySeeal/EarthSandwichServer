package com.EarthSandwich.dto;

import java.util.List;

public class CommentListResponseDTO {
	private int totalPageNumber;

	private List<CommentResponseDTO> commentList;

	public CommentListResponseDTO(int totalPageNumber, List<CommentResponseDTO> commentList) {

		this.totalPageNumber = totalPageNumber;
		this.commentList = commentList;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public List<CommentResponseDTO> getCommentList() {
		return commentList;
	}

}
