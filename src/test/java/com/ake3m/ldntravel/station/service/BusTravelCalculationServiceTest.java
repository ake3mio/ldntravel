package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.station.model.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BusTravelCalculationServiceTest {

    @Autowired
    private BusTravelCalculationService travelCalculationService;

    @Test
    void getDepositIsTheSameAsFare() {
        assertEquals(
                travelCalculationService.getDepositFee(),
                travelCalculationService.calculateTrip(new Station(), new Station())
        );
    }
}