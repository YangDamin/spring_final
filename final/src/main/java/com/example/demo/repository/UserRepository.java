package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    public User findByEmail(String email);
    public User findByEmailAndPwd(String email, String pwd);
    public User findByNameAndPhone(String name, String phone);
}
