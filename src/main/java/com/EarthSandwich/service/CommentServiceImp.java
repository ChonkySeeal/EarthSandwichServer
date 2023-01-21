package com.EarthSandwich.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.EarthSandwich.dao.CommentDAO;
import com.EarthSandwich.dao.PostDAO;
import com.EarthSandwich.dao.UserDAO;
import com.EarthSandwich.dto.CommentListResponseDTO;
import com.EarthSandwich.dto.CommentRequestDTO;
import com.EarthSandwich.dto.CommentResponseDTO;
import com.EarthSandwich.entity.Comment;
import com.EarthSandwich.entity.Post;
import com.EarthSandwich.entity.User;

@Service
public class CommentServiceImp implements CommentService {

	private UserDAO userDAO;
	private PostDAO postDAO;
	private CommentDAO commentDAO;

	public CommentServiceImp(UserDAO userDAO, PostDAO postDAO, CommentDAO commentDAO) {
		this.userDAO = userDAO;
		this.postDAO = postDAO;
		this.commentDAO = commentDAO;
	}

	@Transactional
	@Override
	public CommentResponseDTO uploadComment(CommentRequestDTO comment, String email, int postId) {
		User user = userDAO.findByEmail(email);
		Post post = postDAO.findById(postId).orElse(null);
		if (user != null && post != null) {
			Comment tempComment = new Comment();
			tempComment.setComment(comment.getContent());
			LocalDateTime current = LocalDateTime.now();
			tempComment.setDate(current);
			tempComment.setM_date(current);
			tempComment.setId(0);
			tempComment.setWriter(user.getUsername());
			user.addComment(tempComment);
			post.addComment(tempComment);
			commentDAO.save(tempComment);
			CommentResponseDTO responseDTO = new CommentResponseDTO(tempComment.getId(), tempComment.getComment(),
					tempComment.getWriter(), tempComment.getM_date(), 0);
			return responseDTO;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unathorized user attempt");
		}

	}

	@Override
	public List<CommentResponseDTO> getAllComments(int postId) {
		List<Comment> tempComments = commentDAO.findAllByPost_Id(postId);
		if (tempComments.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}

		List<CommentResponseDTO> commentsList = new ArrayList<CommentResponseDTO>();
		for (Comment comment : tempComments) {
			CommentResponseDTO commentDTO = new CommentResponseDTO(comment.getId(), comment.getComment(),
					comment.getWriter(), comment.getM_date(), 0);
			commentsList.add(commentDTO);
		}

		return commentsList;
	}

	@Transactional
	@Override
	public void updateComment(CommentRequestDTO comment, String email, int commentId) {
		User user = userDAO.findByEmail(email);
		Comment oldComment = commentDAO.findById(commentId).orElse(null);
		if (user != null && oldComment != null && user.getUsername().equals(oldComment.getWriter())) {
			LocalDateTime current = LocalDateTime.now();
			oldComment.setComment(comment.getContent());
			oldComment.setM_date(current);
			commentDAO.save(oldComment);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unathorized user attempt");
		}

	}

	@Override
	public void deleteComment(CommentRequestDTO comment, String email, int commentId) {
		User user = userDAO.findByEmail(email);
		Comment oldComment = commentDAO.findById(commentId).orElse(null);
		if (user != null && oldComment != null && user.getUsername().equals(oldComment.getWriter())) {
			commentDAO.deleteById(commentId);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unathorized user attempt");
		}

	}

	@Override
	public CommentListResponseDTO getAllCommentsByWriter(String writer, int pageNumber) {
		Pageable commentPages = PageRequest.of(pageNumber, 16, Sort.by("date").descending());
		Page<Comment> paginationResult = commentDAO.findAllByUser_Username(commentPages, writer);
		List<Comment> comments = paginationResult.getContent();
		if (comments.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}

		List<CommentResponseDTO> commentsList = new ArrayList<CommentResponseDTO>();
		for (Comment comment : comments) {
			CommentResponseDTO commentDTO = new CommentResponseDTO(comment.getId(), comment.getComment(),
					comment.getWriter(), comment.getM_date(), comment.getPost().getId());
			commentsList.add(commentDTO);
		}
		CommentListResponseDTO commentListDTO = new CommentListResponseDTO(paginationResult.getTotalPages() - 1,
				commentsList);

		return commentListDTO;

	}

}
