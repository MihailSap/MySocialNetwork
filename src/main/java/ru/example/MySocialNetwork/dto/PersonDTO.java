package ru.example.MySocialNetwork.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import ru.example.MySocialNetwork.validations.constraints.Address;
import ru.example.MySocialNetwork.validations.constraints.BirthDate;
import ru.example.MySocialNetwork.validations.constraints.Username;

@Data
public class PersonDTO {

    private long id;

    @Username
    private String username;

    @Email(message = "{validation.errors.person_email}")
    private String email;

    private String phoneNumber;

    @Address
    private String address;

    @BirthDate
    private String birthDate;
}
