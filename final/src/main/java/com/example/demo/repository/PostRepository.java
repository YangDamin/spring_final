package com.example.demo.repository;


import java.util.List;

import com.example.demo.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
    // public Post findTopByOrderByViewCntDesc();
    List<Post> findByTitleContaining(String title);
}
