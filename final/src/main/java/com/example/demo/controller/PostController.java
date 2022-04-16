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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    //게시물 작성
    @PostMapping("/write")

    public void writeContent(String userEmail, String title, String content, String date, String videoPath, String videothumbnail) {

        postservice.writeContent(userEmail, title, content, date, videoPath, videothumbnail);
    }

    //상세정보
    @GetMapping("/post/detail/{postid}")
    public Map<String, Object> postDetail( @PathVariable("postid") Long postid) {
        Optional<Post> opt = postRepository.findById(postid);
        Post post = opt.get();
        post.setViewCnt(post.getViewCnt() + 1);
        postRepository.save(post);

        //게시물 작성 사용자 이름 뽑아내기        
        String name = postservice.writeUser(opt.get().getUser());

        Map<String, Object> result = new HashMap<>();
        result.put("post", opt.get());
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
  public Post postUpdate(@ModelAttribute Post post,@PathVariable("id") long id) {
    User user = (User) session.getAttribute("user_info");
    post.setId(id);
    postRepository.save(post);
    return post;

  }


  public Map<String, Object> modifyPw(@ModelAttribute User user) {
    Map<String, Object> result = new HashMap<>();
    User modifyUser = userRepository.findByEmail(user.getEmail());
    boolean pwdMatch = passwordEncoder.matches(user.getPwd(), modifyUser.getPwd());


    if (modifyUser != null && pwdMatch == false) {   // 기존 비번과 새로운 비번이 일치하지않으면 비번변경
        modifyUser.setPwd(passwordEncoder.encode(user.getPwd()));
        userRepository.save(modifyUser);
        result.put("compare", pwdMatch);
        result.put("pwd", modifyUser.getPwd());
    }else {
        result.put("compare", pwdMatch);
    }
    System.out.println("비밀번호 : " + user.getPwd());
    System.out.println("이메일 : " + user.getEmail());
    System.out.println(modifyUser);

    return result;
}

    @PutMapping("/post/update/{id}")
    public 




   //삭제
   @DeleteMapping("/post/delete/{id}")
   public void postDelete(@PathVariable("id") long id) {

       postRepository.deleteById(id);
   }

}
