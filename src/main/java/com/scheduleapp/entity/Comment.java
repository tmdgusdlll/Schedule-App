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
    private String contents;
    private String name;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;


    public Comment(String password, String name, String contents, Schedule schedule) {
        this.password = password;
        this.name = name;
        this.contents = contents;
        this.schedule = schedule;
    }
}
