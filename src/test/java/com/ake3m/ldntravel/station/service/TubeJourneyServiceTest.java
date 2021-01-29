package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.oyster.exception.InsufficientFundsException;
import com.ake3m.ldntravel.oyster.model.OysterCard;
import com.ake3m.ldntravel.oyster.service.CardMachineService;
import com.ake3m.ldntravel.oyster.service.CardPaymentService;
import com.ake3m.ldntravel.station.model.Station;
import com.ake3m.ldntravel.station.model.Zone;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TubeJourneyServiceTest {

    @Autowired
    CardMachineService<OysterCard> cardMachineService;
    @Autowired
    CardPaymentService<OysterCard> cardPaymentService;
    @Autowired
    TubeTravelCalculationService travelCalculationService;
    @Autowired
    TubeJourneyService journeyService;
    @Autowired
    private StationService stationService;

    @Test
    void startJourneyChargesTheMaximumFare() {
        var station = stationService.getStations().get(0);
        var amount = 30.00;
        var card = cardMachineService.createCard();
        card = cardMachineService.topUp(card, amount);
        journeyService.startJourney(card, station);
        card = cardPaymentService.getUpdatedCard(card);

        assertEquals(card.getBalance(), amount - travelCalculationService.getDepositFee());
    }

    @Test
    void startJourneyThrowsAnErrorOnInsufficientFunds() {
        var card = cardMachineService.createCard();
        assertThrows(InsufficientFundsException.class, () -> {
            journeyService.startJourney(card, new Station());
        });
    }

    @Test
    void startJourneyPopulateFromFieldOnJourney() {
        var station1 = stationService.getStations().get(0);
        var station2 = stationService.getStations().get(2);
        var amount = 30.00;
        var card = cardMachineService.createCard();
        card = cardMachineService.topUp(card, amount);
        journeyService.startJourney(card, station1);
        var journey = journeyService.endJourney(card, station2);

        assertEquals(journey.getFrom().getName(), station1.getName());

    }

    @Test
    void endJourneyPopulatesToFieldOnJourney() {
        var station1 = stationService.getStations().get(0);
        var station2 = stationService.getStations().get(2);
        var amount = 30.00;
        var card = cardMachineService.createCard();
        card = cardMachineService.topUp(card, amount);
        journeyService.startJourney(card, station1);
        var journey = journeyService.endJourney(card, station2);

        assertEquals(journey.getTo().getName(), station2.getName());
    }

    @Test
    void endJourneyRefundsTheDifference() {
        var zone = stationService.getZones().get(1);
        var station1 = zone.getStations().get(0);
        var station2 = stationService.getZones().get(1).getStations().get(1);
        var amount = 30.00;

        var card = cardMachineService.createCard();
        card = cardMachineService.topUp(card, amount);
        journeyService.startJourney(card, station1);

        card = cardPaymentService.getUpdatedCard(card);

        var afterDepositAmount = amount - travelCalculationService.getDepositFee();

        assertEquals(card.getBalance(), afterDepositAmount);

        journeyService.endJourney(card, station2);

        card = cardPaymentService.getUpdatedCard(card);

        assertTrue(card.getBalance() > afterDepositAmount);
        assertEquals(card.getBalance(), amount - zone.getFees().get(TransportMethodType.TUBE).getAmount());
    }
}