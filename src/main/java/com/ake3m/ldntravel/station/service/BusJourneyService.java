package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.oyster.service.CardPaymentService;
import com.ake3m.ldntravel.station.model.OysterJourney;
import com.ake3m.ldntravel.station.model.Station;
import com.ake3m.ldntravel.station.repository.OysterJourneyRepository;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "busJourneyServiceTest")
public class BusJourneyService extends JourneyService<OysterCard> {

    public BusJourneyService(
            CardPaymentService<OysterCard> cardPaymentService,
            @Qualifier("busTravelCalculationService") TravelCalculationService tradeCalculationService,
            OysterJourneyRepository oysterJourneyRepository
    ) {
        super(cardPaymentService, tradeCalculationService, oysterJourneyRepository);
    }

    @Override
    public OysterJourney startJourney(OysterCard card, Station from) {
        return startJourney(card, from, TransportMethodType.BUS);
    }
}
