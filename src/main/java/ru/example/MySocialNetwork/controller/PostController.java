package ru.example.MySocialNetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.MySocialNetwork.dto.PostDTO;
import ru.example.MySocialNetwork.services.PostService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PostDTO postDTO){
        var newPostDTO = postService.create(postDTO);
        return ResponseEntity.ok(Map.of("post", newPostDTO));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> getPostById(@PathVariable long id){
        var postDTO = postService.getPostById(id);
        return ResponseEntity.ok(Map.of("post", postDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody PostDTO postDTO){
        var updatedPostDTO = postService.update(id, postDTO);
        return ResponseEntity.ok(Map.of("post", updatedPostDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        postService.delete(id);
        return ResponseEntity.ok("Пост успешно удалён");
    }
}
