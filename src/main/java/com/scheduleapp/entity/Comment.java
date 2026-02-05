package com.scheduleapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// 댓글 Entity
@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false) // Lv.7 댓글 내용 100자 제한, 필수값 처리
    private String contents;
    @Column(nullable = false) // Lv.7 작성자명 필수값 처리
    private String name;
    @Column(nullable = false) // Lv.7 비밀번호 필수값 처리
    private String password;
    // Foreign Key
    private Long scheduleId; // Lv.5 한 일정에 여러 댓글을 작성하는 것이므로 Comment에 scheduleId 필드 주입하기


    public Comment(String contents, String name, String password, Long scheduleId) {
        this.contents = contents;
        this.name = name;
        this.password = password;
        this.scheduleId = scheduleId;
    }
}
