package ru.example.MySocialNetwork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ru.example.MySocialNetwork.validations.constraints.Address;
import ru.example.MySocialNetwork.validations.constraints.BirthDate;
import ru.example.MySocialNetwork.validations.constraints.Username;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Username
    private String username;

    @NotEmpty(message = "{validation.errors.person_password}")
    private String password;

    @Email(message = "{validation.errors.person_email}")
    private String email;

    private String phoneNumber;

    @Address
    private String address;

    @BirthDate
    private String birthDate;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publication> publications = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();
}
