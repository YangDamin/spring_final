package com.example.demo.repository;


import java.util.List;

import com.example.demo.model.Post;
import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findByTitleContaining(String title);
    List<Post> findByUserId(Long id, Sort sort);
    public Post findTopByOrderByViewCntDesc();
    List<Post> findByUser(User user);
    // public void deleteById(Long id);
}
