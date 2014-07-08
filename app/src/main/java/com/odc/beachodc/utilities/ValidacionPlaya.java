package com.odc.beachodc.utilities;

import android.app.Activity;

import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Playa;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Paco on 08/07/2014.
 */
public class ValidacionPlaya {

    public static Playa playa;

    public static boolean validarInfoPlaya(Activity activity){
        if ((ValidacionPlaya.playa.longitud == null) || (ValidacionPlaya.playa.latitud == null)){
            Crouton.makeText(activity, R.string.error_localizacion, Style.ALERT).show();
            return false;
        } else if ((ValidacionPlaya.playa.nombre == null) || (ValidacionPlaya.playa.nombre.equals(""))){
            Crouton.makeText(activity, R.string.error_nombre, Style.ALERT).show();
            return false;
        }

        return true;
    }

}
