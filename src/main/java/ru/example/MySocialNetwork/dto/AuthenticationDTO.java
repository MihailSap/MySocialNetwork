package ru.example.MySocialNetwork.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.example.MySocialNetwork.validations.constraints.Username;

@Data
public class AuthenticationDTO {

    @Username
    private String username;

    @NotEmpty(message = "{validation.errors.person_password}")
    private String password;
}
