package ru.example.MySocialNetwork.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO {

    private long id;

    private String title;

    private String text;

    private List<CommentDTO> comments;
}
