package com.scheduleapp.repository;

import com.scheduleapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 10개 제한 기능을 구현하기 위한 메서드 (scheduleId를 기반으로 해당 일정에 달린 댓글만 세겠다.)
    long countByScheduleId(Long scheduleId);

    // scheduleId로 댓글 목록 조회 메서드(Lv.6 일정 단 건 조회 + 댓글 구현하기 위함)
    // 댓글 목록이니까 List로 받기
    List<Comment> findByScheduleId(Long scheduleId);
}
