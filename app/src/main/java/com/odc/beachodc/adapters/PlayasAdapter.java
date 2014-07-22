package com.odc.beachodc.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;

import java.text.DecimalFormat;
import java.util.Date;
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

    public PlayasAdapter(Activity activity, List<Playa> items, boolean checkins) {
        this.activity = activity;
        this.items = items;
        if (checkins)
            this.isCheckins=true;
        this.isSearchByLocation = false;
    }

    public PlayasAdapter(Activity activity, List<Playa> items, Double latitudO, Double longitudO) {
        this.activity = activity;
        this.items = items;
        this.isCheckins=false;
        this.isSearchByLocation = true;
        this.latitudO = latitudO;
        this.longitudO = longitudO;
    }

    public PlayasAdapter(Activity activity, List<Playa> items) {
        this.activity = activity;
        this.items = items;
        this.isCheckins=false;
        this.isSearchByLocation = false;
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
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.item_playa_list, null);
        }

        Playa playa = items.get(position);

        // Trabajar con la UI, que es 'vi'

        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/aSongforJenniferBold.ttf");

        TextView nombreTV = (TextView) vi.findViewById(R.id.nombreTV);
        nombreTV.setTypeface(tf);
        nombreTV.setText(playa.nombre);

        tf = Typeface.createFromAsset(activity.getAssets(), "fonts/aSongforJennifer.ttf");

        TextView valoracionTV = (TextView) vi.findViewById(R.id.valoracionTV);

        if (isCheckins) {
            valoracionTV.setVisibility(View.GONE);
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
            valoracionTV.setTypeface(tf);
            if (playa.valoracion != null) {
                DecimalFormat df = new DecimalFormat("#.#");
                valoracionTV.setText(df.format(playa.valoracion).replace(".", ","));
                if (playa.valoracion == 0){
                    valoracionTV.setText("-.-");
                    valoracionTV.setTextColor(Color.rgb(238, 180, 0));
                }else if (playa.valoracion < 3) {
                    valoracionTV.setTextColor(Color.rgb(189, 22, 13));
                } else if (playa.valoracion < 4) {
                    valoracionTV.setTextColor(Color.rgb(38, 130, 180));
                } else if (playa.valoracion < 5) {
                    valoracionTV.setTextColor(Color.rgb(0, 121, 0));
                }
                if (playa.valoracion == 5) {
                    valoracionTV.setTextColor(Color.rgb(238, 180, 0));
                }

            }
        }


        TextView distanciaTV = (TextView) vi.findViewById(R.id.distanciaTV);
        distanciaTV.setTypeface(tf);
        if (isCheckins)
            distanciaTV.setText(Utilities.formatFechaNotHour(playa.checkin));
        else {
            try {
                if (isSearchByLocation) {
                    distanciaTV.setText(Geo.getDistanceToPrint(activity, this.latitudO, this.longitudO, playa.latitud, playa.longitud));
                } else {
                    distanciaTV.setText(Geo.getDistanceToPrint(activity, playa.latitud, playa.longitud));
                }
            } catch (Exception e){
                distanciaTV.setText(Geo.getDistanceToPrint(activity, playa.latitud, playa.longitud));
            }
        }

        ImageView banderaazulIV = (ImageView) vi.findViewById(R.id.banderaAzulImage);
        ImageView dificultadaccesoIV = (ImageView) vi.findViewById(R.id.dificultadAccesoImage);
        ImageView limpiezaIV = (ImageView) vi.findViewById(R.id.limpiezaImage);
        ImageView tipoarenaIV = (ImageView) vi.findViewById(R.id.tipoArenaImage);
        ImageView rompeolasIV = (ImageView) vi.findViewById(R.id.rompeolasImage);
        ImageView hamacasIV = (ImageView) vi.findViewById(R.id.hamacasImage);
        ImageView sombrillasIV = (ImageView) vi.findViewById(R.id.sombrillasImage);
        ImageView duchasIV = (ImageView) vi.findViewById(R.id.duchasImage);
        ImageView chiringuitosIV = (ImageView) vi.findViewById(R.id.chiringuitosImage);
        ImageView socorristaIV = (ImageView) vi.findViewById(R.id.socorristaImage);

        setIconsExtraInfo(playa, banderaazulIV, dificultadaccesoIV, limpiezaIV, tipoarenaIV, rompeolasIV, hamacasIV, sombrillasIV, duchasIV, chiringuitosIV, socorristaIV);

        return vi;
    }

    private void setIconsExtraInfo (Playa playa, ImageView banderaazul, ImageView dificultadacceso, ImageView limpieza, ImageView tipoarena, ImageView rompeolas,
                                    ImageView hamacas, ImageView sombrillas, ImageView duchas, ImageView chiringuitos, ImageView socorrista){

        if ((playa.banderaazul != null) && (playa.banderaazul))
            banderaazul.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            banderaazul.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if (playa.dificultadacceso != null){
            if (playa.dificultadacceso.equals("media")){
                dificultadacceso.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
            } else if (playa.dificultadacceso.equals("extrema")){
                dificultadacceso.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_picture_blank_square));
            } else {
                dificultadacceso.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
            }
        } else {
            dificultadacceso.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
        }

        if (playa.limpieza != null){
            if (playa.limpieza.equals("sucia")){
                limpieza.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
            } else if (playa.limpieza.equals("mucho")){
                limpieza.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_picture_blank_square));
            } else {
                limpieza.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
            }
        } else {
            limpieza.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
        }

        if (playa.tipoarena != null){
            if (playa.tipoarena.equals("blanca")){
                tipoarena.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
            } else if (playa.tipoarena.equals("rocas")){
                tipoarena.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_picture_blank_square));
            } else {
                tipoarena.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
            }
        } else {
            tipoarena.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
        }

        if ((playa.rompeolas != null) && (playa.rompeolas))
            rompeolas.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            rompeolas.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.hamacas != null) && (playa.hamacas))
            hamacas.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            hamacas.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.sombrillas != null) && (playa.sombrillas))
            sombrillas.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            sombrillas.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.chiringuitos != null) && (playa.chiringuitos))
            chiringuitos.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            chiringuitos.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.duchas != null) && (playa.duchas))
            duchas.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            duchas.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.socorrista != null) && (playa.socorrista))
            socorrista.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            socorrista.setImageDrawable(activity.getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
    }

}
