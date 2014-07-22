package com.odc.beachodc.utilities;

import com.google.android.gms.maps.model.LatLng;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.Playa;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Paco on 09/07/2014.
 */
public class ComentariosFechaComparator implements Comparator<Comentario> {

    public ComentariosFechaComparator(){
        super();
    }

    @Override
    public int compare(Comentario c1, Comentario c2) {
        return c2.fecha.compareTo(c1.fecha);
    }
}
