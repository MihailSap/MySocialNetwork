package ru.example.MySocialNetwork.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.MySocialNetwork.models.Person;
import ru.example.MySocialNetwork.services.PersonDetailsService;

@RequiredArgsConstructor
@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var person = (Person) target;
        try{
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch(UsernameNotFoundException ignored){
            return;
        }
        errors.rejectValue("username", "",
                "Человек с таким именем пользователя уже существует");
    }
}
