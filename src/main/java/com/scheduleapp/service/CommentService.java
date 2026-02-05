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
//        System.out.println("보내온 내용: " + request.getContents());
        // 일정이 존재하는지부터 확인(무결성 체크) , 없다면 예외 던지기.
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalStateException("해당 일정이 존재하지 않습니다.");
        }
        // 존재한다면 Comment 생성
        // 카운트 조건 메서드 사용하여 count 변수에 담기.
        long count = commentRepository.countByScheduleId(scheduleId); // DB는 int가 아니라 BIGINT(64bit)로 반환 -> long 타입
        if (count >= 10) { // 카운트(댓글)가 10개 넘는다면 예외
            throw new IllegalStateException("댓글이 10개로 제한되어있습니다.");
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
        // 요청받은 댓글들 데이터를 comment 변수에 담은 상황에서 저장소에 저장한 순간 Comment 타입의 새로운 객체 savedComment 생성됨.
        // 그 savedComment 객체를 CreateCommentResponse dto로 반환해라.
//        return new CreateCommentResponse(savedComment);
    }
}
