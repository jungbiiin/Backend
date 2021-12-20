package com.village.soonyee.domain;

import com.village.soonyee.dto.PostingDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@Getter @Setter
public class Posting {
    @Id
    @GeneratedValue
    @Column(name = "posting_id")
    private Long id;

    private String title;
    private String content;
    private int date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id",nullable = false)
    private Member writer;

    public Posting() {}

    public void update(PostingDto postingDto){
        this.title = postingDto.getTitle();
        this.content = postingDto.getContent();
    }
}
