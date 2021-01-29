package com.ake3m.ldntravel.station.repository;


import com.ake3m.ldntravel.station.model.Zone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ZoneRepository extends CrudRepository<Zone, Integer> {
    List<Zone> findAllByStationsId(int stationId);

    List<Zone> findAll();
}
