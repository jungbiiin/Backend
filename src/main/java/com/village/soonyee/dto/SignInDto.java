package com.village.soonyee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {
    @NotBlank(message = "Id should be valid")
    private String email;
    @NotBlank(message = "Password should be valid")
    private String password;
}
