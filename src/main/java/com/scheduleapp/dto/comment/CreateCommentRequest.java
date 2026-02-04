package com.scheduleapp.dto.comment;

import lombok.Getter;

@Getter
public class CreateCommentRequest {
    private String contents;
    private String name;
    private String password;
}
