package com.odc.beachodc.utilities;

import com.google.android.gms.maps.model.LatLng;
import com.odc.beachodc.db.models.Playa;

import java.util.Comparator;

/**
 * Created by Paco on 09/07/2014.
 */
public class PlayasDistanceComparator implements Comparator<Playa> {

    boolean toMe;
    LatLng origin;

    public PlayasDistanceComparator (){
        super();
        this.toMe = true;
    }

    public PlayasDistanceComparator (boolean toMe, LatLng origin){
        super();
        this.toMe = toMe;
        this.origin = origin;
    }

    @Override
    public int compare(Playa p1, Playa p2) {
        Double distanceP1, distanceP2;
        if (this.toMe) {
            distanceP1 = (double) Geo.getDistanceInMetersToMe(p1.latitud, p1.longitud);
            distanceP2 = (double) Geo.getDistanceInMetersToMe(p2.latitud, p2.longitud);
        } else {
            distanceP1 = (double) Geo.getDistanceInMetersTo(p1.latitud, p1.longitud, origin.latitude, origin.longitude);
            distanceP2 = (double) Geo.getDistanceInMetersTo(p2.latitud, p2.longitud, origin.latitude, origin.longitude);
        }
        return distanceP1.compareTo(distanceP2);
    }
}
