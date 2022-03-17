package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.KakaoAPI;
import com.example.demo.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession session;

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




    // 로그인
    @PostMapping("/users/signin")
    @ResponseBody
    public Map<String, Object> login(@ModelAttribute User user) {
        Map<String, Object> result = new HashMap<>();
        User loginUser = userRepository.findByEmailAndPwd(user.getEmail(), user.getPwd());
        System.out.println(user.getEmail() + "\n" + user.getPwd());

        if (loginUser != null) { // DB에 user가 있으면
            session.setAttribute("user_info", loginUser);
            System.out.println("로그인 성공");
            result.put("user", loginUser);
            result.put("msg", "로그인 성공");
            result.put("code", 200);
        } else { // // DB에 user가 없으면
            System.out.println("로그인 실패");
            result.put("msg", "로그인 실패");
            result.put("code", 400);
        }
        return result;

    }

    // 로그아웃
    @GetMapping("/users/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}


// ========================== Kakao Login ==============================


//카카오 로그인
KakaoAPI kakaoApi = new KakaoAPI();

@RequestMapping(value = "/login")
public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
    ModelAndView mav = new ModelAndView();
    // 1번 인증코드 요청 전달
    String accessToken = kakaoApi.getAccessToken(code);
    // 2번 인증코드로 토큰 전달
    HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

    System.out.println("login info : " + userInfo.toString());

    if (userInfo.get("email") != null) {
        session.setAttribute("userId", userInfo.get("email"));
        session.setAttribute("accessToken", accessToken);
    }
    mav.addObject("userId", userInfo.get("email"));
    mav.setViewName("index");
    return mav;
}


//카카오 로그아웃
@RequestMapping(value = "/logout")
public ModelAndView logout(HttpSession session) {
    ModelAndView mav = new ModelAndView();

    kakaoApi.kakaoLogout((String) session.getAttribute("accessToken"));
    session.removeAttribute("accessToken");
    session.removeAttribute("userId");
    mav.setViewName("index");
    return mav;
}


}