package com.example.scheduleapp.entity;

import jakarta.persistence.*;

import lombok.Getter;

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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Schedule() {

    }

    public Schedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
