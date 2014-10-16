package com.odc.beachodc.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.odc.beachodc.Home;
import com.odc.beachodc.Inicio;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Checkin;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.webservices.Request;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Paco on 17/01/14.
 */
public class Utilities {

    public static final String PROPERTY_REG_ID = "registration_idfacebook";
    public static final String PROPERTY_REG_NAME = "registration_namefacebook";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static DisplayImageOptions options;
    public static ImageLoader imageLoader;



    public static void setImageLoader(Context ctx){
        Utilities.setOptionsImageLoader();
        ImageLoader.getInstance().init(Utilities.getImageLoaderConfiguration(ctx));
        Utilities.imageLoader = ImageLoader.getInstance();
        Utilities.imageLoader.clearDiskCache();
        Utilities.imageLoader.clearMemoryCache();
    }

    public static void setOptionsImageLoader (){
        Utilities.options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.error_cargar_webcam)
                .showImageOnFail(R.drawable.error_cargar_webcam)
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public static ImageLoaderConfiguration getImageLoaderConfiguration(Context ctx){
        File cacheDir = new File(ctx.getCacheDir(), "imgcachedir");
        if (!cacheDir.exists())
            cacheDir.mkdir();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx)
                .threadPoolSize(1)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .diskCacheExtraOptions(480, 320, null)
                .defaultDisplayImageOptions(Utilities.options)
                .build();
        return config;
    }

    public static String getCamelCase(String init){
        if (init==null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.equals("")) {
                ret.append(Character.toUpperCase(word.charAt(0)));
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length()==init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

    public static boolean haveInternet(Context ctx) {

        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null || !info.isConnected()) {
            return false;
        }
        if (info.isRoaming()) {
            // here is the roaming option you can change it if you want to
            // disable internet while roaming, just return false
        }
        if (isConnectionFast(info.getType(), info.getSubtype()))
            return true;
        return false;
    }

    public static boolean isConnectionFast(int type, int subType){
        if(type== ConnectivityManager.TYPE_WIFI){
            return true;
        }else if(type==ConnectivityManager.TYPE_MOBILE){
            switch(subType){
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
            /*
             * Above API level 7, make sure to set android:targetSdkVersion
             * to appropriate level to use these
             */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        }else{
            return false;
        }
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    static private SharedPreferences getUserPreferences(Context context) {
        // This sample app persists the registration IDFB in shared preferences, but
        // how you store the IDFB in your app is up to you.
        return context.getSharedPreferences(Inicio.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    /**
     * Stores IDFB and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param idFB registration ID FACEBOOK
     * @param nameFB name FACEBOOK Profile
     */
    static public void storeRegistrationId(Context context, String idFB, String nameFB) {
        final SharedPreferences prefs = getUserPreferences(context);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, idFB);
        editor.putString(PROPERTY_REG_NAME, nameFB);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the ID on Facebook, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID FB, or empty string if there is no existing
     *         registration ID FB.
     */
    static public String getUserIdFacebook(Context context) {
        final SharedPreferences prefs = getUserPreferences(context);
        String idFB = prefs.getString(PROPERTY_REG_ID, "");
        if (idFB.isEmpty()) {
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return idFB;
    }

    /**
     * Gets the Name on Facebook, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration Name FB, or empty string if there is no existing
     *         registration Name FB.
     */
    static public String getUserNameFacebook(Context context) {
        final SharedPreferences prefs = getUserPreferences(context);
        String nameFB = prefs.getString(PROPERTY_REG_NAME, "");
        if (nameFB.isEmpty()) {
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return nameFB;
    }

    static public void setActionBarCustomize(Activity activity){
        activity.getActionBar().setDisplayShowCustomEnabled(true);
        activity.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(activity);
        View v = inflator.inflate(R.layout.actionbar_customize, null);

        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/LobsterTwo-Bold.ttf");

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        TextView title = (TextView)v.findViewById(R.id.title);
        title.setTypeface(tf);
        title.setText(activity.getTitle());

        //assign the view to the actionbar
        activity.getActionBar().setCustomView(v);
    }


    public static String formatFecha (Date date){
        try {
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return DATE_FORMAT.format(date);
        } catch (Exception e){
            return "-";
        }
    }

    public static String formatFechaNotHour (Date date){
        try {
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
            return DATE_FORMAT.format(date);
        } catch (Exception e){
            return "-";
        }
    }

    public static Date dateStringZTToDate (String zt){
        Date ret = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(zt));
            ret = cal.getTime();
        } catch (Exception e){}
        return ret;
    }

    public static String getTemperatureC (Context ctx, Double temp){
        if (temp == null)
            return "-";
        return temp.intValue()+" "+ctx.getString(R.string.grados_centigrados);
    }

    public static boolean validarBusqueda(RadioGroup busqueda, EditText nombrePlaya, Activity activity, LatLng porCercania){
        if (busqueda.getCheckedRadioButtonId() == R.id.searchByName){
            if (!nombrePlaya.getText().toString().equals("")) {
                return true;
            } else {
                Crouton.makeText(activity, R.string.error_search_name, Style.ALERT).show();
            }
        } else if (busqueda.getCheckedRadioButtonId() == R.id.searchByAddress){
            if (porCercania != null){
                return true;
            } else {
                Crouton.makeText(activity, R.string.error_search_address, Style.ALERT).show();
            }
        }
        return false;
    }

    public static void buscarPlaya (RadioGroup busqueda, EditText nombrePlaya, Activity activity, LatLng porCercania, EditText direccion, Playa playa){
        if (!playa.isEmptyForSearch()){
            String nombre=null;
            if (busqueda.getCheckedRadioButtonId() == R.id.searchByName){
                if (!nombrePlaya.getText().toString().equals("")) {
                    nombre = nombrePlaya.getText().toString();
                }
            }
            ProgressDialog pdI = ProgressDialog.show(activity, activity.getResources().getText(R.string.esperar), activity.getResources().getText(R.string.esperar));
            pdI.setIndeterminate(false);
            pdI.setCancelable(true);
            Request.getPlayasByExtras(activity, nombre, porCercania, playa, pdI);
        } else {
            if (Utilities.validarBusqueda(busqueda, nombrePlaya, activity, porCercania)) {
                if (busqueda.getCheckedRadioButtonId() == R.id.searchByName) {
                    // Buscar por nombrePlaya
                    ProgressDialog pdI = ProgressDialog.show(activity, activity.getResources().getText(R.string.esperar), activity.getResources().getText(R.string.esperar));
                    pdI.setIndeterminate(false);
                    pdI.setCancelable(true);
                    Request.getPlayasByName(activity, nombrePlaya.getText().toString(), pdI);
                } else if (busqueda.getCheckedRadioButtonId() == R.id.searchByAddress) {
                    // Buscar porCercania
                    ProgressDialog pdI = ProgressDialog.show(activity, activity.getResources().getText(R.string.esperar), activity.getResources().getText(R.string.esperar));
                    pdI.setIndeterminate(false);
                    pdI.setCancelable(true);
                    Request.getPlayasCercanasTo(activity, direccion.getText().toString(), porCercania.latitude, porCercania.longitude, pdI);
                } else {
                    Crouton.makeText(activity, R.string.error_unknown, Style.ALERT).show();
                }
            }
        }
    }

    public static List<Comentario> orderByDateComentario (List<Comentario> comentarios){
        Collections.sort(comentarios, new ComentariosFechaComparator());
        return comentarios;
    }

    public static List<MensajeBotella> orderByDateMensajeBotella (List<MensajeBotella> mensajesBotella){
        Collections.sort(mensajesBotella, new MensajesBotellaFechaComparator());
        return mensajesBotella;
    }

    public static List<Playa> orderByDateCheckins (List<Playa> checkins){
        Collections.sort(checkins, new CheckinFechaComparator());
        return checkins;
    }

    public static String getURIDrawable (int drawable){
        return "drawable://" + drawable;
    }

    public static String getURIIconWeather (String icon){
        return "http://openweathermap.org/img/w/"+icon+".png";
    }

    public static boolean isAnonymous (Context ctx){
        if ((Utilities.getUserIdFacebook(ctx) == null) || (Utilities.getUserIdFacebook(ctx) == "")){
            return true;
        }
        return false;
    }

    public static void goToLoginAsking (final Activity activity){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder
                .setTitle(activity.getString(R.string.no_login))
                .setMessage(activity.getString(R.string.need_login))
                .setCancelable(true)
                .setNegativeButton(activity.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Crouton.cancelAllCroutons();
                                Crouton.makeText(activity, activity.getString(R.string.need_login), Style.ALERT).show();
                            }
                        })
                .setPositiveButton(activity.getString(R.string.login),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(activity, Inicio.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static boolean checkGooglePlayServiceAvailability(Activity activity) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);

        // Check Google Play Service Available
        try {
            if (status != ConnectionResult.SUCCESS) {
                GooglePlayServicesUtil.getErrorDialog(status, activity, 1).show();
            } else {
                return true;
            }
        } catch (Exception e) {
            Log.e("Error: GooglePlayServiceUtil: ", "" + e);
        }
        return false;
    }

    public static void publishFeedDialog(final Activity activity) {
        Bundle params = new Bundle();
        String description = activity.getString(R.string.temp_fail);
        if ((ValidacionPlaya.temperatura != null) && (ValidacionPlaya.temperatura > 0))
            description = activity.getString(R.string.content_share_0) + " " + Utilities.getTemperatureC(activity, ValidacionPlaya.temperatura) + " " + activity.getString(R.string.content_share_1);
        params.putString("name", activity.getString(R.string.title_share)+ " " + ValidacionPlaya.playa.nombre);
        params.putString("caption", activity.getString(R.string.subtitle_share));
        params.putString("description", description);
        params.putString("link", activity.getString(R.string.url_share));
        if ((ValidacionPlaya.imagenes != null) && (ValidacionPlaya.imagenes.size()>0))
            params.putString("picture", ValidacionPlaya.imagenes.get(0).link);

        WebDialog feedDialog = (
                new WebDialog.FeedDialogBuilder(activity, Session.getActiveSession(),params)).setOnCompleteListener(new WebDialog.OnCompleteListener() {

            @Override
            public void onComplete(Bundle values, FacebookException error) {
                if (error == null) {
                    // When the story is posted, echo the success
                    // and the post Id.
                    final String postId = values.getString("post_id");
                    if (postId != null) {
                        Crouton.makeText(activity, activity.getString(R.string.share_fb), Style.CONFIRM).show();
                    } else {
                        // User clicked the Cancel button
                    }
                } else if (error instanceof FacebookOperationCanceledException) {
                    // User clicked the "x" button
                } else {
                    // Generic, ex: network error
                }
            }

        })
                .build();
        feedDialog.show();
    }

    public static void hideSoftKeyboard(View view, Activity activity) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
