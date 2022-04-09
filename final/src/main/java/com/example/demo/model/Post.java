package com.example.demo.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private long id;

    private String title;
    private String content;

    private String date;

    private long like;
    private boolean open;
    private long viewCnt;
    private String videoPath;
    private Long userId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public static Post createPost(Long userId, String title, String content, String date, String videoPath , Long viewCnt){
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setContent(content);
        post.setDate(date);
        post.setVideoPath(videoPath);
        post.setViewCnt(viewCnt);
        return post;
    }

}
