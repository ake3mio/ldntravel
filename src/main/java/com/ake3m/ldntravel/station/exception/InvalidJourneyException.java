package com.ake3m.ldntravel.station.exception;

public class InvalidJourneyException extends RuntimeException{
    public InvalidJourneyException() {
        super("This journey is invalid");
    }
}
