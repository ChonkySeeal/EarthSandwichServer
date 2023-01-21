package com.EarthSandwich.rest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EarthSandwich.dto.CommentRequestDTO;
import com.EarthSandwich.dto.CommentResponseDTO;
import com.EarthSandwich.dto.EditPostRequestDTO;
import com.EarthSandwich.dto.PasswordRequestDTO;
import com.EarthSandwich.dto.PostRequestDTO;
import com.EarthSandwich.dto.UserResponseDTO;
import com.EarthSandwich.service.CommentService;
import com.EarthSandwich.service.PostService;
import com.EarthSandwich.service.UserService;

@RestController
@RequestMapping("/member")
public class MemberRestController {

	private PostService postService;

	private UserService userService;

	private CommentService commentService;

	@Autowired
	public MemberRestController(PostService postService, UserService userService, CommentService commentService) {
		this.postService = postService;
		this.userService = userService;
		this.commentService = commentService;
	}

	@PostMapping("/post")
	public void submitPost(PostRequestDTO post, @AuthenticationPrincipal User user) {
		postService.uploadPost(post, user.getUsername());
	}

	@PutMapping("/post/{postId}")
	public void updatePost(EditPostRequestDTO post, @PathVariable int postId, @AuthenticationPrincipal User user) {

		postService.updatePost(post, postId, user.getUsername());
	}

	@DeleteMapping("/post/{postId}")
	public void deltePost(@PathVariable int postId, @AuthenticationPrincipal User user) {
		postService.deletePost(postId, user.getUsername());
	}

	@PostMapping("/post/{postId}/comment")
	public CommentResponseDTO submitComment(CommentRequestDTO comment, @AuthenticationPrincipal User user,
			@PathVariable int postId) {

		return commentService.uploadComment(comment, user.getUsername(), postId);
	}

	@PutMapping("/post/{postId}/{commentId}")
	public void updateComment(CommentRequestDTO comment, @AuthenticationPrincipal User user,
			@PathVariable int commentId) {
		commentService.updateComment(comment, user.getUsername(), commentId);
	}

	@DeleteMapping("/post/{postId}/{commentId}")
	public void deleteComment(CommentRequestDTO comment, @AuthenticationPrincipal User user,
			@PathVariable int commentId) {
		commentService.deleteComment(comment, user.getUsername(), commentId);
	}

	@GetMapping("/logout")
	public void logoutMember(HttpServletResponse response) {
		Cookie[] cookies = userService.logoutUser();
		response.addCookie(cookies[0]);
		response.addCookie(cookies[1]);
	}

	@GetMapping("/account")
	public UserResponseDTO getUser(@AuthenticationPrincipal User user) {
		com.EarthSandwich.entity.User existUser = userService.findByEmail(user.getUsername());
		UserResponseDTO dto = new UserResponseDTO(existUser.getEmail(), existUser.getUsername());
		return dto;
	}

	@PutMapping("/account")
	public void changePassword(@AuthenticationPrincipal User user, PasswordRequestDTO dto) {
		userService.changePassword(dto.getOldpassword(), dto.getNewpassword(), user.getUsername());
	}

	@DeleteMapping("/account")
	public void deleteAccount(@AuthenticationPrincipal User user, HttpServletResponse response) {
		Cookie[] cookies = userService.deleteAccount(user.getUsername());
		response.addCookie(cookies[0]);
		response.addCookie(cookies[1]);
	}

}
