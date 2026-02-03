package com.scheduleapp.service;

import com.scheduleapp.dto.comment.CreateCommentRequest;
import com.scheduleapp.dto.comment.CreateCommentResponse;
import com.scheduleapp.entity.Comment;
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
    // 일정 고유 id에 댓글을 달기 위해 생성자에 scheduleId 받기
    public CreateCommentResponse save(Long scheduleId, CreateCommentRequest request) {
        // 일정이 존재하는지부터 확인 있으면 schedule변수에 담고 없으면 예외
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("해당 일정이 존재하지 않습니다.");
        }

        // 카운트 조건 메서드 사용하여 count 변수에 담기.
        long count = commentRepository.countByScheduleId(scheduleId);
        if (count > 10) { // 카운트(댓글)가 10개 넘는다면 예외
            throw new IllegalArgumentException("댓글이 10개로 제한되어있습니다.");
        } // 아니라면 댓글 정보 가져와서 comment 변수에 담기.
        Comment comment = new Comment(
                request.getContents(),
                request.getName(),
                request.getPassword(),
                scheduleId
        );
        // 댓글 정보를 commentRepository에 저장하고 savedComment 변수에 담기 (dto로 감싸기 위함)
        Comment savedComment = commentRepository.save(comment);

        // savedComment 내용들을 CreateCommentResponse(dto) 타입으로 반환
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContents(),
                savedComment.getName(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }
}
