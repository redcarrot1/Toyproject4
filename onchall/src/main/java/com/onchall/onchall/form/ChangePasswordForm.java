package com.onchall.onchall.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordForm {
    @Email
    String email;
    @Size(min=6, max=20)
    String newPassword;
}
