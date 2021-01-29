package com.ake3m.ldntravel.station.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Insufficient funds are available");
    }
}
