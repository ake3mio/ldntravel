package com.ake3m.ldntravel.station.repository;


import com.ake3m.ldntravel.station.model.OysterJourney;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface OysterJourneyRepository extends CrudRepository<OysterJourney, Integer> {
    Optional<OysterJourney> findByCardId(int cardId);
}
