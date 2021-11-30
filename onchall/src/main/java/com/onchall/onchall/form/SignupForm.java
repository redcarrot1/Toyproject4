package com.onchall.onchall.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupForm {
    @NotBlank
    String name;
    @NotBlank @Email
    String email;
    @Size(min=6, max=20)
    String password;
}
