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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.AnimateFirstDisplayListener;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;

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
            viewHolder.perrosIV = (ImageView) vi.findViewById(R.id.perrosImage);
            viewHolder.nudistaIV = (ImageView) vi.findViewById(R.id.nudistaImage);
            viewHolder.cerradaIV = (ImageView) vi.findViewById(R.id.cerradaImage);
            viewHolder.distanciaTV = (TextView) vi.findViewById(R.id.distanciaTV);
            viewHolder.nombreTV = (TextView) vi.findViewById(R.id.nombreTV);
            viewHolder.valoracionTV = (TextView) vi.findViewById(R.id.valoracionTV);
            viewHolder.webcam = (ImageView) vi.findViewById(R.id.webcam_icon);
            viewHolder.starsRL = (RelativeLayout) vi.findViewById(R.id.starsRL);
            viewHolder.v1 = (ImageView) vi.findViewById(R.id.v1);
            viewHolder.v2 = (ImageView) vi.findViewById(R.id.v2);
            viewHolder.v3 = (ImageView) vi.findViewById(R.id.v3);
            viewHolder.v4 = (ImageView) vi.findViewById(R.id.v4);
            viewHolder.v5 = (ImageView) vi.findViewById(R.id.v5);
            vi.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) vi.getTag();
        }

        Playa playa = items.get(position);

        // Trabajar con la UI, que es 'vi'

        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/aSongforJenniferBold.ttf");

        viewHolder.nombreTV.setTypeface(tf);
        viewHolder.nombreTV.setText(playa.nombre);

        if ((playa.webcamURL != null) && (!playa.webcamURL.equals(""))) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.webcam), viewHolder.webcam, animateFirstListener);
            viewHolder.webcam.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.RIGHT_OF, R.id.webcam_icon);
            lp.addRule(RelativeLayout.LEFT_OF, R.id.distanciaTV);
            lp.setMargins(5, 5, 5, 0);
            viewHolder.nombreTV.setLayoutParams(lp);
        } else{
            viewHolder.webcam.setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.LEFT_OF, R.id.distanciaTV);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            lp.setMargins(15, 5, 5, 0);
            viewHolder.nombreTV.setLayoutParams(lp);
        }

        tf = Typeface.createFromAsset(activity.getAssets(), "fonts/aSongforJennifer.ttf");

        if (isCheckins) {
            viewHolder.starsRL.setVisibility(View.GONE);
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
                setValoracion(playa.valoracion.intValue());
                DecimalFormat df = new DecimalFormat("#.0");
                viewHolder.valoracionTV.setText(df.format(playa.valoracion).replace(".", ","));
                if (playa.valoracion == 0){
                    viewHolder.valoracionTV.setText("-,-");
                    viewHolder.valoracionTV.setTextColor(Color.rgb(155, 155, 155));
                }else if (playa.valoracion < 3) {
                    viewHolder.valoracionTV.setTextColor(Color.rgb(189, 22, 13));
                } else if (playa.valoracion < 4) {
                    viewHolder.valoracionTV.setTextColor(Color.rgb(255, 149, 20));
                } else if (playa.valoracion < 5) {
                    viewHolder.valoracionTV.setTextColor(Color.rgb(238, 180, 0));
                }
                if (playa.valoracion == 5) {
                    viewHolder.valoracionTV.setTextColor(Color.rgb(0, 121, 0));
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
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_si), viewHolder.banderaazulIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), viewHolder.banderaazulIV, animateFirstListener);
        }

        if (playa.dificultadacceso != null){
            if (playa.dificultadacceso.equals("media")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_media), viewHolder.dificultadaccesoIV, animateFirstListener);
            } else if (playa.dificultadacceso.equals("extrema")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_alta), viewHolder.dificultadaccesoIV, animateFirstListener);
            } else {
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), viewHolder.dificultadaccesoIV, animateFirstListener);
            }
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), viewHolder.dificultadaccesoIV, animateFirstListener);
        }

        if (playa.limpieza != null){
            if (playa.limpieza.equals("sucia")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_sucia), viewHolder.limpiezaIV, animateFirstListener);
            } else if (playa.limpieza.equals("mucho")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_limpia), viewHolder.limpiezaIV, animateFirstListener);
            } else {
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), viewHolder.limpiezaIV, animateFirstListener);
            }
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), viewHolder.limpiezaIV, animateFirstListener);
        }

        if (playa.tipoarena != null){
            if (playa.tipoarena.equals("blanca")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_blanca), viewHolder.tipoarenaIV, animateFirstListener);
            } else if (playa.tipoarena.equals("rocas")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_rocas), viewHolder.tipoarenaIV, animateFirstListener);
            } else {
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), viewHolder.tipoarenaIV, animateFirstListener);
            }
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), viewHolder.tipoarenaIV, animateFirstListener);
        }

        if ((playa.rompeolas != null) && (playa.rompeolas)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_si), viewHolder.rompeolasIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), viewHolder.rompeolasIV, animateFirstListener);
        }

        if ((playa.hamacas != null) && (playa.hamacas)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_si), viewHolder.hamacasIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), viewHolder.hamacasIV, animateFirstListener);
        }

        if ((playa.sombrillas != null) && (playa.sombrillas)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_si), viewHolder.sombrillasIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), viewHolder.sombrillasIV, animateFirstListener);
        }

        if ((playa.chiringuitos != null) && (playa.chiringuitos)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), viewHolder.chiringuitosIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_no), viewHolder.chiringuitosIV, animateFirstListener);
        }

        if ((playa.duchas != null) && (playa.duchas)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_si), viewHolder.duchasIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), viewHolder.duchasIV, animateFirstListener);
        }

        if ((playa.socorrista != null) && (playa.socorrista)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_si), viewHolder.socorristaIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), viewHolder.socorristaIV, animateFirstListener);
        }

        if ((playa.perros != null) && (playa.perros)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.perros_si), viewHolder.perrosIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.perros_no), viewHolder.perrosIV, animateFirstListener);
        }

        if ((playa.nudista != null) && (playa.nudista)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.nudista_si), viewHolder.nudistaIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.nudista_no), viewHolder.nudistaIV, animateFirstListener);
        }

        if (!isCheckins) {
            if ((playa.cerrada != null) && (playa.cerrada)) {
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.cerrada), viewHolder.cerradaIV, animateFirstListener);
                viewHolder.cerradaIV.setVisibility(View.VISIBLE);
            } else {
                viewHolder.cerradaIV.setVisibility(View.GONE);
            }
        } else {
            viewHolder.cerradaIV.setVisibility(View.GONE);
        }
    }

    public void setValoracion(int valoracion){
        if (valoracion == 1){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v5, animateFirstListener);
        } else if (valoracion == 2){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v5, animateFirstListener);
        } else if (valoracion == 3){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v5, animateFirstListener);
        } else if (valoracion == 4){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v5, animateFirstListener);
        } else if (valoracion == 5){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), viewHolder.v5, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), viewHolder.v5, animateFirstListener);
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
        ImageView perrosIV;
        ImageView nudistaIV;
        ImageView cerradaIV;
        ImageView socorristaIV;
        TextView nombreTV;
        TextView distanciaTV;
        TextView valoracionTV;
        ImageView webcam;
        ImageView v1, v2, v3, v4, v5;
        RelativeLayout starsRL;
    }
}
