package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;



@Controller
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession session;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 전체 유저 목록 조회
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // 유저 상세 조회
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    // 회원가입
    @PostMapping("/users/signup")
    @ResponseBody
    public Map<String, Object> saveUser(@ModelAttribute User user) {

        Map<String, Object> result = new HashMap<>();
        User dbUser = userRepository.findByEmail(user.getEmail());
        System.out.println(dbUser);
        User findUser = userRepository.findByNameAndPhone(user.getName(), user.getPhone());
        System.out.println(findUser);

        //비밀번호 인코딩
        
        System.out.println("비번 인코딩"+passwordEncoder.encode(user.getPwd()));
        

        if (dbUser != null) { // DB에 이메일이 있으면
            System.out.println("회원가입 실패");
            result.put("msg", "🤦‍♂️회원가입 실패🤦‍♂️");
            result.put("code", 201);
        } else { // DB에 이메일이 없으면
            if(findUser != null){   // DB에 해당 회원정보가 있으면
                System.out.println("이미 가입된 회원정보입니다.");
                result.put("code",401);
            }else{
                user.setPwd(passwordEncoder.encode(user.getPwd()));
                userRepository.save(user);
                System.out.println("회원가입 성공");
                result.put("msg", "👊회원가입 성공👊");
                result.put("code", 200);
            }
        }
        return result;
    }

    // 비밀번호 변경
    @PostMapping("/mypage")
    @ResponseBody
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

    // 유저 수정
    @PutMapping("/users/{id}")
    public User modifyUser(@RequestBody User user, @PathVariable("id") Long id) {
        User user1 = user.builder()
                .id(id)
                .email(user.getEmail())
                .pwd(user.getPwd())
                .name(user.getName())
                .phone(user.getPhone())
                .build();

        return userService.modifyUser(user1);
    }

    // 탈퇴
    // @DeleteMapping(value = "/mypage/{userId}")
    // @ResponseBody
    // public void deleteUser(@PathVariable("userId") long userId) {
    //     System.out.println(userId);
    //     Optional<User> user = userRepository.findById(userId);
    //     userRepository.delete(user.get());
    // }

    // 이메일 찾기
    @PostMapping("/users/findEmail")
    @ResponseBody
    public Map<String, Object> findEmail(@ModelAttribute User user) {
        System.out.println("유저 이름 : " + user.getName() + "유저 폰번호 :" + user.getPhone());
        Map<String, Object> result = new HashMap<>();

        User findUser = userRepository.findByNameAndPhone(user.getName(), user.getPhone());
        System.out.println(findUser);

        if (findUser != null) { // 이름과 휴대폰번호에 해당하는 유저가 있으면
            System.out.println("이름+번호에 해당하는 이메일이 있다.");
            System.out.println("이메일:" + findUser.getEmail());

            result.put("findemail", findUser.getEmail());
            result.put("code", 200);
        } else {
            System.out.println("이름+번호에 해당하는 이메일이 없다.");
            result.put("code", 400);
        }

        System.out.println(result);
        return result;
    }

    // 임시 비밀번호 발급
    public String getRandomPassword(int len){
        char[] charSet = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8',
             '9', 
             'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
             'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        int idx = 0;
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<len; i++){
            idx = (int) (charSet.length * Math.random());
            sb.append(charSet[idx]);
        }
        System.out.println("임시 비번 : "+ sb.toString());
        return sb.toString();
    }

    @PostMapping("/users/findPassword")
    @ResponseBody
    public Map<String, Object> findPW(@ModelAttribute User user) {
        Map<String, Object> result = new HashMap<>();

        User findUser = userRepository.findByEmailAndNameAndPhone(user.getEmail(), user.getName(), user.getPhone());
        System.out.println(findUser);

        //임시 비번
        getRandomPassword(10);

        if (findUser != null) { // 이메일,이름, 휴대폰번호에 해당하는 유저가 있으면
            System.out.println("해당 계정에 임시 비번 발급!");
            
            findUser.setPwd(getRandomPassword(10));
            userRepository.save(findUser);

            result.put("findPwd", findUser.getPwd());
            result.put("code", 200);
        } else {
            System.out.println("이메일+이름+번호에 해당하는 이메일이 없다.");
            result.put("code", 400);
        }
        return result;
    }
    
    
    // 로그인
    @PostMapping("/users/signin")
    @ResponseBody
    public Map<String, Object> login(@ModelAttribute User user) {
        Map<String, Object> result = new HashMap<>();
        User loginUser = userRepository.findByEmail(user.getEmail());
        boolean pwdMatch = passwordEncoder.matches(user.getPwd(), loginUser.getPwd());
        System.out.println("비번 일치하는지?"+pwdMatch);

        if ((loginUser != null && pwdMatch == true) | (loginUser != null && (user.getPwd().equals(loginUser.getPwd()) ))) { // DB에 user가 있으면
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

@ResponseBody
    @GetMapping("/kakao")
    public void  kakaoCallback(@RequestParam String code) {

        System.out.println("kakao code : " + code);

    }
    
}

