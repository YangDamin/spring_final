package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    @Autowired
    PostService postservice;
    
    @PostMapping("/write")
    @ResponseBody
    public void writeContent(String userEmail, String title, String content, String date){
        postservice.writeContent(userEmail, title, content, date);
    }
}
