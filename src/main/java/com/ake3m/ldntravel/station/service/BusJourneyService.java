package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.model.Card;
import com.ake3m.ldntravel.station.model.Journey;
import com.ake3m.ldntravel.station.model.Station;
import org.springframework.stereotype.Service;

@Service
public class BusJourneyService implements JourneyService {
    @Override
    public <T extends Card> Journey<T> startJourney(T card, Station from) {
        return null;
    }

    @Override
    public <T extends Card> Journey<T> endJourney(T card, Station to) {
        return null;
    }
}
