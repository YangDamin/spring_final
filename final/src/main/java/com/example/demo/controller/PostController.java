package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;

import com.example.demo.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
  @Autowired
  PostService postservice;

  @Autowired
  PostRepository postRepository;

  @Autowired
  HttpSession session;

  // 글 작성
  @PostMapping("/write")
  public void writeContent(String userEmail, String title, String content, String date, String videoPath) {

    postservice.writeContent(userEmail, title, content, date, videoPath);
  }

  // 글 상세정보
  @GetMapping("/post/detail/{id}")
  public Post postDetail(@PathVariable("id") Long id) {
    Optional<Post> opt = postRepository.findById(id);
    // 조회수 증가
    Post post = opt.get();
    post.setViewCnt(post.getViewCnt() + 1);
    postRepository.save(post);
    return opt.get();

  }

  // 게시글 전체 불러오기
  @GetMapping("/posts")
  public List<Post> postList(Long id) {
    Sort sort = Sort.by(Order.desc("id"));
    List<Post> list = postRepository.findAll(sort);

    return list;

  }

  // 내가 쓴 게시물 조회
  @PostMapping("/myfeed")
  public List<Post> mypostList(Long id) {

    return postservice.mypostList(id);
  }

  // 제목 검색
  @GetMapping("/search/{word}")
  public List<Post> searchVideo(@PathVariable("word") String word) {
    List<Post> postBySearch = postRepository.findByTitleContaining(word);

    return postBySearch;
  }

  // 인기글
  @GetMapping("/best")
  public Post bestView(@ModelAttribute Post post) {
    Post bestPost = postRepository.findTopByOrderByViewCntDesc();

    return bestPost;
  }

  //수정
  // @PostMapping("/post/update/{id}")
  // public Post postUpdate( @ModelAttribute Post post,
  //                         @PathVariable("id") long id) {
  //   User user = (User) session.getAttribute("user_info");
  //   post.setId(id);
  //   postRepository.save(post);
  //   return post;

  // }


  @PostMapping("/post/update/{id}")
  public Post postUpdate(Post post,@PathVariable("id") long id) {
    User user = (User) session.getAttribute("user_info");
    post.setId(id);
    postRepository.save(post);
    return post;

  }


   //삭제
   @DeleteMapping("/post/delete/{id}")
   public void postDelete(@PathVariable("id") long id) {

       postRepository.deleteById(id);
   }
}
