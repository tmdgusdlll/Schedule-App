package com.scheduleapp.service;

import com.scheduleapp.dto.comment.GetCommentResponse;
import com.scheduleapp.dto.schedule.*;
import com.scheduleapp.entity.Comment;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.CommentRepository;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;


    // Lv.1 일정 생성
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        // 스케줄 데이터들을 받을 schedule 변수 생성 후 담기
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContents(),
                request.getName(),
                request.getPassword()
        );
        // 스케줄 데이터를 Entity 그대로 밖으로 보내면 (응답하면) Entity 내의 민감한 정보가 위험할 수 있음
        // -> dto를 사용해 안전하게 반환 여기서 dto 응답 클래스는 CreateScheduleResponse
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getName(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
//        return new CreateScheduleResponse(savedSchedule);
    }

    // Lv.2 일정 전체 조회
    @Transactional(readOnly = true) // 조회만 할 경우엔 데이터의 수정이 없기 때문에 읽기전용으로 설정해 메모리를 아끼자(효율성)
    public List<GetScheduleResponse> getAll(String name) {
        List<Schedule> allSchedules; // 조회될 전체 일정을 담을 리스트

        // Lv.2 작성자명 기준으로 일정 전체 조회(null이 아니면 이름기준 전체 조회 + 수정일 기준으로 내림차순)
        // 만약 작성자명이 null이 아니고 빈 값이 아니라면 findAllByNameOrderByModifiedAtDesc() 실행하여 allSchedules 변수에 담기.
        if (name != null && !name.isEmpty()) {
            allSchedules = scheduleRepository.findAllByNameOrderByModifiedAtDesc(name); // 해당 메서드는 ScheduleRepository에서 정의함
        } else {
            // null이면 그냥 수정일 기준 내림차순 조회
            allSchedules = scheduleRepository.findAllByOrderByModifiedAtDesc(); // 해당 메서드도 ScheduleRepository에서 정의함
        }
        // 전체 조회 결과 또한 dto로 반환해야 함
        List<GetScheduleResponse> dtos = new ArrayList<>();

        // allSchedules에 담김 요소들을 돌면서 schedule을 내보낸다.
        for (Schedule schedule : allSchedules) {
            // 그 schedule들을 반환타입이 GetscheduleResponse 타입인 dto라는 변수에 담아라.
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContents(),
                    schedule.getName(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            // 그 dto를 dtos 리스트에 추가해라.
            dtos.add(dto);
        }
        return dtos;
    }

    // Lv.2 일정 선택 조회
    @Transactional(readOnly = true) // 마찬가지로 조회이므로 읽기전용 설정
    // 일정 id를 입력받아 조회하겠다.
    public GetScheduleDetailResponse getOne(Long scheduleId) {
        // 입력받은 id의 일정을 찾아서 schedule 변수에 담고, 없다면 예외를 던저라
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );
        // 일정 id를 기준으로 댓글들을 찾아서 반환타입이 List 타입인 coms 에 담겠다.
        List<Comment> coms = commentRepository.findByScheduleId(scheduleId);

        // 최종 GetCommentResponse 타입인 댓글들을 담을 comments 리스트 생성.
        List<GetCommentResponse> comments = new ArrayList<>();

        // dto로 감싸기
        // coms를 순회하면서 각각 내용들을 dto 변수에 담아 GetCommentResponse 타입으로 반환하겠다.
        for (Comment c : coms) {
            GetCommentResponse dto = new GetCommentResponse(
                    c.getId(),
                    c.getContents(),
                    c.getName(),
                    c.getCreatedAt(),
                    c.getModifiedAt()
            );
            // 최종 List<GetCommentResponse> 타입의 comments 리스트에 dto를 추가해라.
            comments.add(dto);
        }
        // 담긴 schedule + 댓글을 dto에 감싸서 반환하기
        return new GetScheduleDetailResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                comments
        );
//        return new GetScheduleDetailResponse(schedule, comments);
    }

    // Lv.3 일정 수정 (선택수정)
    @Transactional
    // 고유 id 와 수정요청에 맞게 업데이트 할것이다.
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        // 레포지토리에서 id에 맞게 찾아주고, 그 해당 일정이 없다면 예외를 던져라.
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );
//        // 로그 찍어보기 (터미널에서 확인용)
//        System.out.println("DB에서 가져온 비번: " + schedule.getPassword());
//        System.out.println("사용자가 입력한 비번: " + request.getPassword());

        // 만약 일정 비번이랑 요청 비번이랑 같지 않다면 예외
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
    public void deleteSchedule(Long scheduleId, DeleteScheduleRequest request) {
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
