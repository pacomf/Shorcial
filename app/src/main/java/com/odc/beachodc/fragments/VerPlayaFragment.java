package com.odc.beachodc.fragments;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class VerPlayaFragment extends Fragment {

        View rootView;
        GoogleMap mapa;
        ImageView v1, v2, v3, v4, v5;

        public VerPlayaFragment() {
            // Se ejecuta antes que el onCreateView

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            if (rootView != null) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (parent != null)
                    parent.removeView(rootView);
            }
            try {
                rootView = inflater.inflate(R.layout.fragment_ver_playa, container, false);
            } catch (InflateException e) {}

            try {
                MapsInitializer.initialize(getActivity());
                mapa = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud), 15.0f);
                mapa.moveCamera(cameraUpdate);
            } catch (GooglePlayServicesNotAvailableException e) {}

            // Empezar aqui a trabajar con la UI

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aSongforJenniferBold.ttf");

            TextView nombreTV = (TextView) rootView.findViewById(R.id.nombreTV);
            nombreTV.setTypeface(tf);
            nombreTV.setText(ValidacionPlaya.playa.nombre);

            TextView tempTV = (TextView) rootView.findViewById(R.id.temp);
            tempTV.setTypeface(tf);
            tempTV.setText(Utilities.getTemperatureC(getActivity(), ValidacionPlaya.temperatura));

            ImageView banderaazulIV = (ImageView) rootView.findViewById(R.id.banderaAzulImage);
            ImageView dificultadaccesoIV = (ImageView) rootView.findViewById(R.id.dificultadAccesoImage);
            ImageView limpiezaIV = (ImageView) rootView.findViewById(R.id.limpiezaImage);
            ImageView tipoarenaIV = (ImageView) rootView.findViewById(R.id.tipoArenaImage);
            ImageView rompeolasIV = (ImageView) rootView.findViewById(R.id.rompeolasImage);
            ImageView hamacasIV = (ImageView) rootView.findViewById(R.id.hamacasImage);
            ImageView sombrillasIV = (ImageView) rootView.findViewById(R.id.sombrillasImage);
            ImageView duchasIV = (ImageView) rootView.findViewById(R.id.duchasImage);
            ImageView chiringuitosIV = (ImageView) rootView.findViewById(R.id.chiringuitosImage);
            ImageView socorristaIV = (ImageView) rootView.findViewById(R.id.socorristaImage);

            setIconsExtraInfo(ValidacionPlaya.playa, banderaazulIV, dificultadaccesoIV, limpiezaIV, tipoarenaIV, rompeolasIV, hamacasIV, sombrillasIV, duchasIV, chiringuitosIV, socorristaIV);

            v1 = (ImageView) rootView.findViewById(R.id.v1);
            v2 = (ImageView) rootView.findViewById(R.id.v2);
            v3 = (ImageView) rootView.findViewById(R.id.v3);
            v4 = (ImageView) rootView.findViewById(R.id.v4);
            v5 = (ImageView) rootView.findViewById(R.id.v5);

            setValoracion(ValidacionPlaya.playa.valoracion.intValue());

            return rootView;
        }

    private void setIconsExtraInfo (Playa playa, ImageView banderaazul, ImageView dificultadacceso, ImageView limpieza, ImageView tipoarena, ImageView rompeolas,
                                    ImageView hamacas, ImageView sombrillas, ImageView duchas, ImageView chiringuitos, ImageView socorrista){

        if ((playa.banderaazul != null) && (playa.banderaazul))
            banderaazul.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            banderaazul.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if (playa.dificultadacceso != null){
            if (playa.dificultadacceso.equals("media")){
                dificultadacceso.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
            } else if (playa.dificultadacceso.equals("extrema")){
                dificultadacceso.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_picture_blank_square));
            } else {
                dificultadacceso.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
            }
        } else {
            dificultadacceso.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
        }

        if (playa.limpieza != null){
            if (playa.limpieza.equals("sucia")){
                limpieza.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
            } else if (playa.limpieza.equals("mucho")){
                limpieza.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_picture_blank_square));
            } else {
                limpieza.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
            }
        } else {
            limpieza.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
        }

        if (playa.tipoarena != null){
            if (playa.tipoarena.equals("blanca")){
                tipoarena.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
            } else if (playa.tipoarena.equals("rocas")){
                tipoarena.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_picture_blank_square));
            } else {
                tipoarena.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
            }
        } else {
            tipoarena.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
        }

        if ((playa.rompeolas != null) && (playa.rompeolas))
            rompeolas.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            rompeolas.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.hamacas != null) && (playa.hamacas))
            hamacas.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            hamacas.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.sombrillas != null) && (playa.sombrillas))
            sombrillas.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            sombrillas.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.chiringuitos != null) && (playa.chiringuitos))
            chiringuitos.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            chiringuitos.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.duchas != null) && (playa.duchas))
            duchas.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            duchas.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));

        if ((playa.socorrista != null) && (playa.socorrista))
            socorrista.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_profile_default_icon));
        else
            socorrista.setImageDrawable(getResources().getDrawable(R.drawable.com_facebook_place_default_icon));
    }

    public void setValoracion(int valoracion){
        if (valoracion == 1){
            v1.setImageResource(R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_off);
            v3.setImageResource(android.R.drawable.star_off);
            v4.setImageResource(android.R.drawable.star_off);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (valoracion == 2){
            v1.setImageResource(R.drawable.star_on);
            v2.setImageResource(R.drawable.star_on);
            v3.setImageResource(android.R.drawable.star_off);
            v4.setImageResource(android.R.drawable.star_off);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (valoracion == 3){
            v1.setImageResource(R.drawable.star_on);
            v2.setImageResource(R.drawable.star_on);
            v3.setImageResource(R.drawable.star_on);
            v4.setImageResource(android.R.drawable.star_off);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (valoracion == 4){
            v1.setImageResource(R.drawable.star_on);
            v2.setImageResource(R.drawable.star_on);
            v3.setImageResource(R.drawable.star_on);
            v4.setImageResource(R.drawable.star_on);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (valoracion == 5){
            v1.setImageResource(R.drawable.star_on);
            v2.setImageResource(R.drawable.star_on);
            v3.setImageResource(R.drawable.star_on);
            v4.setImageResource(R.drawable.star_on);
            v5.setImageResource(R.drawable.star_on);
        }
    }

}
