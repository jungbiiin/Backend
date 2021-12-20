package com.village.soonyee.dto;

import com.village.soonyee.domain.Posting;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor @NoArgsConstructor
public class PostingDto {
    @NotBlank(message = "title should be valid")
    private String title;
    @NotBlank(message = "content should be valid")
    private String content;
    @NotBlank(message="Date should be valid")
    private int date;

    public Posting toEntity(){
        return Posting.builder()
                .content(this.content)
                .title(this.title)
                .date(this.date)
                .build();
    }
}
