package com.example.scheduleapp.schedule.entity;

import com.example.scheduleapp.global.entity.BaseDateTime;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.comment.entity.Comment;
import jakarta.persistence.*;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "schedules")
public class Schedule extends BaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    //외래키 지정
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();

    public Schedule() {
    }

    public Schedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

}
