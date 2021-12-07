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
    @NotBlank(message = "비밀번호는 6자 이상입니다.")
    String password;
}
