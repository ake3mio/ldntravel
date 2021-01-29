package com.ake3m.ldntravel.station.model;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class OysterJourney implements Journey {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private OysterCard card;
    @ManyToOne
    private Station from;
    @ManyToOne
    private Station to;
    private TransportMethodType method;
}
