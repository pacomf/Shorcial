package com.odc.beachodc.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.db.models.Playa;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Paco on 08/07/2014.
 */
public class ValidacionPlaya {

    public static Playa playa;
    public static Double temperatura;
    public static ArrayList<Playa> playas;
    public static ArrayList<Playa> playasCheckins;
    public static ArrayList<MensajeBotella> mensajesBotella;
    public static ArrayList<Comentario> comentariosPlaya;

    public static boolean cargadaPlayas;
    public static boolean cargadosUltimosCheckins;

    public static boolean cargadosMensajesPlaya;
    public static boolean cargadosComentarios;
    public static boolean cargadaTemperatura;

    public static boolean lanzadaVerPlaya;

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

    public static boolean comprobarCargaPlaya (Context ctx, Intent intent){
        if ((ValidacionPlaya.cargadosMensajesPlaya) && (ValidacionPlaya.cargadosComentarios) && (ValidacionPlaya.cargadaTemperatura)){
            if (!ValidacionPlaya.lanzadaVerPlaya){
                ValidacionPlaya.lanzadaVerPlaya = true;
                ctx.startActivity(intent);
            }
            return true;
        }
        return false;
    }

}
