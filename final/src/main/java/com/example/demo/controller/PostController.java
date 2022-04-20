package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    HttpSession session;

    //게시물 작성
    @PostMapping("/write")

    public void writeContent(String userEmail, String title, String content, String date, String videoPath, String videothumbnail) {

        postservice.writeContent(userEmail, title, content, date, videoPath, videothumbnail);
    }

    //상세정보
    @GetMapping("/post/detail/{postid}")
    public Map<String, Object> postDetail( @PathVariable("postid") Long postid ,Model model) {
        Post postEntity = postRepository.findById(postid).get();
        model.addAttribute("postEntity", postEntity);
        
        Optional<Post> opt = postRepository.findById(postid);
        Post post = opt.get();
        post.setViewCnt(post.getViewCnt() + 1);
        postRepository.save(post);

        //게시물 작성 사용자 이름 뽑아내기        
        String name = postservice.writeUser(opt.get().getUser());

        Map<String, Object> result = new HashMap<>();
        result.put("post", opt.get());
        System.out.println("post 보기:" + opt.get());
        result.put("name", name);
        
        return result;

    }

    //게시글 전체 불러오기
    @GetMapping("/posts")
    public List<Post> postList(Long id) {
        Sort sort = Sort.by(Order.desc("id"));
        List<Post> list = postRepository.findAll(sort);
        return list;

    }

    //  내가 쓴 게시물 조회
    //  나의 인기 게시물 상위 n개 추출
    @PostMapping("/myfeed")
    public Map<String, Object> mypostList(Long id, String email){
        return postservice.mypostList(id, email);
    }

    //검색 기능
    @GetMapping("/search/{word}")
    public List<Post> searchVideo(@PathVariable("word") String word){
      List<Post> postBySearch = postRepository.findByTitleContaining(word);

      return postBySearch;
    }

    // 게시물 삭제
    @DeleteMapping(value = "/post/delete/{id}")
    public void deletePost(@PathVariable("id") long id){
        postservice.deletePost(id);
    }

    //글 수정
    @PutMapping("/post/update/{id}")
    public Post postUpdate(
            @ModelAttribute Post post, @PathVariable("id") long id) {
        
        // Optional<Post> data = postRepository.findById(id);
        // Post postData = data.get();
        System.out.println(post);
        User principal = (User) session.getAttribute("principal");
        
        if (principal != null && id == principal.getId()){
        
        User userEntity = userRepository.findById(id).get();
        post.setTitle(post.getTitle());
        post.setContent(post.getContent());
        post.setDate(post.getDate());
        post.setVideoPath(post.getVideoPath());
        post.setVideothumbnail(post.getVideothumbnail());

        postRepository.save(post);
        session.setAttribute("user", userEntity);

    }
        // board.setContent(content);
        
        
        return post;
    }


   //글 삭제
   @DeleteMapping("/post/delete/{id}")
   public void postDelete(@PathVariable("id") long id) {

           postRepository.deleteById(id);

    }
}