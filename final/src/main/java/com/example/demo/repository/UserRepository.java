package com.example.demo.repository;

import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    public User findByEmailAndPwd(String email,String pwd);
    public User findByEmail(String email);
    public boolean existsByEmail(String email);
}
