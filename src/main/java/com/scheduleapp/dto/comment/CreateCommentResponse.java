package com.scheduleapp.dto.comment;

import com.scheduleapp.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    private final Long id;
    private final String contents;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CreateCommentResponse(Long id, String contents, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Comment를 객체로 받음으로서 Comment 타입이 들어오면 얘가 알아서 데이터들을 가져오게 함.
    // Service에서는 Comment 타입의 객체만 잘 넣어주면 됨.
//    public CreateCommentResponse(Comment comment) {
//        this.id = comment.getId();
//        this.contents = comment.getContents();
//        this.name = comment.getName();
//        this.createdAt = comment.getCreatedAt();
//        this.modifiedAt = comment.getModifiedAt();
//    }

}
