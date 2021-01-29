package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.model.Card;
import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.oyster.service.CardPaymentService;
import com.ake3m.ldntravel.station.exception.InvalidJourneyException;
import com.ake3m.ldntravel.station.model.Journey;
import com.ake3m.ldntravel.station.model.OysterJourney;
import com.ake3m.ldntravel.station.model.Station;
import com.ake3m.ldntravel.station.repository.OysterJourneyRepository;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@RequiredArgsConstructor
public abstract class JourneyService<T extends Card> {
    private final CardPaymentService<OysterCard> cardPaymentService;
    private final TravelCalculationService tradeCalculationService;
    private final OysterJourneyRepository oysterJourneyRepository;

    protected abstract Journey startJourney(T card, Station from);

    public OysterJourney endJourney(OysterCard card, Station to) {
        return oysterJourneyRepository
                .findByCardId(card.getId())
                .map(journey -> {
                    journey.setTo(to);
                    beforePersist(journey);
                    return oysterJourneyRepository.save(journey);
                })
                .orElseThrow(InvalidJourneyException::new);
    }

    protected OysterJourney startJourney(OysterCard card, Station from, TransportMethodType methodType) {
        card = cardPaymentService.charge(card, tradeCalculationService.getDepositFee());
        var journey = new OysterJourney();
        journey.setMethod(methodType);
        journey.setCard(card);
        journey.setFrom(from);
        return oysterJourneyRepository.save(journey);
    }

    protected void beforePersist(Journey journey) { }
}
