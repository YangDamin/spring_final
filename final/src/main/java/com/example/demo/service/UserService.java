package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.transaction.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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


    // public String getAccessToken (String authorize_code) {
	// 	String access_Token = "";
	// 	String refresh_Token = "";
	// 	String reqURL = "https://kauth.kakao.com/oauth/token";
	// 	try {
	// 		URL url = new URL(reqURL);
	// 		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// 		conn.setRequestMethod("POST");
	// 		conn.setDoOutput(true);
	// 		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	// 		StringBuilder sb = new StringBuilder();
	// 		sb.append("grant_type=authorization_code");
	// 		sb.append("&client_id=04c9a760d057d6ccbc3cdb399201c2a3"); //본인이 발급받은 key
	// 		sb.append("&redirect_uri=http://localhost:3000/oauth/kakao"); // 본인이 설정한 주소
	// 		sb.append("&code=" + authorize_code);
	// 		bw.write(sb.toString());
	// 		bw.flush();
	// 		int responseCode = conn.getResponseCode();
	// 		System.out.println("responseCode : " + responseCode);
	// 		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	// 		String line = "";
	// 		String result = "";
	// 		while ((line = br.readLine()) != null) {
	// 			result += line;
	// 		}
	// 		System.out.println("response body : " + result);
	// 		JsonParser parser = new JsonParser();
	// 		JsonElement element = parser.parse(result);
	// 		access_Token = element.getAsJsonObject().get("access_token").getAsString();
	// 		refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
	// 		System.out.println("access_token : " + access_Token);
	// 		System.out.println("refresh_token : " + refresh_Token);
	// 		br.close();
	// 		bw.close();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// 	return access_Token;
	// }
    
	// public HashMap<String, Object> getUserInfo(String access_Token) {
	// 	HashMap<String, Object> userInfo = new HashMap<String, Object>();
	// 	String reqURL = "https://kapi.kakao.com/v2/user/me";
	// 	try {
	// 		URL url = new URL(reqURL);
	// 		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// 		conn.setRequestMethod("GET");
	// 		conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	// 		int responseCode = conn.getResponseCode();
	// 		System.out.println("responseCode : " + responseCode);
	// 		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	// 		String line = "";
	// 		String result = "";
	// 		while ((line = br.readLine()) != null) {
	// 			result += line;
	// 		}
	// 		System.out.println("response body : " + result);
	// 		JsonParser parser = new JsonParser();
	// 		JsonElement element = parser.parse(result);
	// 		JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	// 		JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	// 		String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	// 		String email = kakao_account.getAsJsonObject().get("email").getAsString();
	// 		userInfo.put("nickname", nickname);
	// 		userInfo.put("email", email);
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// 	return userInfo;
	// }



	// public void createKakaoUser(String token) {

    //     String reqURL = "https://kapi.kakao.com/v2/user/me";

    //     //access_token을 이용하여 사용자 정보 조회
    //     try {
    //         URL url = new URL(reqURL);
    //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    //         conn.setRequestMethod("POST");
    //         conn.setDoOutput(true);
    //         conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

    //         //결과 코드가 200이라면 성공
    //         int responseCode = conn.getResponseCode();
    //         System.out.println("responseCode : " + responseCode);

    //         //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
    //         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    //         String line = "";
    //         String result = "";

    //         while ((line = br.readLine()) != null) {
    //             result += line;
    //         }
    //         System.out.println("response body : " + result);

    //         //Gson 라이브러리로 JSON파싱
    //         JsonParser parser = new JsonParser();
    //         JsonElement element = parser.parse(result);

    //         int id = element.getAsJsonObject().get("id").getAsInt();
    //         boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
    //         String email = "";
    //         if(hasEmail){
    //             email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
    //         }

    //         System.out.println("id : " + id);
    //         System.out.println("email : " + email);

    //         br.close();

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }



	// public void createKakaoUser(String token) {

    //     String reqURL = "https://kapi.kakao.com/v2/user/me";

    //     //access_token을 이용하여 사용자 정보 조회
    //     try {
    //         URL url = new URL(reqURL);
    //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    //         conn.setRequestMethod("POST");
    //         conn.setDoOutput(true);
    //         conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

    //         //결과 코드가 200이라면 성공
    //         int responseCode = conn.getResponseCode();
    //         System.out.println("responseCode : " + responseCode);

    //         //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
    //         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    //         String line = "";
    //         String result = "";

    //         while ((line = br.readLine()) != null) {
    //             result += line;
    //         }
    //         System.out.println("response body : " + result);

    //         //Gson 라이브러리로 JSON파싱
    //         JsonParser parser = new JsonParser();
    //         JsonElement element = parser.parse(result);

    //         int id = element.getAsJsonObject().get("id").getAsInt();
    //         boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
    //         String email = "";
    //         if(hasEmail){
    //             email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
    //         }

    //         System.out.println("id : " + id);
    //         System.out.println("email : " + email);

    //         br.close();

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

	// public String getKaKaoAccessToken(String code){
    //     String access_Token="";
    //     String refresh_Token ="";
    //     String reqURL = "https://kauth.kakao.com/oauth/token";

    //     try{
    //         URL url = new URL(reqURL);
    //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    //         //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
    //         conn.setRequestMethod("POST");
    //         conn.setDoOutput(true);

    //         //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
    //         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
    //         StringBuilder sb = new StringBuilder();
    //         sb.append("grant_type=authorization_code");
    //         sb.append("&client_id=fadeb1cea8077be1a20d8cc98139a990"); // TODO REST_API_KEY 입력
    //         sb.append("&redirect_uri=http://localhost:9000/app/users/kakao"); // TODO 인가코드 받은 redirect_uri 입력
    //         sb.append("&code=" + code);
    //         bw.write(sb.toString());
    //         bw.flush();

    //         //결과 코드가 200이라면 성공
    //         int responseCode = conn.getResponseCode();
    //         System.out.println("responseCode : " + responseCode);
    //         //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
    //         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    //         String line = "";
    //         String result = "";

    //         while ((line = br.readLine()) != null) {
    //             result += line;
    //         }
    //         System.out.println("response body : " + result);

    //         //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
    //         JsonParser parser = new JsonParser();
    //         JsonElement element = parser.parse(result);

    //         access_Token = element.getAsJsonObject().get("access_token").getAsString();
    //         refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

    //         System.out.println("access_token : " + access_Token);
    //         System.out.println("refresh_token : " + refresh_Token);

    //         br.close();
    //         bw.close();
    //     }catch (IOException e) {
    //         e.printStackTrace();
    //     }

    //     return access_Token;
    // }
	
}
