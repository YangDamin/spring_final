package com.example.demo.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.ColumnDefault;

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
    
    @ColumnDefault(value = "0")
    private long viewCnt;
    private boolean open;
    private String videoPath;
    private Long userId;
    // private String userName;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public static Post createPost(Long userId, String title, String content, String date, String videoPath){
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setContent(content);
        post.setDate(date);
        post.setVideoPath(videoPath);
        return post;
    }

}