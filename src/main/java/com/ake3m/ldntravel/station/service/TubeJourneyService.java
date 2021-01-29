package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.oyster.service.CardPaymentService;
import com.ake3m.ldntravel.station.model.Journey;
import com.ake3m.ldntravel.station.model.OysterJourney;
import com.ake3m.ldntravel.station.model.Station;
import com.ake3m.ldntravel.station.repository.OysterJourneyRepository;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "tubeJourneyService")
public class TubeJourneyService extends JourneyService<OysterCard> {

    private final CardPaymentService<OysterCard> cardPaymentService;
    private final TravelCalculationService tradeCalculationService;

    public TubeJourneyService(
            CardPaymentService<OysterCard> cardPaymentService,
            @Qualifier("tubeTravelCalculationService") TravelCalculationService tradeCalculationService,
            OysterJourneyRepository oysterJourneyRepository
    ) {
        super(cardPaymentService, tradeCalculationService, oysterJourneyRepository);
        this.cardPaymentService = cardPaymentService;
        this.tradeCalculationService = tradeCalculationService;
    }


    @Override
    public OysterJourney startJourney(OysterCard card, Station from) {
        return startJourney(card, from, TransportMethodType.TUBE);
    }

    @Override
    protected void beforePersist(Journey journey) {
        var card = journey.getCard();
        cardPaymentService.refund(card, tradeCalculationService.getDepositFee());
        var amount = tradeCalculationService.calculateTrip(journey.getFrom(), journey.getTo());
        card = cardPaymentService.charge(card, amount);
        journey.setCard(card);
    }
}
