package ru.example.MySocialNetwork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    @Email
    private String email;

    private String phoneNumber;

    private String address;

    private String birthDate;

    @OneToMany(mappedBy = "person")
    private List<Post> posts;

    @OneToMany(mappedBy = "person")
    private List<Comment> comments;

    @OneToMany(mappedBy = "person")
    private List<Like> likes;
}
