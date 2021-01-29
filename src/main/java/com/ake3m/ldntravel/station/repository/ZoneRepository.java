package com.ake3m.ldntravel.station.repository;


import com.ake3m.ldntravel.station.model.Zone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ZoneRepository extends CrudRepository<Zone, Integer> {
    Optional<Zone> findByStationsId(int stationId);

    List<Zone> findAll();
}
