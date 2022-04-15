package com.example.demo.service;


import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        Post post = Post.createPost(findUser,title,content,date,videoPath);

        postRepository.save(post);
    }

    // 게시물 작성 사용자 이름 뽑아내기
    public String writeUser(User user){
        return user.getName();
    }

    // 내가 쓴 게시물 조회
    // 나의 인기 게시물 상위 n개 추출
    public Map<String, Object> mypostList(Long id, String email){

        Map<String, Object> result = new HashMap<>();
        // 내 게시물
        User user = userRepository.findByEmail(email);
        List<Post> myfeedlist = postRepository.findByUser(user);
        result.put("myfeed", myfeedlist);

        // 내 인기 게시물
        Sort sort = Sort.by(Order.desc("viewCnt"));
        List<Post> list = postRepository.findByUserId(id,sort);     //모든 게시물 조회수에 따라 정렬
        

        result.put("mypopular", list);
        return result;

    }
}