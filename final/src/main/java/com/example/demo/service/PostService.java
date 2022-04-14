package com.example.demo.service;


import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@Service
@Transactional
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    //게시물 작성
    public void writeContent(String userEmail, String title, String content, String date , String videoPath){
        User findUser = userRepository.findByEmail(userEmail);

        Post post = Post.createPost(findUser.getId(),title,content,date,videoPath);

        postRepository.save(post);
    }

    // 내가 쓴 게시물 조회
    public List<Post> mypostList(Long id){

        return postRepository.findByUserId(id);

    }
}