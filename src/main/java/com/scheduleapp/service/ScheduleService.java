package com.scheduleapp.service;

import com.scheduleapp.dto.*;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    // Lv.2 일정 선택 조회
    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );
        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // Lv.3 일정 수정 (선택수정)
    @Transactional
    // 고유 id 와 수정요청에 맞게 업데이트 할것이다.
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        // 레포지토리에서 id에 맞게 찾아주고, 그 해당 일정이 없다면 예외를 던져라.
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        ); // 만약 일정 비번이랑 요청 비번이랑 같지 않다면 예외
        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } else { // 같아면 제목과 이름만 변경할 수 있게
            schedule.update(request.getTitle(), request.getName());
        } // 업데이트 된 모든 것을 응답 dto에 담아서 반환
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // Lv.4 일정 삭제
    @Transactional
    public void delete(Long scheduleId, DeleteScheduleRequest request) {
//        boolean existence = scheduleRepository.existsById(scheduleId);
//
//        if (!existence) {
//            throw new IllegalStateException("존재하지 않는 일정입니다.");
//        }
//        scheduleRepository.deleteById(scheduleId);

        // 삭제할 일정을 id를 기준으로 찾아서 schedule 변수에 담겠다
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                // 해당 id에 일정이 없다면 예외
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        ); // 만약 일정 비번이랑 요청 비번이 다르다면 예외
        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } // 일치하다면 schedule에 담겨있던 일정 삭제
        scheduleRepository.delete(schedule);
    }
}
