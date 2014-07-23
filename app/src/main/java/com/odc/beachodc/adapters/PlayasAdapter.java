package com.odc.beachodc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.AnimateFirstDisplayListener;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Paco on 09/07/2014.
 */
public class PlayasAdapter extends BaseAdapter {

    protected Activity activity;
    protected List<Playa> items;
    protected boolean isCheckins;
    protected boolean isSearchByLocation;
    protected Double latitudO, longitudO;

    ViewHolder viewHolder;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    protected ImageLoader imageLoader = ImageLoader.getInstance();


    public PlayasAdapter(Activity activity, List<Playa> items, boolean checkins) {
        this.activity = activity;
        this.items = items;
        if (checkins)
            this.isCheckins=true;
        this.isSearchByLocation = false;
        viewHolder = new ViewHolder();
    }

    public PlayasAdapter(Activity activity, List<Playa> items, Double latitudO, Double longitudO) {
        this.activity = activity;
        this.items = items;
        this.isCheckins=false;
        this.isSearchByLocation = true;
        this.latitudO = latitudO;
        this.longitudO = longitudO;
        viewHolder = new ViewHolder();
    }

    public PlayasAdapter(Activity activity, List<Playa> items) {
        this.activity = activity;
        this.items = items;
        this.isCheckins=false;
        this.isSearchByLocation = false;
        viewHolder = new ViewHolder();
    }

