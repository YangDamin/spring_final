package com.example.demo.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    private String videothumbnail;

   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(name = "user_id")
   private User user;

    public static Post createPost(User user, String title, String content, String date, String videoPath, String videothumbnail){
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setDate(date);
        post.setVideoPath(videoPath);
        post.setVideothumbnail(videothumbnail);
        return post;
    }

}