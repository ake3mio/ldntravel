package com.ake3m.ldntravel.station.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Fee {
    @Id
    @GeneratedValue
    private int id;
    private double amount;
}
