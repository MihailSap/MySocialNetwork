package ru.example.MySocialNetwork.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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

    private String email;

//    private Date dateOfBirth;

    private String phoneNumber;

    private String address;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Comment> comments;
}