    @Override
    public int getCount() {
        return (items == null) ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.item_playa_list, null);
            viewHolder.banderaazulIV = (ImageView) vi.findViewById(R.id.banderaAzulImage);
            viewHolder.dificultadaccesoIV = (ImageView) vi.findViewById(R.id.dificultadAccesoImage);
            viewHolder.limpiezaIV = (ImageView) vi.findViewById(R.id.limpiezaImage);
            viewHolder.tipoarenaIV = (ImageView) vi.findViewById(R.id.tipoArenaImage);
            viewHolder.rompeolasIV = (ImageView) vi.findViewById(R.id.rompeolasImage);
            viewHolder.hamacasIV = (ImageView) vi.findViewById(R.id.hamacasImage);
            viewHolder.sombrillasIV = (ImageView) vi.findViewById(R.id.sombrillasImage);
            viewHolder.duchasIV = (ImageView) vi.findViewById(R.id.duchasImage);
            viewHolder.chiringuitosIV = (ImageView) vi.findViewById(R.id.chiringuitosImage);
            viewHolder.socorristaIV = (ImageView) vi.findViewById(R.id.socorristaImage);
            viewHolder.distanciaTV = (TextView) vi.findViewById(R.id.distanciaTV);
            viewHolder.nombreTV = (TextView) vi.findViewById(R.id.nombreTV);
            viewHolder.valoracionTV = (TextView) vi.findViewById(R.id.valoracionTV);
            vi.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) vi.getTag();
        }

        Playa playa = items.get(position);

        // Trabajar con la UI, que es 'vi'

        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/aSongforJenniferBold.ttf");

        viewHolder.nombreTV.setTypeface(tf);
        viewHolder.nombreTV.setText(playa.nombre);

        tf = Typeface.createFromAsset(activity.getAssets(), "fonts/aSongforJennifer.ttf");

        if (isCheckins) {
            viewHolder.valoracionTV.setVisibility(View.GONE);
            //TextView titleCheckinsTV = (TextView) vi.findViewById(R.id.title_checkins);
            //titleCheckinsTV.setVisibility(View.VISIBLE);
            //titleCheckinsTV.setTypeface(tf);
            // TODO: Calcular el número de checkins que haya hecho el usuario sobre esa playa
            //TextView checkinsTV = (TextView) vi.findViewById(R.id.checkinsTV);
            //checkinsTV.setVisibility(View.VISIBLE);
            //checkinsTV.setTypeface(tf);
            //int totalCheckins = 12;
            //checkinsTV.setText(String.valueOf(totalCheckins));

        } else {
            viewHolder.valoracionTV.setTypeface(tf);
            if (playa.valoracion != null) {
                DecimalFormat df = new DecimalFormat("#.#");
                viewHolder.valoracionTV.setText(df.format(playa.valoracion).replace(".", ","));
                if (playa.valoracion == 0){
                    viewHolder.valoracionTV.setText("-.-");
                    viewHolder.valoracionTV.setTextColor(Color.rgb(238, 180, 0));
                }else if (playa.valoracion < 3) {
                    viewHolder.valoracionTV.setTextColor(Color.rgb(189, 22, 13));
                } else if (playa.valoracion < 4) {
                    viewHolder.valoracionTV.setTextColor(Color.rgb(38, 130, 180));
                } else if (playa.valoracion < 5) {
                    viewHolder.valoracionTV.setTextColor(Color.rgb(0, 121, 0));
                }
                if (playa.valoracion == 5) {
                    viewHolder.valoracionTV.setTextColor(Color.rgb(238, 180, 0));
                }

            }
        }


        viewHolder.distanciaTV.setTypeface(tf);
        if (isCheckins)
            viewHolder.distanciaTV.setText(Utilities.formatFechaNotHour(playa.checkin));
        else {
            try {
                if (isSearchByLocation) {
                    viewHolder.distanciaTV.setText(Geo.getDistanceToPrint(activity, this.latitudO, this.longitudO, playa.latitud, playa.longitud));
                } else {
                    viewHolder.distanciaTV.setText(Geo.getDistanceToPrint(activity, playa.latitud, playa.longitud));
                }
            } catch (Exception e){
                viewHolder.distanciaTV.setText(Geo.getDistanceToPrint(activity, playa.latitud, playa.longitud));
            }
        }

        setIconsExtraInfo(playa);

        return vi;
    }

    private void setIconsExtraInfo (Playa playa){

        if ((playa.banderaazul != null) && (playa.banderaazul)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_si), viewHolder.banderaazulIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), viewHolder.banderaazulIV, Utilities.options, animateFirstListener);
        }

        if (playa.dificultadacceso != null){
            if (playa.dificultadacceso.equals("media")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_media), viewHolder.dificultadaccesoIV, Utilities.options, animateFirstListener);
            } else if (playa.dificultadacceso.equals("extrema")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_alta), viewHolder.dificultadaccesoIV, Utilities.options, animateFirstListener);
            } else {
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), viewHolder.dificultadaccesoIV, Utilities.options, animateFirstListener);
            }
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), viewHolder.dificultadaccesoIV, Utilities.options, animateFirstListener);
        }

        if (playa.limpieza != null){
            if (playa.limpieza.equals("sucia")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_sucia), viewHolder.limpiezaIV, Utilities.options, animateFirstListener);
            } else if (playa.limpieza.equals("mucho")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_limpia), viewHolder.limpiezaIV, Utilities.options, animateFirstListener);
            } else {
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), viewHolder.limpiezaIV, Utilities.options, animateFirstListener);
            }
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), viewHolder.limpiezaIV, Utilities.options, animateFirstListener);
        }

        if (playa.tipoarena != null){
            if (playa.tipoarena.equals("blanca")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_blanca), viewHolder.tipoarenaIV, Utilities.options, animateFirstListener);
            } else if (playa.tipoarena.equals("rocas")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_rocas), viewHolder.tipoarenaIV, Utilities.options, animateFirstListener);
            } else {
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), viewHolder.tipoarenaIV, Utilities.options, animateFirstListener);
            }
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), viewHolder.tipoarenaIV, Utilities.options, animateFirstListener);
        }

        if ((playa.rompeolas != null) && (playa.rompeolas)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_si), viewHolder.rompeolasIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), viewHolder.rompeolasIV, Utilities.options, animateFirstListener);
        }

        if ((playa.hamacas != null) && (playa.hamacas)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_si), viewHolder.hamacasIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), viewHolder.hamacasIV, Utilities.options, animateFirstListener);
        }

        if ((playa.sombrillas != null) && (playa.sombrillas)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_si), viewHolder.sombrillasIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), viewHolder.sombrillasIV, Utilities.options, animateFirstListener);
        }

        if ((playa.chiringuitos != null) && (playa.chiringuitos)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), viewHolder.chiringuitosIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_no), viewHolder.chiringuitosIV, Utilities.options, animateFirstListener);
        }

        if ((playa.duchas != null) && (playa.duchas)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_si), viewHolder.duchasIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), viewHolder.duchasIV, Utilities.options, animateFirstListener);
        }

        if ((playa.socorrista != null) && (playa.socorrista)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_si), viewHolder.socorristaIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), viewHolder.socorristaIV, Utilities.options, animateFirstListener);
        }
    }

    static class ViewHolder {
        ImageView banderaazulIV;
        ImageView dificultadaccesoIV;
        ImageView limpiezaIV;
        ImageView tipoarenaIV;
        ImageView rompeolasIV;
        ImageView hamacasIV;
        ImageView sombrillasIV;
        ImageView duchasIV;
        ImageView chiringuitosIV;
        ImageView socorristaIV;
        TextView nombreTV;
        TextView distanciaTV;
        TextView valoracionTV;


    }
}
