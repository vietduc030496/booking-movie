package com.ntu.domain.authentication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "{validation.email.require}")
    @Email(message = "{validation.email.format}")
    private String email;

    @NotEmpty(message = "{validation.password.empty}")
    private String password;
}
