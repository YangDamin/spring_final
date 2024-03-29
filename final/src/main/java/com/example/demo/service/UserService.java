package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.AllArgsConstructor;

import java.util.List;

import javax.transaction.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
@AllArgsConstructor
@CrossOrigin
public class UserService {
    
    private final UserRepository userRepository;

    // 전체 유저 목록 조회
    @Transactional
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    // 유저 상세 조회
    @Transactional
    public User getUser(Long id){
        return userRepository.findById(id).get();
    }

    // 유저 등록
    @Transactional
    public User saveUser(User user){
        
        return userRepository.save(user);
    }

    // 유저 수정
    @Transactional
    public User modifyUser(User user){
        return userRepository.save(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
