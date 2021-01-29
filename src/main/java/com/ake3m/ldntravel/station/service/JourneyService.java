package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.model.Card;
import com.ake3m.ldntravel.station.model.Journey;
import com.ake3m.ldntravel.station.model.Station;

public interface JourneyService<T extends Card> {
    Journey startJourney(T card, Station from);

    Journey endJourney(T card, Station to);
}
