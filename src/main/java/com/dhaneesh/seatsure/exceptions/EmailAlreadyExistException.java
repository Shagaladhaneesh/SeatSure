package com.dhaneesh.seatsure.exceptions;

public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(String string)
    {
        super(string);
    }
}
