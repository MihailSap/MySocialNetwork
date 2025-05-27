package ru.example.MySocialNetwork.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private long id;

    private String text;

    private String authorName;
}
