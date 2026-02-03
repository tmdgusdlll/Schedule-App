package com.scheduleapp.controller;

import com.scheduleapp.dto.comment.CreateCommentRequest;
import com.scheduleapp.dto.comment.CreateCommentResponse;
import com.scheduleapp.dto.comment.GetScheduleCommentResponse;
import com.scheduleapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 도전기능 Lv.5 댓글 생성: PostMapping
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> create(
            // scheduleId로 부터 일정을 가져와서 댓글을 달아야하기 때문에 id 받아오기
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.save(scheduleId, request));
    }

    // 도전기능 Lv.6 일정 단 건 조회 (해당 일정에 등록된 댓글 포함): GetMapping
//    @GetMapping("/schedules/{scheduleId}/comments/{commentsId}")
//    public ResponseEntity<GetScheduleCommentResponse> getSdCm() {
//
//    }
}
