package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.station.exception.InvalidJourneyException;
import com.ake3m.ldntravel.station.model.Station;
import com.ake3m.ldntravel.station.model.Zone;
import com.ake3m.ldntravel.station.repository.ZoneRepository;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "tubeTravelCalculationService")
@RequiredArgsConstructor
public class TubeTravelCalculationService implements TravelCalculationService {

    private final ZoneRepository zoneRepository;

    @Override
    public double getMaxFee() {
        return 3.20;
    }

    @Override
    public double calculateTrip(Station from, Station to) {

        List<Zone> zonesFrom = zoneRepository.findAllByStationsId(from.getId());
        List<Zone> zonesTo = zoneRepository.findAllByStationsId(to.getId());

        if (zonesFrom.size() == 0 || zonesTo.size() == 0) {
            throw new InvalidJourneyException();
        }

        if (from.equals(to)) {
            return zonesFrom
                    .stream()
                    .map(this::getZoneFee)
                    .reduce(Math::min)
                    .orElseThrow(InvalidJourneyException::new);
        }

        if (zonesTo.size() > zonesFrom.size()) {
            var temp = zonesFrom;
            zonesFrom = zonesTo;
            zonesTo = temp;
        }

        return Math.min(calculateLowestTripFee(zonesFrom, zonesTo), getMaxFee());
    }

    public double calculateLowestTripFee(List<Zone> zones1, List<Zone> zones2) {
        return zones1
                .stream()
                .flatMap(zoneFrom -> zones2.stream().map(zoneTo -> this.calculateTrip(zoneFrom, zoneTo)))
                .reduce(Math::min)
                .orElseThrow(InvalidJourneyException::new);
    }


    public double calculateTrip(Zone zone1, Zone zone2) {
        if (zone1.equals(zone2)) {
            return getZoneFee(zone1);
        }

        double weight = 0;
        double price = Math.max(getZoneFee(zone1), getZoneFee(zone2));
        var target = zone1.getLevel() > zone2.getLevel() ? zone1 : zone2;
        var currentZone = zone1.getLevel() < zone2.getLevel() ? zone1 : zone2;
        while (!currentZone.equals(target)) {
            weight += currentZone.getWeight();
            currentZone = currentZone.getAdjZone();
        }
        return price + weight;
    }

    private double getZoneFee(Zone zone) {
        return zone.getFees().get(TransportMethodType.TUBE).getAmount();
    }
}
