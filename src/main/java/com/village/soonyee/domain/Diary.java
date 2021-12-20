package com.village.soonyee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Diary {
    @Id
    @GeneratedValue
    @Column(name = "diary_id")
    private Long id;
    private String title;
    private String content;
    private int date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id",nullable = false)
    private Member writer;

    public Diary() {}

    public Diary(String title, String content, int date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
