package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class VideoUploadController {
    @GetMapping("/upload2")
    public String upload2() {
    return "upload2";
    }
    @PostMapping("/upload2")
    @ResponseBody
    public String upload2Post(@RequestParam("file") MultipartFile mFile) {
    String result = "";
    String oName = mFile.getOriginalFilename();
    result += oName + "\n";
    return result;
    }
        
        
}
