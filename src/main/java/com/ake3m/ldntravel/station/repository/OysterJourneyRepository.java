package com.ake3m.ldntravel.station.repository;


import com.ake3m.ldntravel.station.model.OysterJourney;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OysterJourneyRepository extends CrudRepository<OysterJourney, Integer> {
    List<OysterJourney> findAllByCardIdOrderByIdDesc(int cardId, Pageable pageable);
}
