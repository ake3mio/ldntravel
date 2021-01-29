package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.model.Card;
import com.ake3m.ldntravel.station.model.Station;

public interface JourneyService {
   <T extends Card> void startJourney(T card, Station from);
   <T extends Card> void endJourney(T card, Station to);
}
