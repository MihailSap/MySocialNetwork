package ru.example.MySocialNetwork.mappers;

import org.springframework.stereotype.Component;
import ru.example.MySocialNetwork.dto.PersonDTO;
import ru.example.MySocialNetwork.models.Person;

@Component
public class PersonMapper {

    public PersonDTO mapToDTO(Person person){
        var personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setUsername(person.getUsername());
        personDTO.setPhoneNumber(person.getPhoneNumber());
        personDTO.setEmail(person.getEmail());
        personDTO.setAddress(person.getAddress());
        return personDTO;
    }
}
