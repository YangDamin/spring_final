package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Controller
public class FileUploadController {
@GetMapping("/upload1")
public String upload1() {
return "upload1";
}
@PostMapping("/upload1")
@ResponseBody
public String upload1Post(MultipartHttpServletRequest mRequest) {
String result = "";
MultipartFile mFile = mRequest.getFile("file");
String oName = mFile.getOriginalFilename();
result += oName + "\n";
return result;
}
}
// @RestController
// public class FileUploadController {

//     @PostMapping
//     public String fileUpload(@RequestParam("file")MultipartFile file){
//         File file2 = new File("./file" + file.getOriginalFilename());

//         try (InputStream inputStream = file.getInputStream();
//         FileOutputStream outputStream = new FileOutputStream(file2)
//         ){
//          int read;
//          byte[] bytes = new byte[1024];
         
//          while ((read = inputStream.read(bytes)) != -1){
//              outputStream.write(bytes, read);
//          }
//         } catch (Exception e) {
//             e.printStackTrace();
//             //TODO: handle exception
//         }
// System.out.println("File size : " + file.getSize());
// return file.getOriginalFilename();
//     }
// }

// import java.io.File;
// import java.io.IOException;
// import java.util.Date;
// import java.util.Random;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.example.demo.model.Post;

// @RestController
// @CrossOrigin("*") // 모든 출처에 대해 오픈 (CORS)
// public class MyController {

// 	@PostMapping("/uploadFiles")
// 	public ResponseEntity<Object> uploadFiles(MultipartFile[] multipartFiles, String stringFood) {
// 		String UPLOAD_PATH = "F:\\myUpload"; // 업로드 할 위치
		
// 		try {
//         		// JSON데이터는 파일 데이터와 같이 못 보내기 때문에 문자열로 받아와서 JSON으로 변환
//             		// JSON데이터로 같이 보내는 방법을 아시는 분은 댓글로 알려주시면 감사드립니다.
// 			Food food = new ObjectMapper().readValue(stringFood, Food.class); // String to JSON
			
// 			for(int i=0; i<multipartFiles.length; i++) {
// 				MultipartFile file = multipartFiles[i];
                
// 				String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
// 				String originName = file.getOriginalFilename(); // ex) 파일.jpg
// 				String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
// 				originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
// 				long fileSize = file.getSize(); // 파일 사이즈
				
// 				File fileSave = new File(UPLOAD_PATH, fileId + "." + fileExtension); // ex) fileId.jpg
// 				if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
// 					fileSave.mkdirs();
// 				}
                
// 				file.transferTo(fileSave); // fileSave의 형태로 파일 저장
				
// 				System.out.println("fileId= " + fileId);
// 				System.out.println("originName= " + originName);
// 				System.out.println("fileExtension= " + fileExtension);
// 				System.out.println("fileSize= " + fileSize);
// 			}
			
// 			System.out.println("food= " + food);
// 		} catch(IOException e) {
// 			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
// 		}
		
// 		return new ResponseEntity<Object>("Success", HttpStatus.OK);
// 	}
