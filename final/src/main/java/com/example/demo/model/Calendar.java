package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue
    private long id;

    private String title;
    private String start;
    private String end;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public static Calendar createCalendar(User user, String title, String start, String end){
        Calendar calendar = new Calendar();
        calendar.setUser(user);
        calendar.setTitle(title);
        calendar.setStart(start);
        calendar.setEnd(end);
        return calendar;
    }
}
