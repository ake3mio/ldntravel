package com.ake3m.ldntravel.station.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Station {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;
}
