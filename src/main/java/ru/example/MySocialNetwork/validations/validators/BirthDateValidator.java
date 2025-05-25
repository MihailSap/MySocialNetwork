package ru.example.MySocialNetwork.validations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.example.MySocialNetwork.validations.constraints.BirthDate;

import java.time.LocalDateTime;

public class BirthDateValidator implements ConstraintValidator<BirthDate, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) {
            return true;
        }

        for(var ch : s.toCharArray()){
            if (!Character.isDigit(ch) && ch != '.'){
                return false;
            }
        }

        var parts = s.split("\\.");
        if (parts.length != 3){
            return false;
        }

        var day = Integer.parseInt(parts[0]);
        if (day < 1 || day > 31){
            return false;
        }

        var month = Integer.parseInt(parts[1]);
        if (month < 1 || month > 12){
            return false;
        }

        var year = Integer.parseInt(parts[2]);
        var currentYear = LocalDateTime.now().getYear();
        return year >= (currentYear - 115) && year <= currentYear;
    }
}
