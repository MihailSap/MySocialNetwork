package ru.example.MySocialNetwork.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PersonDTO {
    private long id;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String address;
}
