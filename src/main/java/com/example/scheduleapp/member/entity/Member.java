package com.example.scheduleapp.member.entity;

import com.example.scheduleapp.global.entity.BaseDateTime;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table (name = "users")
public class Member extends BaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    public Member() {

    }

    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
