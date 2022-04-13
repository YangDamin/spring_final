package com.example.demo.repository;


import java.util.List;

import com.example.demo.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findByTitleContaining(String title);
    List<Post> findByUserId(Long id);
}
