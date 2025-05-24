package ru.example.MySocialNetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.MySocialNetwork.dto.CommentDTO;
import ru.example.MySocialNetwork.services.CommentService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{id}/create-comment")
    public ResponseEntity<?> create(@PathVariable long id, @RequestBody CommentDTO comment){
        var commentDTO = commentService.create(id, comment);
        return ResponseEntity.ok(Map.of("comment", commentDTO));
    }

    @PutMapping("/comment/{id}/update")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody CommentDTO comment){
        var commentDTO = commentService.update(id, comment);
        return ResponseEntity.ok(Map.of("comment", commentDTO));
    }

    @DeleteMapping("/comment/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable long id){
        commentService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Комментарий успешно удалён"));
    }

//    @GetMapping("/posts/{id}/comments}")
//    public ResponseEntity<?> getCommentsOfPost(@PathVariable long id){
//
//    }
}
