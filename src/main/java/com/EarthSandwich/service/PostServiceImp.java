package com.EarthSandwich.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.EarthSandwich.dao.PostDAO;
import com.EarthSandwich.dto.EditPostRequestDTO;
import com.EarthSandwich.dto.PostListResponseDTO;
import com.EarthSandwich.dto.PostRequestDTO;
import com.EarthSandwich.dto.PostResponseDTO;
import com.EarthSandwich.entity.Post;
import com.EarthSandwich.entity.User;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;

@Service
public class PostServiceImp implements PostService {

	private PostDAO postDAO;

	private AmazonS3 amazonS3;

	private UserService userService;

	private int pageSize = 6;

	@Value("${aws.bucketname}")
	private String bucketName;
	@Value("${aws.path}")
	private String pathName;

	@Value("${aws.bucket.friendlyURL}")
	private String friendlyURL;

	@Autowired
	public PostServiceImp(PostDAO postDAO, AmazonS3 amazonS3, UserService userService) {
		this.postDAO = postDAO;
		this.amazonS3 = amazonS3;
		this.userService = userService;
	}

	@Transactional
	@Override
	public void uploadPost(PostRequestDTO postDTO, String email) {

		if (postDTO.getPicture().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot upload empty file");
		}

		if (postDTO.getPicture().getSize() > 50000) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "image cannot be more than 50KB");
		}

		if (!postDTO.getPicture().getContentType().startsWith("image")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uploaded file is not an image");
		}

		if (postDTO.getTitle() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title should not be empty");
		}

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(postDTO.getPicture().getSize());
		objectMetadata.setContentType(postDTO.getPicture().getContentType());
		final String imageId = String.format("%s", UUID.randomUUID());
		final String path = String.format("%s/%s", bucketName, pathName);
		final User user = userService.findByEmail(email);
		try {
			PutObjectResult putObjectResult = amazonS3.putObject(path, imageId, postDTO.getPicture().getInputStream(),
					objectMetadata);
			if (putObjectResult != null) {
				Post post = new Post();
				post.setTitle(postDTO.getTitle());
				post.setContent(postDTO.getContent());
				LocalDateTime current = LocalDateTime.now();
				post.setDate(current);
				post.setM_date(current);
				post.setId(0);
				if (postDTO.getLinkedPost() == 0) {
					post.setLinkedPost(null);
					post.setIsParent(true);
				} else {
					Post parentPost = postDAO.getReferenceById(postDTO.getLinkedPost());
					parentPost.setLinkedPost(post);
					post.setLinkedPost(parentPost);
					post.setIsParent(false);
				}

				post.setPicture(imageId);
				post.setLatitude(postDTO.getLatitude());
				post.setLongitude(postDTO.getLongitude());
				post.setWriter(user.getUsername());
				user.addPost(post);
				postDAO.save(post);
			} else {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to save post");
			}

		} catch (AmazonServiceException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not able to make post");
		} catch (SdkClientException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not able to make post");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not able to make post");
		}

	}

	@Override
	public PostListResponseDTO paginatePost(int pageNumber) {
		Pageable postPages = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());
		Page<Post> paginationResult = postDAO.findAllByIsParentTrue(postPages);
		List<Post> posts = paginationResult.getContent();
		if (posts.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}

		List<PostResponseDTO> postlists = new ArrayList<PostResponseDTO>();
		for (Post post : posts) {

			if (post.getLinkedPost() != null) {
				final String stringUrl = String.format("%s/%s", friendlyURL, post.getPicture());
				final String stringUrlLinkedPost = String.format("%s/%s", friendlyURL,
						post.getLinkedPost().getPicture());
				PostResponseDTO tempChildPost = new PostResponseDTO(post.getLinkedPost().getId(),
						post.getLinkedPost().getTitle(), post.getLinkedPost().getWriter(),
						post.getLinkedPost().getContent(), stringUrlLinkedPost, post.getLinkedPost().getLatitude(),
						post.getLinkedPost().getLongitude(), null, post.getLinkedPost().getM_date());
				PostResponseDTO tempParentPost = new PostResponseDTO(post.getId(), post.getTitle(), post.getWriter(),
						post.getContent(), stringUrl, post.getLatitude(), post.getLongitude(), tempChildPost,
						post.getM_date());
				postlists.add(tempParentPost);
			} else {
				final String stringUrl = String.format("%s/%s", friendlyURL, post.getPicture());

				PostResponseDTO tempPost = new PostResponseDTO(post.getId(), post.getTitle(), post.getWriter(),
						post.getContent(), stringUrl, post.getLatitude(), post.getLongitude(), null, post.getM_date());
				postlists.add(tempPost);
			}

		}
		PostListResponseDTO postListDTO = new PostListResponseDTO(paginationResult.getTotalPages() - 1, postlists);
		return postListDTO;
	}

	@Override
	public PostResponseDTO getPostById(int id) {

		Post post = postDAO.getReferenceById(id);
		if (post.getLinkedPost() != null) {

			final String stringUrl = String.format("%s/%s", friendlyURL, post.getPicture());
			final String stringUrlLinkedPost = String.format("%s/%s", friendlyURL, post.getLinkedPost().getPicture());
			PostResponseDTO tempChildPost = new PostResponseDTO(post.getLinkedPost().getId(),
					post.getLinkedPost().getTitle(), post.getLinkedPost().getWriter(),
					post.getLinkedPost().getContent(), stringUrlLinkedPost, post.getLinkedPost().getLatitude(),
					post.getLinkedPost().getLongitude(), null, post.getLinkedPost().getM_date());
			PostResponseDTO tempParentPost = new PostResponseDTO(post.getId(), post.getTitle(), post.getWriter(),
					post.getContent(), stringUrl, post.getLatitude(), post.getLongitude(), tempChildPost,
					post.getM_date());
			return tempParentPost;
		} else {

		}
		final String stringUrl = String.format("%s/%s", friendlyURL, post.getPicture());
		PostResponseDTO tempPost = new PostResponseDTO(post.getId(), post.getTitle(), post.getWriter(),
				post.getContent(), stringUrl, post.getLatitude(), post.getLongitude(), null, post.getM_date());
		return tempPost;

	}

	@Transactional
	@Override
	public void updatePost(EditPostRequestDTO post, int postId, String email) {
		Post oldPost = postDAO.getReferenceById(postId);
		User user = oldPost.getUser();
		if (user.getEmail().equals(email)) {
			oldPost.setContent(post.getContent());
			oldPost.setTitle(post.getTitle());
			LocalDateTime current = LocalDateTime.now();
			oldPost.setM_date(current);
			postDAO.save(oldPost);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unathorized user attempt");
		}

	}

	@Override
	public void deletePost(int postId, String email) {
		Post oldPost = postDAO.getReferenceById(postId);
		User user = oldPost.getUser();
		if (user.getEmail().equals(email)) {
			if (oldPost.getLinkedPost() != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "if post is linked it cannot be deleted");
			}
			amazonS3.deleteObject(bucketName, oldPost.getPicture());
			postDAO.deleteById(postId);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unathorized user attempt");
		}

	}

	@Override
	public PostListResponseDTO paginatePostByWriter(String writer, int pageNumber) {
		Pageable postPages = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());
		Page<Post> paginationResult = postDAO.findAllByWriter(postPages, writer);
		List<Post> posts = paginationResult.getContent();
		if (posts.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}

		List<PostResponseDTO> postlists = new ArrayList<PostResponseDTO>();
		for (Post post : posts) {

			if (post.getLinkedPost() != null) {
				final String stringUrl = String.format("%s/%s", friendlyURL, post.getPicture());

				PostResponseDTO tempPost = new PostResponseDTO(post.getLinkedPost().getId(), post.getTitle(),
						post.getWriter(), post.getContent(), stringUrl, post.getLatitude(), post.getLongitude(), null,
						post.getM_date());

				postlists.add(tempPost);
			} else {
				final String stringUrl = String.format("%s/%s", friendlyURL, post.getPicture());

				PostResponseDTO tempPost = new PostResponseDTO(post.getId(), post.getTitle(), post.getWriter(),
						post.getContent(), stringUrl, post.getLatitude(), post.getLongitude(), null, post.getM_date());
				postlists.add(tempPost);
			}

		}
		PostListResponseDTO postListDTO = new PostListResponseDTO(paginationResult.getTotalPages() - 1, postlists);
		return postListDTO;
	}

}
