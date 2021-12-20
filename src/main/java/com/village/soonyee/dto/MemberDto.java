package com.village.soonyee.dto;

import com.village.soonyee.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MemberDto {
    @Email(message = "email should be valid")
    @NotBlank(message = "email should be valid")
    private String email;
    @NotBlank(message = "password should be valid")
    private String password;
    @NotBlank(message = "name should be valid")
    private String name;
    public Member toEntity(){
        return new Member(name,email,password);
    }
}
