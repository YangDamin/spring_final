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
// import com.example.demo.service.S3Service;
// import com.example.demo.service.GalleryService;
// import com.example.demo.dto.GalleryDto;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@CrossOrigin(origins = "http://localhost:3000")

public class PostController {
    @Autowired
    PostService postservice;
    
    // @Autowired
    // S3Service s3Service;

    // @Autowired
    // GalleryService galleryService;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/write")
    @ResponseBody
    public void writeContent(String userEmail, String title, String content, String date, String videoPath ,Long viewCnt) {

        postservice.writeContent(userEmail, title, content, date, videoPath , viewCnt);
    }


    // @GetMapping("/board")
    // @ResponseBody
    // public List<FreeBoard> freeBoardList() {
    //     Sort sort = Sort.by(Order.desc("id"));
    //     List<FreeBoard> list = freeBoardRepository.findAll(sort);
    //     return list;

    // }

        
    //상세정보
    @GetMapping("/post/detail/{postid}")
    @ResponseBody
    public Post postDetail( @PathVariable("postid") Long postid) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(postid);
        Optional<Post> opt = postRepository.findById(postid);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@"+opt);
        // Post post = opt.get();
        //조회수 증가
        // post.setViewCnt(post.getViewCnt() + 1);
        // postRepository.save(post);
        // System.out.println("@@@@@@@@@@@@@@@@@@@@@@"+opt);
        return opt.get();
    }



    //게시글
    @GetMapping("/posts")
    @ResponseBody
    public List<Post> postList(Long id) {
        Sort sort = Sort.by(Order.desc("id"));
        List<Post> list = postRepository.findAll(sort);
        
        return list;

    }


}
