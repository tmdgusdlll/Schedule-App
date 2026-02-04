package com.scheduleapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
//    // Schedule 클래스와 연결관계를 갖기위한 추가적인 어노테이션과 필드, 생성자까지
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "schedule_id")
    // JPA 연관관계 사용하지 않기
    private Long scheduleId;


    public Comment(String contents, String name, String password, Long scheduleId) {
        this.contents = contents;
        this.name = name;
        this.password = password;
        this.scheduleId = scheduleId;
    }
}
