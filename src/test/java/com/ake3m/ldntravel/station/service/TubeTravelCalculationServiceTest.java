package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.station.types.TransportMethodType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class TubeTravelCalculationServiceTest {

    @Autowired
    private TubeTravelCalculationService travelCalculationService;

    @Autowired
    private StationService stationService;

    @Test
    void maxFeeIsSameAsDeposit() {
        assertEquals(
                travelCalculationService.getMaxFee(),
                travelCalculationService.getDepositFee()
        );
    }

    @Test
    void theFeeOfStationsInTheSameZone() {
        var zones = stationService.getZones();

        var zone = zones
                .stream()
                .filter(z -> z.getStations().size() > 1)
                .findFirst()
                .orElseThrow();

        var expectedFee = zone.getFees().get(TransportMethodType.TUBE).getAmount();

        var to = zone.getStations().get(1);
        var from = zone.getStations().get(0);
        var fee = travelCalculationService.calculateTrip(from, to);
        assertNotEquals(fee, 0);
        assertEquals(fee, expectedFee);
    }

    @Test
    void theFeeOfStationsFromAdjacentZones() {
        var zones = stationService.getZones();
        var zone1 = zones.get(0);
        var zone2 = zone1.getAdjZone();
        var expectedFee = zone1.getFees().get(TransportMethodType.TUBE).getAmount() + zone1.getWeight();
        var to = zone1.getStations().get(1);
        var from = zone2.getStations().get(0);
        var fee = travelCalculationService.calculateTrip(from, to);
        assertEquals(fee, expectedFee);
    }

    @Test
    void theFeeOfStationsFromAllZones() {
        var zones = stationService.getZones();
        var zone1 = zones.get(0);
        var zone2 = zone1.getAdjZone();
        var zone3 = zone2.getAdjZone();

        var expectedFee = travelCalculationService.getMaxFee();
        var to = zone1.getStations().get(1);
        var from = zone3.getStations().get(0);
        var fee = travelCalculationService.calculateTrip(from, to);
        assertEquals(fee, expectedFee);
    }
}