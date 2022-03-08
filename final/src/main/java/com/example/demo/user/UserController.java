package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    // 전체 유저 목록 조회
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    // 유저 상세 조회
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    // 유저 등록
    @PostMapping("/users/signup")
    @ResponseBody
    public Map<String, Object> saveUser(@ModelAttribute User user){
        System.out.println("이메일 : " + user.getName());
        
        Map<String, Object> result = new HashMap<>();
        User dbUser = userRepository.findByEmail(user.getEmail());

        if(dbUser != null){         // DB에 user가 있으면
            System.out.println("회원가입 실패"); 
            result.put("msg", "🤦‍♂️회원가입 실패🤦‍♂️");
			result.put("code", 201);
        }else{                      // DB에 user가 없으면
            userRepository.save(user);
            System.out.println("회원가입 성공"); 
            result.put("msg", "👊회원가입 성공👊");
			result.put("code", 200);
        }
        return result;
    }

    // 중복 체크
    @GetMapping("/users/signup")
    public List<User> checkEmail(){
        List<User> userList = userRepository.findAll();
        System.out.println(userList);
        return userList;
    }

    // 유저 수정
    @PutMapping("/users/{id}")
    public User modifyUser(@RequestBody User user, @PathVariable("id") Long id){
        User user1 = user.builder()
                        .id(id)
                        .email(user.getEmail())
                        .pwd(user.getPwd())
                        .name(user.getName())
                        .phoneNum(user.getPhoneNum())
                        .build();

        return userService.modifyUser(user1);
    }

    // 유저 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }
}
