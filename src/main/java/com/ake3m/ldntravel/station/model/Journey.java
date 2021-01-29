package com.ake3m.ldntravel.station.model;

import com.ake3m.ldntravel.oyster.model.Card;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.Data;

@Data
public class Journey<T extends Card> {
    private T card;
    private Station from;
    private Station to;
    private TransportMethodType method;
}
