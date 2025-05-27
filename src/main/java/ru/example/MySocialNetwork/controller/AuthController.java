package ru.example.MySocialNetwork.controller;

import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.MySocialNetwork.dto.AuthenticationDTO;
import ru.example.MySocialNetwork.models.Person;
import ru.example.MySocialNetwork.services.RegistrationService;
import ru.example.MySocialNetwork.util.PersonValidator;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    @Timed(value = "registration.requests", description = "Запросы на регистрацию")
    public ResponseEntity<?> registration(@RequestBody @Valid AuthenticationDTO authenticationDTO,
                                          BindingResult bindingResult){
        var person = convertToPersonFromDTO(authenticationDTO);
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }

        registrationService.register(person);
        return ResponseEntity.ok(Map.of("message", "Регистрация выполнена успешно"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authenticationDTO, HttpSession session){
        var authenticationInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(), authenticationDTO.getPassword()
        );

        var authentication = authenticationManager.authenticate(authenticationInputToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        return ResponseEntity.ok(Map.of("message", "Вход выполнен успешно"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Выход выполнен успешно"));
    }

    public Person convertToPersonFromDTO(AuthenticationDTO authenticationDTO){
        return this.modelMapper.map(authenticationDTO, Person.class);
    }
}
