package com.scheduleapp.service;

import com.scheduleapp.dto.CreateScheduleRequest;
import com.scheduleapp.dto.CreateScheduleResponse;
import com.scheduleapp.dto.GetScheduleResponse;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;


    // Lv.1 일정 생성
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContents(),
                request.getName()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getName(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    // Lv.2 일정 전체 조회
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAll(String name) {
        List<Schedule> allSchedules;

        // Lv.2 작성자명 기준으로 일정 전체 조회(null이 아니면 이름기준 전체 조회 + 수정일 기준으로 내림차순)
        if (name != null && !name.isEmpty()) {
            allSchedules = scheduleRepository.findAllByNameOrderByModifiedAtDesc(name);
        } else {
            // null이면 그냥 수정일 기준 내림차순 조회
           allSchedules = scheduleRepository.findAllOrderByModifiedAtDesc();
        }
        List<GetScheduleResponse> dtos = new ArrayList<>();

        for (Schedule schedule : allSchedules) {
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContents(),
                    schedule.getName(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
