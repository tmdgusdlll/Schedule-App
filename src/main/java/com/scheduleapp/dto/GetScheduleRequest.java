package com.scheduleapp.dto;

import lombok.Getter;

@Getter
public class GetScheduleRequest {
    private String title;
    private String contents;
    private String name;
    private String password;
}
