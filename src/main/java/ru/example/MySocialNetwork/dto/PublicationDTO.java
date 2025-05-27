package ru.example.MySocialNetwork.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PublicationDTO {

    private long id;

    private String title;

    private String text;

    private String authorName;

    private int likesCount;

    private LocalDateTime createdTime;

    private List<CommentDTO> comments;
}
