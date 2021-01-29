package com.ake3m.ldntravel.station.model;

import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class Zone {
    private int level;
    private Set<Station> stations;
    private Map<TransportMethodType, Fee> fees;
    private Zone adjZone;
}
