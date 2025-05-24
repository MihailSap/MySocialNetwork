package ru.example.MySocialNetwork.dto;

import lombok.Data;

@Data
public class PostDTO {

    private long id;

    private String title;

    private String text;
}
