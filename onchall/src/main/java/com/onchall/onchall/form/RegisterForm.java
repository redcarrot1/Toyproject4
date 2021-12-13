package com.onchall.onchall.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterForm {
    String itemName;
    Integer price;
    Integer originPrice;
    String description;
    Long categoryId;
    MultipartFile image;
    MultipartFile fileData;
}
