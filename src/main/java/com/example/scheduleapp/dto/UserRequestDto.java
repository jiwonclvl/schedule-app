package com.example.scheduleapp.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank
    private final String username;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String password;

}
