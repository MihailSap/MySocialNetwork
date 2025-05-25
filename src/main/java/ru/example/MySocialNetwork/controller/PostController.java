package ru.example.MySocialNetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.MySocialNetwork.aspects.annotations.RequestTimeLimit;
import ru.example.MySocialNetwork.dto.PostDTO;
import ru.example.MySocialNetwork.services.LikeService;
import ru.example.MySocialNetwork.services.PostService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PostDTO postDTO){
        var newPostDTO = postService.create(postDTO);
        return ResponseEntity.ok(Map.of("post", newPostDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable long id){
        var postDTO = postService.getPostById(id);
        return ResponseEntity.ok(Map.of("post", postDTO));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody PostDTO postDTO){
        var updatedPostDTO = postService.update(id, postDTO);
        return ResponseEntity.ok(Map.of("post", updatedPostDTO));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable long id){
        postService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Пост успешно удалён"));
    }

    @RequestTimeLimit
    @PostMapping("/{id}/like")
    public ResponseEntity<?> likePost(@PathVariable long id){
        var response = likeService.likePost(id);
        return ResponseEntity.ok(Map.of("message", response));
    }
}
