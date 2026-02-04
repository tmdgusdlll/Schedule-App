package com.scheduleapp.dto.schedule;

import com.scheduleapp.dto.comment.GetCommentResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetScheduleDetailResponse {

    private final Long id;
    private final String title;
    private final String contents;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private List<GetCommentResponse> comments;

    public GetScheduleDetailResponse(Long id, String title, String contents, String name,  LocalDateTime createdAt,
                                     LocalDateTime modifiedAt, List<GetCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }
}
