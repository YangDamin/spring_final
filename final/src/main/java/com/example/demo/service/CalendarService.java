package com.example.demo.service;

import javax.transaction.Transactional;

import com.example.demo.model.Calendar;
import com.example.demo.model.User;
import com.example.demo.repository.CalendarRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CalendarService {
    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    UserRepository userRepository;

    // 달력 일정 추가
    public void addSchedule(String email, String title, String start, String end){
        User user = userRepository.findByEmail(email);
        Calendar calendar = Calendar.createCalendar(user, title, start, end);

        calendarRepository.save(calendar);
    }

    // 일정 달력에 보여주기
    public List<Calendar> showSchedule(String email){
        User findUser = userRepository.findByEmail(email);
        List<Calendar> scheduleList = calendarRepository.findByUser(findUser);
        return scheduleList;
    }

    // 일정 삭제
    public void deleteSchedule(Long id){
        calendarRepository.deleteById(id);
    }
}
