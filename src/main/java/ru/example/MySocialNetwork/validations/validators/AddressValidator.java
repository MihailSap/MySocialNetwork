package ru.example.MySocialNetwork.validations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.example.MySocialNetwork.validations.constraints.Address;

public class AddressValidator implements ConstraintValidator<Address, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) {
            return true;
        }

        var parts = s.split(" ");
        if (parts.length != 2) {
            return false;
        }

        var city = parts[0];
        var cityChars = city.toCharArray();
        for(int i = 0; i < cityChars.length - 1; i++){
            if (!Character.isLetter(cityChars[i])){
                return false;
            }
        }

        var country = parts[1];
        for(var ch : country.toCharArray()) {
            if (!Character.isLetter(ch)) {
                return false;
            }
        }

        return Character.isUpperCase(city.charAt(0))
                && Character.isUpperCase(country.charAt(0))
                && city.endsWith(",");
    }
}
