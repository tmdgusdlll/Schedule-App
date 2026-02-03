package com.scheduleapp.controller;

import com.scheduleapp.dto.CreateScheduleRequest;
import com.scheduleapp.dto.CreateScheduleResponse;
import com.scheduleapp.dto.GetScheduleResponse;
import com.scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // Lv.1 일정 생성: PostMapping
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> create(@RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(scheduleService.save(request));
    }

    // Lv.2 일정 전체 조회: GetMapping
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAll(@RequestParam (required = false) String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(scheduleService.getAll(name));
    }
}
