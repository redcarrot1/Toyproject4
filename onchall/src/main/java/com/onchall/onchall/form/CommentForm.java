package com.onchall.onchall.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {
    @Range(min = 1, max = 5)
    Integer rating;
    @NotBlank
    String content;
}
