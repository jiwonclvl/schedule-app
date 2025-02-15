package com.example.scheduleapp.comment.entity;

import com.example.scheduleapp.global.entity.BaseDateTime;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comments")
public class Comment extends BaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment() {

    }

    public Comment(String content, Member member, Schedule schedule) {
        this.content = content;
        this.member = member;
        this.schedule = schedule;
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
