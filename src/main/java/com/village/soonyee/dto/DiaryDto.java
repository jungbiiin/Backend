package com.village.soonyee.dto;

import com.village.soonyee.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DiaryDto {
    @NotBlank(message = "title should be valid")
    private String title;
    @NotBlank(message = "content should be valid")
    private String content;
    @NotBlank(message = "date should be valid")
    private int date;
    public Diary toEntity(){
        return new Diary(title,content,date);
    }
}
