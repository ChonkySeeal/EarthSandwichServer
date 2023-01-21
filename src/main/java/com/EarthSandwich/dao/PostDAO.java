package com.EarthSandwich.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EarthSandwich.entity.Post;

@Repository
public interface PostDAO extends JpaRepository<Post, Integer> {
	Post findByTitle(String title);

	Page<Post> findAllByIsParentTrue(Pageable pageable);

	Page<Post> findAllByWriter(Pageable pageable, String writer);
}
