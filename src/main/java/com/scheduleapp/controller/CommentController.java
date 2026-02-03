package com.scheduleapp.controller;

import com.scheduleapp.dto.CreateCommentRequest;
import com.scheduleapp.dto.CreateCommentResponse;
import com.scheduleapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 도전과제 Lv.5 댓글 생성: PostMapping
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> create(
            // scheduleId로 부터 일정을 가져와서 댓글을 달아야하기 때문에 id 받아오기
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.save(scheduleId, request));
    }
}
