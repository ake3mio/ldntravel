package com.ake3m.ldntravel.oyster.model;

import lombok.Data;

@Data
public class OysterCard implements Card {
    private int id;
    private double balance;
}
