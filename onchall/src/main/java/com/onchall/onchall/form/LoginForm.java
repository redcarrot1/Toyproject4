package com.onchall.onchall.form;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LoginForm {
    @Email @NotBlank
    String email;
    @Size(min=6, max=20)
    String password;
}
