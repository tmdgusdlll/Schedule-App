package com.scheduleapp.service;

import com.scheduleapp.dto.CreateCommentRequest;
import com.scheduleapp.dto.CreateCommentResponse;
import com.scheduleapp.entity.Comment;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.CommentRepository;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    // Lv.5 댓글 생성
    @Transactional
    // Schedule과의 연결을 위해 생성자에 scheduleId 주입
    public CreateCommentResponse save(Long scheduleId, CreateCommentRequest request) {
        // 일정이 존재하는지부터 확인 있으면 schedule변수에 담고 없으면 예외
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("일정이 존재하지 않습니다.")
        );

        Comment savedcomment = new Comment(
                request.getContents(),
                request.getName(),
                request.getPassword(),
                schedule
        );
        commentRepository.save(savedcomment);
        return new CreateCommentResponse(
                savedcomment.getId(),
                savedcomment.getContents(),
                savedcomment.getName(),
                savedcomment.getCreatedAt(),
                savedcomment.getModifiedAt()
        );
    }
}
