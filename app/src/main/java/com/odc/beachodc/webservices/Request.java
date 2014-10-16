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
import com.google.android.gms.maps.model.LatLng;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Checkin;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.Imagen;
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
import java.util.Date;
import java.util.HashMap;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import static com.odc.beachodc.utilities.Utilities.*;

/**
 * Created by Paco on 15/07/2014.
 */
public class Request {

    /*public static void getPlayas (final Context ctx){
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
    }*/

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
        params.put("perros", playa.perros.toString());
        params.put("nudista", playa.nudista.toString());
        params.put("cerrada", playa.cerrada.toString());
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

    public static void peticionBorrarPlaya (final Activity activity, final Playa playa, final ProgressDialog pd) {
        final String URL = Config.getURLServer(activity)+"/borrarplaya/"+playa.idserver;

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        com.odc.beachodc.webservices.Response.responsePeticionBorradoPlaya(activity, response, pd);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                pd.dismiss();
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(activity, req);
    }

    public static void valorarPlaya (final Activity activity, final Comentario comentario, final ProgressDialog pd) {
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
                      com.odc.beachodc.webservices.Response.responseValorarPlaya(activity, response, comentario, pd);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(activity, req);
    }

    public static void mensajeBotellaPlaya (final Activity activity, final MensajeBotella mensaje, final ProgressDialog pd) {
        final String URL = Config.getURLServer(activity)+"/mensajebotellaplaya/"+mensaje.idserverplayaorigen+"/"+mensaje.idfbautor;

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fecha", formatFecha(mensaje.fecha));
        params.put("mensaje", mensaje.mensaje);
        params.put("nombreAutor", mensaje.nombreautor);
        params.put("nombrePlaya", mensaje.nombreplayadestino);

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        com.odc.beachodc.webservices.Response.responseMensajeBotellaPlaya(activity, response, mensaje, pd);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
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
                    if ((ValidacionPlaya.cargadosUltimosCheckins) && (pd != null) && (pd.isShowing())) {
                        pd.dismiss();
                    }
                }
            });

            // add the request object to the queue to be executed
            Config.addToRequestQueue(ctx, req);
        } else {
            ValidacionPlaya.cargadaPlayas=true;
            if ((ValidacionPlaya.cargadosUltimosCheckins) && (pd != null) && (pd.isShowing())) {
                pd.dismiss();
            }
        }
    }

    public static void getUltimosCheckins (final Context ctx, final ProgressDialog pd){
        if (Utilities.isAnonymous(ctx)){
            ValidacionPlaya.cargadosUltimosCheckins=true;
            if ((ValidacionPlaya.cargadaPlayas) && (pd != null) && (pd.isShowing())) {
                pd.dismiss();
            }
            return;
        }
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
                if ((ValidacionPlaya.cargadaPlayas) && (pd != null) && (pd.isShowing())) {
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
                        Crouton.makeText(activity, R.string.error_unknown, Style.ALERT).show();
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

    public static void getPlayasByExtras (final Context ctx, final String name, final LatLng direccion, final Playa playa, final ProgressDialog pd){
        HashMap<String, String> params = new HashMap<String, String>();
        if (name != null)
            params.put("nombre", name.replaceAll(" ", "%20"));
        else
            params.put("nombre", null);

        if (direccion != null) {
            params.put("lon", String.valueOf(direccion.longitude));
            params.put("lat", String.valueOf(direccion.latitude));
        } else {
            if (name == null) {
                if (Geo.myLocation != null) {
                    params.put("lon", String.valueOf(Geo.myLocation.getLongitude()));
                    params.put("lat", String.valueOf(Geo.myLocation.getLatitude()));
                } else {
                    params.put("lon", null);
                    params.put("lat", null);
                }
            } else {
                params.put("lon", null);
                params.put("lat", null);
            }
        }
        if (playa.banderaazul != null)
            params.put("banderaazul", playa.banderaazul.toString());
        else
            params.put("banderaazul", null);

        params.put("acceso", playa.dificultadacceso);
        params.put("arena", playa.tipoarena);
        params.put("limpieza", playa.limpieza);

        if (playa.rompeolas != null)
            params.put("rompeolas", playa.rompeolas.toString());
        else
            params.put("rompeolas", null);

        if (playa.hamacas != null)
            params.put("hamacas", playa.hamacas.toString());
        else
            params.put("hamacas", null);

        if (playa.sombrillas != null)
            params.put("sombrillas", playa.sombrillas.toString());
        else
            params.put("sombrillas", null);

        if (playa.chiringuitos != null)
            params.put("chiringuitos", playa.chiringuitos.toString());
        else
            params.put("chiringuitos", null);

        if (playa.duchas != null)
            params.put("duchas", playa.duchas.toString());
        else
            params.put("duchas", null);

        if (playa.socorrista != null)
            params.put("socorrista", playa.socorrista.toString());
        else
            params.put("socorrista", null);

        if (playa.perros != null)
            params.put("perros", playa.perros.toString());
        else
            params.put("perros", null);

        if (playa.nudista != null)
            params.put("nudista", playa.nudista.toString());
        else
            params.put("nudista", null);

        if (playa.cerrada != null)
            params.put("cerrada", playa.cerrada.toString());
        else
            params.put("cerrada", null);

        final String URL = Config.getURLServer(ctx) + "/playasbyextras/" + params.get("nombre") +
                                                                     "/" + params.get("lon") +
                                                                     "/" + params.get("lat") +
                                                                     "/" + params.get("banderaazul") +
                                                                     "/" + params.get("acceso") +
                                                                     "/" + params.get("arena") +
                                                                     "/" + params.get("limpieza") +
                                                                     "/" + params.get("rompeolas") +
                                                                     "/" + params.get("hamacas") +
                                                                     "/" + params.get("sombrillas") +
                                                                     "/" + params.get("chiringuitos") +
                                                                     "/" + params.get("duchas") +
                                                                     "/" + params.get("socorrista") +
                                                                     "/" + params.get("perros") +
                                                                     "/" + params.get("nudista") +
                                                                     "/" + params.get("cerrada") ;

        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                com.odc.beachodc.webservices.Response.responseGetPlayasByExtra(ctx, response, pd);
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

    public static void getImagenesPlaya (final Context ctx, String idPlaya, final ProgressDialog pd, final Intent intent){
        final String URL = Config.getURLServer(ctx)+"/imagenesplaya/"+idPlaya;
        ValidacionPlaya.imagenes = new ArrayList<Imagen>();
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                com.odc.beachodc.webservices.Response.responseGetImagenesPlaya(ctx, response, pd, intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                ValidacionPlaya.cargadaImagenes=true;
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

    public static void nuevaImagenPlaya (final Activity activity, final Imagen imagen, final ProgressDialog pd) {
        final String URL = Config.getURLServer(activity)+"/nuevaimagen/"+imagen.idplaya+"/"+imagen.idfbautor;

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("nombreAutor", imagen.nombreautor);
        params.put("comentario", imagen.comentario);
        params.put("link", imagen.link);
        params.put("fecha", formatFecha(imagen.fecha));

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        com.odc.beachodc.webservices.Response.responseNuevaImagenPlaya(activity, response, pd, imagen);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                pd.dismiss();
                Crouton.makeText(activity, R.string.error_unknown, Style.ALERT).show();
            }
        });

        // add the request object to the queue to be executed
        Config.addToRequestQueue(activity, req);
    }


}
