package ru.example.MySocialNetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.MySocialNetwork.dto.PersonDTO;
import ru.example.MySocialNetwork.services.PersonService;

import java.util.Map;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable long id, @RequestBody PersonDTO personDTO) {
        var person = personService.update(id, personDTO);
        return ResponseEntity.ok(Map.of("person", person));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable long id) {
        var person = personService.getPersonDTOById(id);
        return ResponseEntity.ok(Map.of("person", person));
    }
}
