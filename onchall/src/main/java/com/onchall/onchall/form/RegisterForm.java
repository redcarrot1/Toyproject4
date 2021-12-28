package com.onchall.onchall.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterForm {
    @NotBlank
    String itemName;

    @Min(value=0)
    int price;

    @Min(value=0)
    int originPrice;

    String description;

    Long categoryId;

    MultipartFile image;
    MultipartFile fileData;
}
