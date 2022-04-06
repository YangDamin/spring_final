package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;

import com.example.demo.service.OAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/oauth/*")
public class KaKaoController {

	@Autowired
	private OAuthService oauthService;

	// @RequestMapping(value="/kakao", method=RequestMethod.GET)
	// public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
	// 	System.out.println("#########" + code);
	// 	String access_Token = oauthService.getAccessToken(code);
        
	// 	// 위에서 만든 코드 아래에 코드 추가
	// 	HashMap<String, Object> userInfo = oauthService.getUserInfo(access_Token);
	// 	System.out.println("###access_Token#### : " + access_Token);
	// 	System.out.println("###nickname#### : " + userInfo.get("nickname"));
	// 	System.out.println("###email#### : " + userInfo.get("account_email"));
        
	// 	return "users/signin";
    // 	}

    @ResponseBody
    @GetMapping("/kakao")
    public void  kakaoCallback(@RequestParam String code) throws IOException {
    
        System.out.println("#########" + code);
        String access_Token = oauthService.getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);
        }

}
