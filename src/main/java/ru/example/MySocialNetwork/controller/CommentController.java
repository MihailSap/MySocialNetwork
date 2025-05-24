package ru.example.MySocialNetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.MySocialNetwork.dto.CommentDTO;
import ru.example.MySocialNetwork.models.Comment;
import ru.example.MySocialNetwork.services.CommentService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}/create-comment")
    public ResponseEntity<?> create(@PathVariable long id, @RequestBody CommentDTO comment){
        var commentDTO = commentService.create(id, comment);
        return ResponseEntity.ok(Map.of("comment", commentDTO));
    }
}
