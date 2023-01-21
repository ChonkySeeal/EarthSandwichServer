package com.EarthSandwich.service;

import java.util.List;

import com.EarthSandwich.dto.CommentListResponseDTO;
import com.EarthSandwich.dto.CommentRequestDTO;
import com.EarthSandwich.dto.CommentResponseDTO;

public interface CommentService {
	public CommentResponseDTO uploadComment(CommentRequestDTO comment, String email, int postId);

	public List<CommentResponseDTO> getAllComments(int postId);

	public CommentListResponseDTO getAllCommentsByWriter(String writer, int pageNumber);

	public void updateComment(CommentRequestDTO comment, String email, int commentId);

	public void deleteComment(CommentRequestDTO comment, String email, int commentId);

}
