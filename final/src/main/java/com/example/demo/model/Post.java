package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String content;
    // private String file;
    private String phone;
    private long year;
    private long month;
    private long day;
    private boolean open;
    private long like;
    private long view;

        
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)

    User user;
}