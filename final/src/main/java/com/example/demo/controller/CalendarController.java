package com.example.demo.controller;

import java.util.List;

import com.example.demo.model.Calendar;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CalendarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Transactional
public class CalendarController {
    @Autowired
    CalendarService calendarservice;

    @Autowired
    UserRepository userRepository;


    // 일정 추가
    @PostMapping("/addCalendar")
    @ResponseBody
    public void addSchedule(String email, String title, String start, String end){
        calendarservice.addSchedule(email, title, start, end);
    }

    // 일정 달력에 보여주기
    @PostMapping("/calendar")
    @ResponseBody
    public List<Calendar> showSchedule(String email){
        // System.out.println(email);
        return calendarservice.showSchedule(email);
    }

    // 일정 삭제
    @DeleteMapping(value = "/calendar/{id}")
    public void deleteSchedule(@PathVariable("id") long id){
        calendarservice.deleteSchedule(id);
    }
}
