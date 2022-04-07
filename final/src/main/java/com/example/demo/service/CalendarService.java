package com.example.demo.service;

import javax.transaction.Transactional;

import com.example.demo.model.Calendar;
import com.example.demo.model.User;
import com.example.demo.repository.CalendarRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CalendarService {
    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    UserRepository userRepository;

    // 달력 일정 추가
    public void addSchedule(String email, String title, String start, String end){
        User findUser = userRepository.findByEmail(email);
        Calendar calendar = Calendar.createCalendar(findUser, title, start, end);

        calendarRepository.save(calendar);
    }

    public List<Calendar> showSchedule(String email){
        User findUser = userRepository.findByEmail(email);
        List<Calendar> scheduleList = calendarRepository.findByUser(findUser);
        return scheduleList;
    }
}
