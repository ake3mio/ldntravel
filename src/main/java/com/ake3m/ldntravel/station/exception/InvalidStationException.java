package com.ake3m.ldntravel.station.exception;

public class InvalidStationException extends RuntimeException{
    public InvalidStationException() {
        super("This station is invalid");
    }
}
