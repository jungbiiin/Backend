package com.village.soonyee.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@RequiredArgsConstructor
public class GuestBookUpdateDto {
    @NotBlank(message = "content should be valid")
    private String content;
}
