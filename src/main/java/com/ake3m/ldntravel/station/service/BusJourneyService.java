package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.oyster.service.CardPaymentService;
import com.ake3m.ldntravel.station.exception.InvalidJourneyException;
import com.ake3m.ldntravel.station.model.OysterJourney;
import com.ake3m.ldntravel.station.model.Station;
import com.ake3m.ldntravel.station.repository.OysterJourneyRepository;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service(value = "busJourneyServiceTest")
public class BusJourneyService implements JourneyService<OysterCard> {
    private final CardPaymentService<OysterCard> cardPaymentService;

    @Autowired
    @Qualifier("busTravelCalculationService")
    private TravelCalculationService tradeCalculationService;
    private final OysterJourneyRepository oysterJourneyRepository;

    @Override
    public OysterJourney startJourney(OysterCard card, Station from) {
        card = cardPaymentService.charge(card, tradeCalculationService.getDepositFee());
        var journey = new OysterJourney();
        journey.setMethod(TransportMethodType.BUS);
        journey.setCard(card);
        journey.setFrom(from);
        return oysterJourneyRepository.save(journey);
    }

    @Override
    public OysterJourney endJourney(OysterCard card, Station to) {
        return oysterJourneyRepository
                .findByCardId(card.getId())
                .stream()
                .peek(journey -> journey.setTo(to))
                .findFirst()
                .orElseThrow(InvalidJourneyException::new);
    }
}
