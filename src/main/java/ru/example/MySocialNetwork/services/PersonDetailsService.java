package ru.example.MySocialNetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.MySocialNetwork.repositories.PersonRepository;
import ru.example.MySocialNetwork.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var person = personRepository.findByUsername(username);

        if (person.isEmpty()){
            throw new UsernameNotFoundException("Пользователь по имени не найден");
        }

        return new PersonDetails(person.get());
    }
}
