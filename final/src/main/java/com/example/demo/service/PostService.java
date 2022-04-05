package com.example.demo.service;


import java.sql.Date;

import javax.transaction.Transactional;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    //게시물 작성
    public void writeContent(String userEmail, String title, String content, String date){
        User findUser = userRepository.findByEmail(userEmail);

        Post post = Post.createPost(findUser,title,content,date);

        postRepository.save(post);
    }
}
