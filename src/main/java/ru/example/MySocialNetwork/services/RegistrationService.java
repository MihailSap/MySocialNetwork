package ru.example.MySocialNetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.MySocialNetwork.models.Person;
import ru.example.MySocialNetwork.repositories.PersonRepository;

@Service
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PersonRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person){
        var encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        personRepository.save(person);
    }
}