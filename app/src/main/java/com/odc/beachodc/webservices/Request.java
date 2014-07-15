package com.odc.beachodc.webservices;

import android.app.Activity;
import android.content.Context;

import com.android.volley.*;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by Paco on 15/07/2014.
 */
public class Request {

    public static void getPlayas (final Context ctx){
        final String URL = Config.getURLServer(ctx)+"/playas";
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                com.odc.beachodc.webservices.Response.responseGetPlaya(ctx, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(ctx, req);
    }

    public static void editarPlaya (final Activity activity, Playa playa, final boolean isNew) {
        final String URL;
        if (isNew)
            URL = Config.getURLServer(activity)+"/nuevaplaya";
        else
            URL = Config.getURLServer(activity)+"/editarplaya/"+playa.idserver;

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("nombre", playa.nombre);
        params.put("lon", playa.longitud.toString());
        params.put("lat", playa.latitud.toString());
        params.put("banderaazul", playa.banderaazul.toString());
        params.put("acceso", playa.dificultadacceso);
        params.put("arena", playa.tipoarena);
        params.put("limpieza", playa.limpieza);
        params.put("rompeolas", playa.rompeolas.toString());
        params.put("hamacas", playa.hamacas.toString());
        params.put("sombrillas", playa.sombrillas.toString());
        params.put("chiringuitos", playa.chiringuitos.toString());
        params.put("duchas", playa.duchas.toString());
        params.put("socorrista", playa.socorrista.toString());

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (isNew)
                            com.odc.beachodc.webservices.Response.responseNuevaPlaya(activity, response);
                        else
                            com.odc.beachodc.webservices.Response.responseEditarPlaya(activity, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(activity, req);
    }

    public static void valorarPlaya (final Activity activity, Comentario comentario) {
        final String URL = Config.getURLServer(activity)+"/valoracionplaya/"+comentario.idplaya+"/"+comentario.idfbautor;

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("valoracion", comentario.valoracion.toString());
        params.put("fecha", Utilities.formatFecha(comentario.fecha));
        params.put("comentario", comentario.comentario);

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      com.odc.beachodc.webservices.Response.responseValorarPlaya(activity, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(activity, req);
    }
}
