package com.village.soonyee.dto;

import com.village.soonyee.util.entity.IEntitySource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DiaryDto implements IEntitySource {
    @NotBlank(message = "title should be valid")
    private String title;
    @NotBlank(message = "content should be valid")
    private String content;
    @NotBlank(message = "date should be valid")
    private int date;
}
