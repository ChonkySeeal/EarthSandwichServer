package com.EarthSandwich.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EarthSandwich.dto.CommentListResponseDTO;
import com.EarthSandwich.dto.CommentResponseDTO;
import com.EarthSandwich.dto.PostListResponseDTO;
import com.EarthSandwich.dto.PostResponseDTO;
import com.EarthSandwich.service.CommentService;
import com.EarthSandwich.service.PostService;

@RestController
@RequestMapping("/board")
public class PostRestController {

	private PostService postService;

	private CommentService commentService;

	@Autowired
	public PostRestController(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
	}

	@GetMapping("/postlist/{pageNumber}")
	public ResponseEntity<PostListResponseDTO> getPostList(@PathVariable int pageNumber) {

		return ResponseEntity.ok().body(postService.paginatePost(pageNumber));

	}

	@GetMapping("/postlist/user/{writer}/{pageNumber}")
	public ResponseEntity<PostListResponseDTO> getPostListByWriter(@PathVariable String writer,
			@PathVariable int pageNumber) {

		return ResponseEntity.ok().body(postService.paginatePostByWriter(writer, pageNumber));

	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<PostResponseDTO> getSinglePost(@PathVariable int postId) {

		return ResponseEntity.ok().body(postService.getPostById(postId));
	}

	@GetMapping("/post/{postId}/comment")
	public List<CommentResponseDTO> getCommentList(@PathVariable int postId) {

		return commentService.getAllComments(postId);

	}

	@GetMapping("/commentlist/user/{writer}/{pageNumber}")
	public ResponseEntity<CommentListResponseDTO> getCommentListByWriter(@PathVariable String writer,
			@PathVariable int pageNumber) {

		return ResponseEntity.ok().body(commentService.getAllCommentsByWriter(writer, pageNumber));

	}

}
