package com.odc.beachodc.webservices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.android.volley.*;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.android.Util;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Checkin;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import static com.odc.beachodc.utilities.Utilities.*;

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
        // TODO: En un futuro que los usuarios puedan añadir CAMs
        params.put("webcamURL", "");

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

    public static void valorarPlaya (final Activity activity, final Comentario comentario) {
        final String URL = Config.getURLServer(activity)+"/valoracionplaya/"+comentario.idplaya+"/"+comentario.idfbautor;

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("valoracion", comentario.valoracion.toString());
        params.put("nombreautor", Utilities.getUserNameFacebook(activity));
        params.put("fecha", Utilities.formatFecha(comentario.fecha));
        params.put("comentario", comentario.comentario);

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      com.odc.beachodc.webservices.Response.responseValorarPlaya(activity, response, comentario);
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

    public static void mensajeBotellaPlaya (final Activity activity, final MensajeBotella mensaje) {
        final String URL = Config.getURLServer(activity)+"/mensajebotellaplaya/"+mensaje.idserverplayaorigen+"/"+mensaje.idfbautor;

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fecha", formatFecha(mensaje.fecha));
        params.put("mensaje", mensaje.mensaje);
        params.put("nombreAutor", mensaje.nombreautor);

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        com.odc.beachodc.webservices.Response.responseMensajeBotellaPlaya(activity, response, mensaje);
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

    public static void getPlayasCercanas (final Context ctx, final ProgressDialog pd){
        if (Geo.myLocation != null) {
            final String URL = Config.getURLServer(ctx) + "/playascercanas/" + Geo.myLocation.getLatitude() + "/" + Geo.myLocation.getLongitude();
            JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    com.odc.beachodc.webservices.Response.responseGetPlayasCercanas(ctx, response, pd);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    ValidacionPlaya.cargadaPlayas=true;
                    if ((ValidacionPlaya.cargadosUltimosCheckins) && (pd.isShowing())) {
                        pd.dismiss();
                    }
                }
            });

            // add the request object to the queue to be executed
            Config.addToRequestQueue(ctx, req);
        } else {
            ValidacionPlaya.cargadaPlayas=true;
            if ((ValidacionPlaya.cargadosUltimosCheckins) && (pd.isShowing())) {
                pd.dismiss();
            }
        }
    }

    public static void getUltimosCheckins (final Context ctx, final ProgressDialog pd){
        final String URL = Config.getURLServer(ctx)+"/ultimoscheckins/"+ getUserIdFacebook(ctx);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                com.odc.beachodc.webservices.Response.responseGetUltimosCheckins(ctx, response, pd);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                ValidacionPlaya.cargadosUltimosCheckins=true;
                if ((ValidacionPlaya.cargadaPlayas) && (pd.isShowing())) {
                    pd.dismiss();
                }
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(ctx, req);
    }

    public static void nuevoCheckinPlaya (final Activity activity, Checkin checkin, final ProgressDialog pd) {
        final String URL = Config.getURLServer(activity)+"/checkin/"+checkin.idfbuser+"/"+checkin.idplayaserver;

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fecha", formatFecha(checkin.fecha));

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        com.odc.beachodc.webservices.Response.responseCheckinPlaya(activity, response, pd);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                        pd.dismiss();
                        Crouton.makeText(activity, R.string.unknown, Style.ALERT).show();
                    }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(activity, req);
    }

    public static void getPlayasCercanasTo (final Context ctx, final String direccion, final Double latitud, final Double longitud, final ProgressDialog pd){
        final String URL = Config.getURLServer(ctx) + "/playascercanas/" + latitud + "/" + longitud;
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                com.odc.beachodc.webservices.Response.responseGetPlayasCercanasTo(ctx, response, pd, direccion, latitud, longitud);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                pd.dismiss();
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(ctx, req);
    }

    public static void getPlayasByName (final Context ctx, final String name, final ProgressDialog pd){
        final String URL = Config.getURLServer(ctx) + "/playasbyname/" + name.replaceAll(" ", "%20");
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                com.odc.beachodc.webservices.Response.responseGetPlayasByName(ctx, response, pd, name);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                pd.dismiss();
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(ctx, req);
    }

    public static void getMensajesBotella (final Context ctx, String idPlaya, final ProgressDialog pd, final Intent intent){
        final String URL = Config.getURLServer(ctx)+"/mensajesplaya/"+idPlaya;
        ValidacionPlaya.mensajesBotella = new ArrayList<MensajeBotella>();
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                com.odc.beachodc.webservices.Response.responseGetMensajesBotella(ctx, response, pd, intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                ValidacionPlaya.cargadosMensajesPlaya=true;
                if (ValidacionPlaya.comprobarCargaPlaya(ctx, intent)){
                    pd.dismiss();
                }
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(ctx, req);
    }

    public static void getComentariosPlaya (final Context ctx, String idPlaya, final ProgressDialog pd, final Intent intent){
        final String URL = Config.getURLServer(ctx)+"/comentariosplaya/"+idPlaya;
        ValidacionPlaya.comentariosPlaya = new ArrayList<Comentario>();
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                com.odc.beachodc.webservices.Response.responseGetComentariosPlaya(ctx, response, pd, intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                ValidacionPlaya.cargadosComentarios=true;
                if (ValidacionPlaya.comprobarCargaPlaya(ctx, intent)){
                    pd.dismiss();
                }
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(ctx, req);
    }

    //Uilizamos openweathermap para tomar temperaturas geolocalizadas
    public static void getTemp(final Context ctx, double lat, double lon, final ProgressDialog pd, final Intent intent) {
        final String URL = "http://api.openweathermap.org/data/2.5/find?lat=" + lat + "&lon=" + lon + "&cnt=1";
        JsonObjectRequest req = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        com.odc.beachodc.webservices.Response.responseGetTemp(ctx, response, pd, intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                ValidacionPlaya.cargadaTemperatura=true;
                if (ValidacionPlaya.comprobarCargaPlaya(ctx, intent)){
                    pd.dismiss();
                }
            }
        }
        );

        // add the request object to the queue to be executed
        Config.addToRequestQueue(ctx, req);
    }


}
