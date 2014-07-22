package com.odc.beachodc.utilities;

import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;

import java.util.Comparator;

/**
 * Created by Paco on 09/07/2014.
 */
public class MensajesBotellaFechaComparator implements Comparator<MensajeBotella> {

    public MensajesBotellaFechaComparator(){
        super();
    }

    @Override
    public int compare(MensajeBotella mb1, MensajeBotella mb2) {
        return mb2.fecha.compareTo(mb1.fecha);
    }
}
