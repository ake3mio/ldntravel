package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.oyster.service.CardMachineService;
import com.ake3m.ldntravel.oyster.service.CardPaymentService;
import com.ake3m.ldntravel.station.exception.InsufficientFundsException;
import com.ake3m.ldntravel.station.model.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BusJourneyServiceTest {

    @Autowired
    CardMachineService<OysterCard> cardMachineService;
    @Autowired
    CardPaymentService<OysterCard> cardPaymentService;
    @Autowired
    BusTravelCalculationService travelCalculationService;
    @Autowired
    BusJourneyService busJourneyService;
    @Autowired
    private StationService stationService;

    @Test
    void startJourneyChargesTheMaximumFare() {
        var station = stationService.getStations().get(0);
        var amount = 30.00;
        var card = cardMachineService.createCard();
        card = cardMachineService.topUp(card, amount);
        busJourneyService.startJourney(card, station);
        card = cardPaymentService.getUpdatedCard(card);

        assertEquals(card.getBalance(), amount - travelCalculationService.getMaxFee());
    }

    @Test
    void startJourneyThrowsAnErrorOnInsufficientFunds() {
        var card = cardMachineService.createCard();
        assertThrows(InsufficientFundsException.class, () -> {
            busJourneyService.startJourney(card, new Station());
        });
    }

    @Test
    void startJourneyPopulateFromFieldOnJourney() {
        var station1 = stationService.getStations().get(0);
        var station2 = stationService.getStations().get(2);
        var amount = 30.00;
        var card = cardMachineService.createCard();
        card = cardMachineService.topUp(card, amount);
        busJourneyService.startJourney(card, station1);
        var journey = busJourneyService.endJourney(card, station2);

        assertEquals(journey.getFrom().getName(), station1.getName());

    }

    @Test
    void endJourneyPopulatesToFieldOnJourney() {
        var station1 = stationService.getStations().get(0);
        var station2 = stationService.getStations().get(2);
        var amount = 30.00;
        var card = cardMachineService.createCard();
        card = cardMachineService.topUp(card, amount);
        busJourneyService.startJourney(card, station1);
        var journey = busJourneyService.endJourney(card, station2);

        assertEquals(journey.getTo().getName(), station2.getName());
    }
}