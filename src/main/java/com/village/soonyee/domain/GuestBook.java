package com.village.soonyee.domain;

import com.village.soonyee.dto.GuestBookUpdateDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class GuestBook {
    @Id
    @GeneratedValue
    @Column(name = "guestBook_id")
    private long id;
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id",nullable = false)
    private Member writer;

    private Long target_id;

    private int date;

    public GuestBook() {}

    public GuestBook(String content,int date) {
        this.content = content;
        this.date=date;
    }
    public void update(GuestBookUpdateDto guestBookUpdateDto){
        this.content=guestBookUpdateDto.getContent();
    }
}
