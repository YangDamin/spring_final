package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.KakaoAPI;
import com.example.demo.service.UserService;
import com.github.scribejava.core.model.OAuth2AccessToken;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@Controller
@CrossOrigin(origins = "http://localhost:3000")
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

    // 유저 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

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


// ========================== Kakao Login ==============================

// @RequestMapping(value="/kakaoLogin", method=RequestMethod.GET)
// public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
//     System.out.println("#########" + code);
//     return "member/testPage";
//     /*
//      * 리턴값의 testPage는 아무 페이지로 대체해도 괜찮습니다.
//      * 없는 페이지를 넣어도 무방합니다.
//      * 404가 떠도 제일 중요한건 #########인증코드 가 잘 출력이 되는지가 중요하므로 너무 신경 안쓰셔도 됩니다.
//      */
//     }
    
//카카오 로그인
// KakaoAPI kakaoApi = new KakaoAPI();

// @RequestMapping(value = "/oauth/callback/kakao")
// public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
//     ModelAndView mav = new ModelAndView();
//     // 1번 인증코드 요청 전달
//     String accessToken = kakaoApi.getKakaoAccessToken(code);
//     // 2번 인증코드로 토큰 전달
//     HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
//     System.out.println("카카오 로그인");
//     System.out.println("login info : " + userInfo.toString());

//     if (userInfo.get("email") != null) {
//         session.setAttribute("userId", userInfo.get("email"));
//         session.setAttribute("accessToken", accessToken);
//     }
//     mav.addObject("userId", userInfo.get("email"));
//     mav.setViewName("index");
//     return mav;
// }

    // ========================== Kakao Login ==============================

//카카오 로그아웃
// @RequestMapping(value = "/logout")
// public ModelAndView logout(HttpSession session) {
//     ModelAndView mav = new ModelAndView();

//     kakaoApi.kakaoLogout((String) session.getAttribute("accessToken"));
//     session.removeAttribute("accessToken");
//     session.removeAttribute("userId");
//     mav.setViewName("index");
//     return mav;
// }

@RequestMapping(value="/oauth/kakao", method=RequestMethod.GET)
public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
    System.out.println("#########" + code);
    String access_Token = userService.getAccessToken(code);
    HashMap<String, Object> userInfo = userService.getUserInfo(access_Token);
    System.out.println("###access_Token#### : " + access_Token);
    System.out.println("###nickname#### : " + userInfo.get("nickname"));
    System.out.println("###email#### : " + userInfo.get("email"));
    return "redirect:/";
    }


	// @RequestMapping(value="/oauth/kakao", method=RequestMethod.GET)
	// public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
	// 	System.out.println("#########" + code);
        
	// 	// 위에서 만든 코드 아래에 코드 추가
	// 	String access_Token = userService.getAccessToken(code);
	// 	System.out.println("###access_Token#### : " + access_Token);
        
	// 	return "/";
    // 	}

//     @Controller
// public class NaverLogin{
//     private static final Log LOG = LogFactory.getLog(NaverLogin.class );

//     @RequestMapping(value = "/nearo")
//     public String initLogin(Model model, HttpSession session, NaverLoginBO naverLoginBO) throws Exception {

//         String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
//         LOG.info("네이버 url : "+naverAuthUrl);
//         /* 생성한 인증 URL을 View로 전달 */
//         model.addAttribute("naver_url", naverAuthUrl);

//         /* 생성한 인증 URL을 Model에 담아서 전달 */
//         return "nearo";
//     }

//     @RequestMapping(value = "/")
//     public String getUserProfile(
//             RedirectAttributes redirectAttributes, Model model,
//             HttpSession session, HttpServletRequest request,
//             HttpServletResponse response, NaverLoginBO naverLoginBO) throws IOException {
//         //파라메터의 값을 세션에 저장
//         String state = request.getParameter("state");
//         String code = request.getParameter("code");
//         LOG.info("코드, 상태 확인 : "+ state+" , " +code);
//         //리다이렉트로 이 메소드가 다시 실행되므로 세션영역에 getParameter의 null값이 들어가지 않도록 보호
//         if(state!=null && code!=null) {
//             session.setAttribute("state", state);
//             session.setAttribute("code", code);
//         }
//         LOG.info("세션 값 확인 : "+ session.getAttribute("state")+"  "+ session.getAttribute("code"));

//         /* getParameter로 값을 주는 경우 code와 state로 인증토큰(oauthToken)을 획득한 뒤 세션에 저장
//         * 나중에 다른 곳에서 정보가 필요해지면 session에 저장된 토큰을 사용하여
//         REST-API 방식으로 정보를 요청할 것임.
//         * 성공적으로 인증과 발급이 완료되면 addFlashAttribute로 전달할 값을 저장하고
//         리다이렉트로 현재 메소드를 다시 실행하여 메인 페이지를 전송*/

//         OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
//         session.setAttribute("oauthToken",oauthToken);
//         String apiResult = naverLoginBO.getUserProfile(oauthToken);
//         System.out.println("Naver login success");
//         session.setAttribute("result",apiResult);
//         return "redirect:http://13.124.70.197:8080/base";
//     }
    
}

