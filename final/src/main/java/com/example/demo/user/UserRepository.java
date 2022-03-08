package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    public User findByEmailAndPwd(String email,String pwd);
    public User findByEmail(String email);
    public boolean existsByEmail(String email);
}
