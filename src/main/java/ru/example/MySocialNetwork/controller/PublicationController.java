package ru.example.MySocialNetwork.controller;

import io.micrometer.core.annotation.Counted;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.MySocialNetwork.aspects.RequestTimeLimit;
import ru.example.MySocialNetwork.dto.PublicationDTO;
import ru.example.MySocialNetwork.services.LikeService;
import ru.example.MySocialNetwork.services.MetricsService;
import ru.example.MySocialNetwork.services.PublicationService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/publications")
public class PublicationController {

    private final PublicationService publicationService;
    private final LikeService likeService;
    private final MetricsService metricsService;

    @Counted(value = "create.publication.requests.counter", description = "Количество попыток создать публикацию")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PublicationDTO publicationDTO){
        var newPublicationDTO = publicationService.create(publicationDTO);
        return ResponseEntity.ok(Map.of("publication", newPublicationDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicationById(@PathVariable long id){
        var postDTO = publicationService.getPublicationById(id);
        return ResponseEntity.ok(Map.of("publication", postDTO));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody PublicationDTO publicationDTO){
        var updatedPostDTO = publicationService.update(id, publicationDTO);
        return ResponseEntity.ok(Map.of("publication", updatedPostDTO));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable long id){
        publicationService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Публикация успешно удалена"));
    }

    @RequestTimeLimit
    @PostMapping("/{id}/like")
    public ResponseEntity<?> likePublication(@PathVariable long id){
        var response = likeService.likePublication(id);
        metricsService.incrementPersonLikeCounter();
        return ResponseEntity.ok(Map.of("message", response));
    }

    @GetMapping("/show-all")
    public ResponseEntity<?> showAllSortedPublications(){
        var publications = publicationService.getAllSortedPublications();
        return ResponseEntity.ok(Map.of("publications", publications));
    }
}
