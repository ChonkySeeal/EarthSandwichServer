package com.EarthSandwich.service;

import com.EarthSandwich.dto.EditPostRequestDTO;
import com.EarthSandwich.dto.PostListResponseDTO;
import com.EarthSandwich.dto.PostRequestDTO;
import com.EarthSandwich.dto.PostResponseDTO;

public interface PostService {
	public void uploadPost(PostRequestDTO post, String email);

	public PostListResponseDTO paginatePost(int pageNumber);

	public PostListResponseDTO paginatePostByWriter(String writer, int pageNumber);

	public PostResponseDTO getPostById(int id);

	public void updatePost(EditPostRequestDTO post, int postId, String email);

	public void deletePost(int postId, String email);

}
