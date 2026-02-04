package com.scheduleapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 고유 식별자 ID 생성
    private Long id;
    @Column(length = 30, nullable = false) // Lv.7 일정 제목 30자 제한, 필수값처리
    private String title;
    @Column(length = 200, nullable = false) // Lv.7 일정 내용 200자 제한, 필수값처리
    private String contents;
    @Column(nullable = false) // Lv.7 작성자명 필수값 처리
    private String name;
    @Column(nullable = false) // Lv.7 비밀번호 필수값 처리
    private String password;

    public Schedule(String title, String contents, String name, String password) {
        this.title = title;
        this.contents = contents;
        this.name = name;
        this.password = password;
    }

    public void update(String title, String name) {
        this.title = title;
        this.name = name;
    }
}
