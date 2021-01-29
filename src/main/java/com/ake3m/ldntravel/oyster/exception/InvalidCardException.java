package com.ake3m.ldntravel.oyster.exception;

public class InvalidCardException extends RuntimeException{
    public InvalidCardException() {
        super("This card is invalid");
    }
}
