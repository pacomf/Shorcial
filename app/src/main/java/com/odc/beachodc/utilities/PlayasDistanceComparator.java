package com.odc.beachodc.utilities;

import com.odc.beachodc.db.models.Playa;

import java.util.Comparator;

/**
 * Created by Paco on 09/07/2014.
 */
public class PlayasDistanceComparator implements Comparator<Playa> {
    @Override
    public int compare(Playa p1, Playa p2) {
        Double distanceP1, distanceP2;
        distanceP1 = (double) Geo.getDistanceInMetersToMe(p1.latitud, p1.longitud);
        distanceP2 = (double) Geo.getDistanceInMetersToMe(p2.latitud, p2.longitud);
        return distanceP1.compareTo(distanceP2);
    }
}
