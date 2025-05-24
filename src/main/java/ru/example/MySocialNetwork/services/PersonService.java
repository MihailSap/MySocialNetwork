package ru.example.MySocialNetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.MySocialNetwork.dto.PersonDTO;
import ru.example.MySocialNetwork.mappers.PersonMapper;
import ru.example.MySocialNetwork.repositories.PersonRepository;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Transactional
    public PersonDTO update(long id, PersonDTO personDTO){
        var person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким ID не был найден"));

        person.setPhoneNumber(personDTO.getPhoneNumber());
        person.setEmail(personDTO.getEmail());
        person.setAddress(personDTO.getAddress());

        var newPerson = personRepository.save(person);
        return personMapper.mapToDTO(newPerson);
    }

    public PersonDTO getPersonDTOById(long id){
        var person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким ID не найден"));
        return personMapper.mapToDTO(person);
    }
}
