package com.odc.beachodc.db;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mobandme.ada.Entity;
import com.odc.beachodc.Home;
import com.odc.beachodc.R;
import com.odc.beachodc.db.dao.AppDataContext;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.webservices.Request;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Paco on 07/07/2014.
 */
public class BBDD {

    /*public static AppDataContext appDataContext;

    public static void initBBDD (Context ctx){
        getApplicationDataContext(ctx);
    }

    public static AppDataContext getApplicationDataContext(Context ctx) {
        if (BBDD.appDataContext == null) {
            try {
                BBDD.appDataContext = new AppDataContext(ctx);
            } catch (Exception e) {
                Log.e("BBDD", "Error inicializando AppDataContext: " + e.getMessage());
            }
        }
        return BBDD.appDataContext;
    }*/

    public static void guardarPlaya(Activity activity, Playa playa, boolean nuevo){
        // Enviamos al servidor la info y si responde bien, guardamos en local
        if (nuevo) {
            Request.editarPlaya(activity, playa, nuevo);
        } else {
            Request.editarPlaya(activity, playa, false);
        }

    }
}
