package com.ake3m.ldntravel.oyster.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Insufficient funds are available");
    }
}
