package com.scheduleapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 고유 식별자 ID 생성
    private Long id;
    private String title;
    private String contents;
    private String name;
    private int pw;
    private LocalDateTime createdAt; // 작성, 수정일 (JpaAuditing 활용)
    private LocalDateTime modifiedAt;

    public Schedule(String contents, String title, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.contents = contents;
        this.title = title;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void update(String contents, String title, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.contents = contents;
        this.title = title;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
