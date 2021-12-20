package com.village.soonyee.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@RequiredArgsConstructor
public class StatusMessageDto {
    @NotBlank(message = "message should be valid")
    private String message;
}
