package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;


import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;

import com.example.demo.service.PostService;


import org.springframework.beans.factory.annotation.Autowired;

@Controller
@CrossOrigin(origins = "http://localhost:3000")

public class PostController {
    @Autowired
    PostService postservice;

    @Autowired
    PostRepository postRepository;

    //글 작성
    @PostMapping("/write")
    @ResponseBody
    public void writeContent(String userEmail, String title, String content, String date, String videoPath) {

        postservice.writeContent(userEmail, title, content, date, videoPath);
    }

    //상세정보
    @GetMapping("/post/detail/{id}")
    @ResponseBody
    public Post postDetail( @PathVariable("id") Long id) {
        Optional<Post> opt = postRepository.findById(id);
        Post post = opt.get();
        // 조회수 증가
        // post.setViewCnt(post.getViewCnt() + 1);
        // postRepository.save(post);
        return opt.get();
    }

    //게시글 전체 불러오기//
    @GetMapping("/posts")
    @ResponseBody
    public List<Post> postList(Long id) {
        Sort sort = Sort.by(Order.desc("id"));
        List<Post> list = postRepository.findAll(sort);
        
        return list;

    }

    @GetMapping("/search/{search}")
    public List<Post> searchBook(@PathVariable("search") String search){
      List<Post> postBySearch = postRepository.findByTitleContaining(search);
      return postBySearch;
  }


}
