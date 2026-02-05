package com.scheduleapp.dto.schedule;

import com.scheduleapp.dto.comment.GetCommentResponse;
import com.scheduleapp.entity.Schedule;
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
    private List<GetCommentResponse> comments; // Lv.6 일정 단 건 조회시 댓글들을 포함하기 위한 필드 선언

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

//    public GetScheduleDetailResponse(Schedule schedule, List<GetCommentResponse> comments) {
//        this.id = schedule.getId();
//        this.title = schedule.getTitle();
//        this.contents = schedule.getContents();
//        this.name = schedule.getName();
//        this.createdAt = schedule.getCreatedAt();
//        this.modifiedAt = schedule.getModifiedAt();
//        this.comments = comments;
//    }
}
