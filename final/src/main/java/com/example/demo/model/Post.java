package com.example.demo.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
    private long view;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Post createPost(User user, String title, String content, String date){
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setDate(date);
        return post;
    }

}
