package com.scheduleapp.dto.schedule;

import lombok.Getter;

@Getter
public class GetScheduleRequest {
    private String title;
    private String contents;
    private String name;
    private String password;
}
