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

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Paco on 07/07/2014.
 */
public class BBDD {

    public static AppDataContext appDataContext;

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
    }

    public static void guardarPlaya(Activity activity, Playa playa){
        // Enviamos al servidor la info y si responde bien, guardamos en local
        // TODO: Borrar estas lineas cuando se haga bien como se comenta en el TODO de abajo
        try {
            // Descomentar las lineas para que se guarde en Local
            /*playa.setStatus(Entity.STATUS_NEW);
            BBDD.getApplicationDataContext(activity).playasDao.add(playa);
            BBDD.getApplicationDataContext(activity).playasDao.save();*/
            Intent intent = new Intent(activity, Home.class);
            intent.putExtra("creaplaya", true);
            // Para eliminar el historial de activities visitadas ya que volvemos al HOME y asi el boton ATRAS no tenga ningun comportamiento, se resetee.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e){
            Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            Log.e("BBDD", "Error almacenando una playa en local: "+e.getMessage());
        }
        // TODO: Enviar al servidor y cuando responda POSITIVAMENTE guardar en Local (como esta hecho justo arriba)
    }
}
