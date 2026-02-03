package com.scheduleapp.repository;

import com.scheduleapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Lv.2 작성자명 기준으로 조회하기 위한 선언 (이름 + 수정일 기준 내림차순)
    List<Schedule> findAllByNameOrderByModifiedAtDesc(String name);

    // 작성자명이 포함되지 않았을 경우 전체 조회 (수정일 기준 내림차순)
    List<Schedule> findAllOrderByModifiedAtDesc();
}
