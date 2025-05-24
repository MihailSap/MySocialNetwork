package ru.example.MySocialNetwork.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.MySocialNetwork.dto.AuthenticationDTO;
import ru.example.MySocialNetwork.dto.PersonDTO;
import ru.example.MySocialNetwork.models.Person;
import ru.example.MySocialNetwork.services.RegistrationService;
import ru.example.MySocialNetwork.util.PersonValidator;

import java.util.List;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody PersonDTO personDTO,
                                          BindingResult bindingResult){
        var person = convertToPersonFromDTO(personDTO);
        personValidator.validate(person, bindingResult);
        registrationService.register(person);
        return ResponseEntity.ok(Map.of("message", "Регистрация прошла успешно!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO, HttpSession session){
        var authenticationInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(), authenticationDTO.getPassword()
        );

        var authentication = authenticationManager.authenticate(authenticationInputToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        return ResponseEntity.ok(Map.of("message", "Login successful!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> performLogout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logout successful!"));
    }

    public Person convertToPersonFromDTO(PersonDTO personDTO){
        return this.modelMapper.map(personDTO, Person.class);
    }
}
