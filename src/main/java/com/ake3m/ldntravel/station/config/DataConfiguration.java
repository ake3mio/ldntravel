package com.ake3m.ldntravel.station.config;

import com.ake3m.ldntravel.station.model.Fee;
import com.ake3m.ldntravel.station.model.Station;
import com.ake3m.ldntravel.station.model.Zone;
import com.ake3m.ldntravel.station.repository.ZoneRepository;
import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DataConfiguration {
    private final ZoneRepository zoneRepository;

    @PostConstruct
    @Transactional
    void initialise() {
        var z1 = new Zone();
        var z2 = new Zone();
        var z3 = new Zone();

        z1.setLevel(1);
        z2.setLevel(2);
        z3.setLevel(3);

        var busFee = new Fee();
        busFee.setAmount(1.80);

        var z1Fee = new Fee();
        z1Fee.setAmount(2.50);

        var z2Fee = new Fee();
        z2Fee.setAmount(2.00);

        z1.setFees(Map.of(
                TransportMethodType.BUS, busFee,
                TransportMethodType.TUBE, z1Fee
        ));

        List.of(z2, z3).forEach(zone -> {
            zone.setFees(Map.of(
                    TransportMethodType.BUS, busFee,
                    TransportMethodType.TUBE, z2Fee
            ));
        });

        z1.setAdjZone(z2, 0.50);
        z2.setAdjZone(z3, 0.25);

        var holborn = new Station();
        holborn.setName("Holborn");

        z1.getStations().add(holborn);

        var earlsCourt = new Station();
        earlsCourt.setName("Earl’s Court");
        z1.getStations().add(earlsCourt);
        z2.getStations().add(earlsCourt);

        var hammersmith = new Station();
        hammersmith.setName("Hammersmith");
        z2.getStations().add(hammersmith);

        var wimbledon = new Station();
        wimbledon.setName("Wimbledon");
        z3.getStations().add(wimbledon);

        List.of(z1, z2, z3).forEach(zoneRepository::save);
    }
}
