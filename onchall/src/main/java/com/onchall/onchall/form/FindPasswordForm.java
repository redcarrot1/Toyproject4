package com.onchall.onchall.form;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class FindPasswordForm {
    String name;
    @Email
    String email;
}
