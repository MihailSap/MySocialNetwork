package ru.example.MySocialNetwork.exceptions;

public class TooManyRequestsException extends RuntimeException{
    public TooManyRequestsException(String message){
        super(message);
    }
}
