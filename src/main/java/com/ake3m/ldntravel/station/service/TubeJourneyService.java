package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.station.model.Journey;
import com.ake3m.ldntravel.station.model.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service(value = "tubeJourneyService")
public class TubeJourneyService implements JourneyService<OysterCard> {
    @Override
    public Journey startJourney(OysterCard card, Station from) {
        return null;
    }

    @Override
    public Journey endJourney(OysterCard card, Station to) {
        return null;
    }
}
