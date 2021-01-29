package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.station.model.Station;
import org.springframework.stereotype.Service;

@Service(value="busTravelCalculationService")
public class BusTravelCalculationService implements TravelCalculationService {
    @Override
    public double getMaxFee() {
        return 1.80;
    }

    @Override
    public double getDepositFee() {
        return getMaxFee();
    }

    @Override
    public double calculateTrip(Station from, Station to) {
        return getMaxFee();
    }
}
