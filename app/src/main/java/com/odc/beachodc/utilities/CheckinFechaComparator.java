package com.odc.beachodc.utilities;

import com.odc.beachodc.db.models.Checkin;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.Playa;

import java.util.Comparator;

/**
 * Created by Paco on 09/07/2014.
 */
public class CheckinFechaComparator implements Comparator<Playa> {

    public CheckinFechaComparator(){
        super();
    }

    @Override
    public int compare(Playa p1, Playa p2) {
        return p2.checkin.compareTo(p1.checkin);
    }
}
