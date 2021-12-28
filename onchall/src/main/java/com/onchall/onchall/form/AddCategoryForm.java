package com.onchall.onchall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddCategoryForm {
    @NotBlank
    String name;
}
