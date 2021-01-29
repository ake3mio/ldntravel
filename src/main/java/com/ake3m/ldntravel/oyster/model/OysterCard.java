package com.ake3m.ldntravel.oyster.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class OysterCard implements Card {
    @Id
    @GeneratedValue
    private int id;
    private double balance;
}
