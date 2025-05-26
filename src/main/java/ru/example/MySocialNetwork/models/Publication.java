package ru.example.MySocialNetwork.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String text;

    private LocalDateTime createdAt;

    @ManyToOne
    private Person person;

    @OneToMany(mappedBy = "publication")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "publication")
    private List<Like> likes = new ArrayList<>();
}
