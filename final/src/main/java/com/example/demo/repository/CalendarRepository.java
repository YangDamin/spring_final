package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Calendar;
import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long>{
    List<Calendar> findByUser(User user);
}
