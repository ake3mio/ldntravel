package com.ake3m.ldntravel.station.service;

import com.ake3m.ldntravel.station.model.Station;
import com.ake3m.ldntravel.station.model.Zone;
import com.ake3m.ldntravel.station.repository.StationRepository;
import com.ake3m.ldntravel.station.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository stationRepository;
    private final ZoneRepository zoneRepository;

    @Transactional
    public List<Zone> getZones() {
        return zoneRepository.findAll();
    }
    public List<Station> getStations() {
        return stationRepository.findAll();
    }

    public Optional<Station> findStation(String name) {
        return stationRepository.findByName(name);
    }
}
