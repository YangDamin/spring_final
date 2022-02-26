package com.example.demo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    public User modifyUser(@RequestBody User user, @PathVariable("id") Long id){
        User user1 = user.builder()
                        .id(id)
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .pwd(user.getPwd())
                        .name(user.getName())
                        .phoneNum(user.getPhoneNum())
                        .build();

        return userService.modifyUser(user1);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }
}
