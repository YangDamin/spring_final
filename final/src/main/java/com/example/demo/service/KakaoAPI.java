package com.example.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KakaoAPI {

    // public String getAccessToken(String code) {
    //     String accessToken = "";
    //     String refreshToken = "";
    //     String reqURL = "https://kauth.kakao.com/oauth/token";

    //     try {
    //         URL url = new URL(reqURL);
    //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    //         conn.setRequestMethod("POST");
    //         conn.setDoOutput(true);

    //         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
    //         StringBuilder sb = new StringBuilder();
    //         sb.append("grant_type=authorization_code");
    //         sb.append("&client_id=04c9a760d057d6ccbc3cdb399201c2a3");
    //         sb.append("&redirect_uri=http://localhost:3000/oauth/callback/kakao");
    //         sb.append("&code=" + code);

    //         bw.write(sb.toString());
    //         bw.flush();

    //         int responseCode = conn.getResponseCode();
    //         System.out.println("response code = " + responseCode);

    //         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

    //         String line = "";
    //         String result = "";
    //         while ((line = br.readLine()) != null) {
    //             result += line;
    //         }
    //         System.out.println("response body=" + result);

    //         JsonParser parser = new JsonParser();
    //         JsonElement element = parser.parse(result);

    //         accessToken = element.getAsJsonObject().get("access_token").getAsString();
    //         refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

    //         br.close();
    //         bw.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return accessToken;
    // }

    // public HashMap<String, Object> getUserInfo(String accessToken) {
    //     HashMap<String, Object> userInfo = new HashMap<String, Object>();
    //     String reqUrl = "https://kapi.kakao.com/v2/user/me";
    //     try {
    //         URL url = new URL(reqUrl);
    //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    //         conn.setRequestMethod("POST");
    //         conn.setRequestProperty("Authorization", "Bearer " + accessToken);
    //         int responseCode = conn.getResponseCode();
    //         System.out.println("responseCode =" + responseCode);

    //         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

    //         String line = "";
    //         String result = "";

    //         while ((line = br.readLine()) != null) {
    //             result += line;
    //         }
    //         System.out.println("response body =" + result);

    //         JsonParser parser = new JsonParser();
    //         JsonElement element = parser.parse(result);

    //         JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
    //         JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

    //         String nickname = properties.getAsJsonObject().get("nickname").getAsString();
    //         String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

    //         userInfo.put("nickname", nickname);
    //         userInfo.put("email", email);

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return userInfo;
    // }

    // public void kakaoLogout(String accessToken) {
    //     String reqURL = "http://kapi.kakao.com/v1/user/logout";
    //     try {
    //         URL url = new URL(reqURL);
    //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    //         conn.setRequestMethod("POST");
    //         conn.setRequestProperty("Authorization", "Bearer " + accessToken);
    //         int responseCode = conn.getResponseCode();
    //         System.out.println("responseCode = " + responseCode);

    //         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

    //         String result = "";
    //         String line = "";

    //         while ((line = br.readLine()) != null) {
    //             result += line;
    //         }
    //         System.out.println(result);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }


    // public String getKakaoAccessToken (String code) {
    //     String access_Token = "";
    //     String refresh_Token = "";
    //     String reqURL = "https://kauth.kakao.com/oauth/token";

    //     try {
    //         URL url = new URL(reqURL);
    //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    //         //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
    //         conn.setRequestMethod("POST");
    //         conn.setDoOutput(true);

    //         //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
    //         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
    //         StringBuilder sb = new StringBuilder();
    //         sb.append("grant_type=authorization_code");
    //         sb.append("&client_id=e4a81d5a6acbda948310e08e2eafc123"); // TODO REST_API_KEY 입력
    //         sb.append("&redirect_uri=http://localhost:9000/oauth/kakao"); // TODO 인가코드 받은 redirect_uri 입력
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
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     return access_Token;
    // }
}
