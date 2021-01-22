package ru.aicontest.form.login;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterCredentials extends UserCredentials {

    @NotEmpty
    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Incorrect email")
    private String email;

    public String getEmail() {
        return email;
    }
}
