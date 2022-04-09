package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

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
    
    @PostMapping("/write")
    @ResponseBody
    public void writeContent(String userEmail, String title, String content, String date){
        postservice.writeContent(userEmail, title, content, date);
    }

    @GetMapping("/posts")
    @ResponseBody
    public List<Post> postList(Long id) {
        Sort sort = Sort.by(Order.desc("id"));
        List<Post> list = postRepository.findAll(sort);
        
        return list;

    }
}
