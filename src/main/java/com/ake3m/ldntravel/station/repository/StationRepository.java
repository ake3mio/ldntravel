package com.ake3m.ldntravel.station.repository;


import com.ake3m.ldntravel.station.model.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface StationRepository extends CrudRepository<Station, Integer> {
    Optional<Station> findByName(String name);
    List<Station> findAll();
}
