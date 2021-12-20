package com.village.soonyee.dto;


import com.village.soonyee.domain.GuestBook;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class GuestBookDto {
    @NotBlank(message = "Content should be valid")
    private String content;
    @NotBlank(message = "Date should be valid")
    private int date;
    public GuestBook toEntity(){
        return new GuestBook(content,date);
    }
}
