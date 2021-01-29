package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.station.model.Station;

public interface TravelCalculationService {
    double getMaxFee();

    double calculateTrip(Station from, Station to);

    default double getDepositFee() {
        return getMaxFee();
    }
}
