package com.EarthSandwich.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EarthSandwich.entity.Comment;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Integer> {

	List<Comment> findAllByPost_Id(int postId);

	Page<Comment> findAllByUser_Username(Pageable pageable, String writer);
}
