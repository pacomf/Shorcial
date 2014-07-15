package com.odc.beachodc.webservices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.odc.beachodc.Home;
import com.odc.beachodc.Playas;
import com.odc.beachodc.R;
import com.odc.beachodc.db.BBDD;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.ValidacionPlaya;

import org.json.JSONArray;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Paco on 15/07/2014.
 */
public class Response {

    public static void responseGetPlaya(Context ctx, JSONArray response){
        Playa playa = null;
        boolean BBDDborrada = false;
        for (int i=0; i<response.length(); i++){
            try {
                playa = JSONToModel.toPlaya(response.getJSONObject(i));
                if (!BBDDborrada){
                    BBDD.getApplicationDataContext(ctx).playasDao.removeAll();
                    BBDD.getApplicationDataContext(ctx).playasDao.save();
                    BBDDborrada = true;
                }
                playa.setStatus(Entity.STATUS_NEW);
                BBDD.getApplicationDataContext(ctx).playasDao.add(playa);
                BBDD.getApplicationDataContext(ctx).playasDao.save();
            } catch (Exception e){
                System.out.println("FALLO RESPONSEGETPLAYA: "+e.getMessage());
                playa = null;
            }
        }
    }

    public static void responseNuevaPlaya(Activity activity, JSONObject response){
        try {
            Playa playa = JSONToModel.toPlaya(response);

            playa.setStatus(Entity.STATUS_NEW);
            BBDD.getApplicationDataContext(activity).playasDao.add(playa);
            BBDD.getApplicationDataContext(activity).playasDao.save();

            Intent intent = new Intent(activity, Home.class);
            intent.putExtra("creaplaya", true);

            // Para eliminar el historial de activities visitadas ya que volvemos al HOME y asi el boton ATRAS no tenga ningun comportamiento, se resetee.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e) {
            Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            System.out.println("FALLO RESPONSENUEVAPLAYA: "+e.getMessage());
            return;
        }
    }

    public static void responseEditarPlaya(Activity activity, JSONObject response){
        try {
            Playa playa = JSONToModel.toPlaya(response);

            try {
                Playa aActualizar = BBDD.getApplicationDataContext(activity).playasDao.search(false, "idserver = ?", new String[]{playa.idserver}, null, null, null, null, null).get(0);
                aActualizar.setStatus(Entity.STATUS_DELETED);
                BBDD.getApplicationDataContext(activity).playasDao.remove(aActualizar);
                BBDD.getApplicationDataContext(activity).playasDao.save(aActualizar);
            } catch (Exception e){
                return;
            }

            playa.setStatus(Entity.STATUS_NEW);
            BBDD.getApplicationDataContext(activity).playasDao.add(playa);
            BBDD.getApplicationDataContext(activity).playasDao.save();

            Intent intent = new Intent(activity, Home.class);
            intent.putExtra("editaplaya", true);

            // Para eliminar el historial de activities visitadas ya que volvemos al HOME y asi el boton ATRAS no tenga ningun comportamiento, se resetee.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e) {
            Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            System.out.println("FALLO RESPONSENUEVAPLAYA: "+e.getMessage());
            return;
        }
    }

    public static void responseValorarPlaya(Activity activity, JSONObject response){
        try {
            Playa playa = JSONToModel.toPlaya(response);

            try {
                Playa aActualizar = BBDD.getApplicationDataContext(activity).playasDao.search(false, "idserver = ?", new String[]{playa.idserver}, null, null, null, null, null).get(0);
                aActualizar.setStatus(Entity.STATUS_DELETED);
                BBDD.getApplicationDataContext(activity).playasDao.remove(aActualizar);
                BBDD.getApplicationDataContext(activity).playasDao.save(aActualizar);
            } catch (Exception e){
                return;
            }

            playa.setStatus(Entity.STATUS_NEW);
            BBDD.getApplicationDataContext(activity).playasDao.add(playa);
            BBDD.getApplicationDataContext(activity).playasDao.save();

            Intent intent = new Intent(activity, Playas.class);
            ValidacionPlaya.playa = playa;
            //TODO: Enviar algun boolean en la activity para que aparezca un mensajito como que la valoracion se ha realizado correctamente

            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e) {
            Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            System.out.println("FALLO RESPONSENUEVAPLAYA: "+e.getMessage());
            return;
        }
    }
}
