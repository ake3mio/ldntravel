package com.ake3m.ldntravel.station.model;

import com.ake3m.ldntravel.station.types.TransportMethodType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Zone {
    @Id
    @GeneratedValue
    private int id;
    private int level;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Station> stations = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TRANSPORT_METHOD_FEE",
            joinColumns = {@JoinColumn(name = "ZONE_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "TRANSPORT_METHOD_FEE_ID", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "fees_id")
    private Map<TransportMethodType, Fee> fees = new EnumMap<>(TransportMethodType.class);

    @OneToOne(cascade = CascadeType.ALL)
    private Zone adjZone;
    private double weight;

    public void setAdjZone(Zone adjZone, double weight) {
        if (adjZone != this) {
            this.adjZone = adjZone;
            this.weight = weight;
        }
    }

    public boolean equals(Zone other) {
        return other.getId() == id;
    }
}
