package com.ake3m.ldntravel.station.model;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public interface Journey {
    int getId();

    void setId(int id);

    OysterCard getCard();

    void setCard(OysterCard card);

    Station getFrom();

    void setFrom(Station from);

    Station getTo();

    void setTo(Station to);

    TransportMethodType getMethod();

    void setMethod(TransportMethodType method);
}
