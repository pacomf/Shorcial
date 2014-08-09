package com.odc.beachodc.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.AnimateFirstDisplayListener;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.IconosVerPlayaEffect;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class VerPlayaFragment extends Fragment {

        View rootView;
        GoogleMap mapa;
        ImageView v1, v2, v3, v4, v5;
        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
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
        ImageView iconWeather;
        ImageView perrosIV;
        TextView tempTV;


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
            } catch (Exception e) {
                System.out.println("M: "+e.getMessage());
                e.printStackTrace();
            }

            IconosVerPlayaEffect.initConfig();

            try {
                MapsInitializer.initialize(getActivity());
                mapa = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                mapa.setMyLocationEnabled(true);
                mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud), 15.0f);
                mapa.moveCamera(cameraUpdate);
            } catch (Exception e) {}

            // Empezar aqui a trabajar con la UI

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aSongforJenniferBold.ttf");

            TextView nombreTV = (TextView) rootView.findViewById(R.id.nombreTV);
            nombreTV.setTypeface(tf);
            nombreTV.setText(ValidacionPlaya.playa.nombre);

            tempTV = (TextView) rootView.findViewById(R.id.temp);
            tempTV.setTypeface(tf);
            tempTV.setText(Utilities.getTemperatureC(getActivity(), ValidacionPlaya.temperatura));

            iconWeather = (ImageView) rootView.findViewById(R.id.iconWeather);

            Utilities.imageLoader.displayImage(Utilities.getURIIconWeather(ValidacionPlaya.iconWeather), iconWeather, animateFirstListener);

            tempTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tempTV.setVisibility(View.GONE);
                    iconWeather.setVisibility(View.VISIBLE);
                }
            });

            iconWeather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iconWeather.setVisibility(View.GONE);
                    tempTV.setVisibility(View.VISIBLE);
                }
            });

            banderaazulIV = (ImageView) rootView.findViewById(R.id.banderaAzulImage);
            dificultadaccesoIV = (ImageView) rootView.findViewById(R.id.dificultadAccesoImage);
            limpiezaIV = (ImageView) rootView.findViewById(R.id.limpiezaImage);
            tipoarenaIV = (ImageView) rootView.findViewById(R.id.tipoArenaImage);
            rompeolasIV = (ImageView) rootView.findViewById(R.id.rompeolasImage);
            hamacasIV = (ImageView) rootView.findViewById(R.id.hamacasImage);
            sombrillasIV = (ImageView) rootView.findViewById(R.id.sombrillasImage);
            duchasIV = (ImageView) rootView.findViewById(R.id.duchasImage);
            chiringuitosIV = (ImageView) rootView.findViewById(R.id.chiringuitosImage);
            socorristaIV = (ImageView) rootView.findViewById(R.id.socorristaImage);
            perrosIV = (ImageView) rootView.findViewById(R.id.perrosImage);

            v1 = (ImageView) rootView.findViewById(R.id.v1);
            v2 = (ImageView) rootView.findViewById(R.id.v2);
            v3 = (ImageView) rootView.findViewById(R.id.v3);
            v4 = (ImageView) rootView.findViewById(R.id.v4);
            v5 = (ImageView) rootView.findViewById(R.id.v5);

            setValoracion(ValidacionPlaya.playa.valoracion.intValue());

            setIconsExtraInfo(ValidacionPlaya.playa);

            Button irPlaya = (Button) rootView.findViewById(R.id.irPlayaBTN);

            irPlaya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utilities.haveInternet(getActivity())) {
                        if (Geo.myLocation != null) {
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse("http://maps.google.com/maps?saddr=" + Geo.myLocation.getLatitude() + "," + Geo.myLocation.getLongitude() + "&daddr=" + ValidacionPlaya.playa.latitud + "," + ValidacionPlaya.playa.longitud));
                            startActivity(intent);
                        } else {
                            Crouton.makeText(getActivity(), getString(R.string.no_gps), Style.ALERT).show();
                        }
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.no_internet), Style.ALERT).show();
                    }
                }
            });

            IconosVerPlayaEffect.setImages(banderaazulIV, dificultadaccesoIV, limpiezaIV, tipoarenaIV, rompeolasIV, hamacasIV, sombrillasIV, chiringuitosIV, duchasIV, socorristaIV, perrosIV);

            banderaazulIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringBA.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringBA.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.banderaazul != null) && (ValidacionPlaya.playa.banderaazul)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_banderaazul_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_banderaazul_no), Style.INFO).show();
                    }
                    return true;
                }
            });

            dificultadaccesoIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringDA.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringDA.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if (ValidacionPlaya.playa.dificultadacceso != null) {
                        if (ValidacionPlaya.playa.dificultadacceso.equals("media")) {
                            Crouton.makeText(getActivity(), getString(R.string.info_acceso_medio), Style.INFO).show();
                        } else if (ValidacionPlaya.playa.dificultadacceso.equals("extrema")) {
                            Crouton.makeText(getActivity(), getString(R.string.info_acceso_extremo), Style.INFO).show();
                        } else {
                            Crouton.makeText(getActivity(), getString(R.string.info_acceso_facil), Style.INFO).show();
                        }
                    }
                    return true;
                }
            });

            limpiezaIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringL.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringL.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if (ValidacionPlaya.playa.limpieza != null) {
                        if (ValidacionPlaya.playa.limpieza.equals("sucia")) {
                            Crouton.makeText(getActivity(), getString(R.string.info_limpieza_baja), Style.INFO).show();
                        } else if (ValidacionPlaya.playa.limpieza.equals("mucho")) {
                            Crouton.makeText(getActivity(), getString(R.string.info_limpieza_alta), Style.INFO).show();
                        } else {
                            Crouton.makeText(getActivity(), getString(R.string.info_limpieza_media), Style.INFO).show();
                        }
                    }
                    return true;
                }
            });

            tipoarenaIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringTA.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringTA.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if (ValidacionPlaya.playa.tipoarena != null) {
                        if (ValidacionPlaya.playa.tipoarena.equals("blanca")) {
                            Crouton.makeText(getActivity(), getString(R.string.info_arena_blanca), Style.INFO).show();
                        } else if (ValidacionPlaya.playa.tipoarena.equals("rocas")) {
                            Crouton.makeText(getActivity(), getString(R.string.info_arena_roca), Style.INFO).show();
                        } else {
                            Crouton.makeText(getActivity(), getString(R.string.info_arena_negra), Style.INFO).show();
                        }
                    }
                    return true;
                }
            });

            rompeolasIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringR.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringR.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.rompeolas != null) && (ValidacionPlaya.playa.rompeolas)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_rompeolas_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_rompeolas_no), Style.INFO).show();
                    }
                    return true;
                }
            });

            hamacasIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringH.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringH.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.hamacas != null) && (ValidacionPlaya.playa.hamacas)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_hamacas_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_hamacas_no), Style.INFO).show();
                    }
                    return true;
                }
            });

            sombrillasIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringS.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringS.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.sombrillas != null) && (ValidacionPlaya.playa.sombrillas)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_sombrillas_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_sombrillas_no), Style.INFO).show();
                    }
                    return true;
                }
            });

            duchasIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringD.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringD.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.duchas != null) && (ValidacionPlaya.playa.duchas)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_duchas_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_duchas_no), Style.INFO).show();
                    }
                    return true;
                }
            });

            chiringuitosIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringC.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringC.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.chiringuitos != null) && (ValidacionPlaya.playa.chiringuitos)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_chiringuitos_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_chiringuitos_no), Style.INFO).show();
                    }
                    return true;
                }
            });

            socorristaIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringSO.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringSO.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.socorrista != null) && (ValidacionPlaya.playa.socorrista)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_socorrista_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_socorrista_no), Style.INFO).show();
                    }
                    return true;
                }
            });

            perrosIV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            IconosVerPlayaEffect.mScaleSpringP.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            IconosVerPlayaEffect.mScaleSpringP.setEndValue(0);
                            break;
                    }
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.perros != null) && (ValidacionPlaya.playa.perros)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_perros_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_perros_no), Style.INFO).show();
                    }
                    return true;
                }
            });

            return rootView;
        }

    private void setIconsExtraInfo (Playa playa){

        if ((playa.banderaazul != null) && (playa.banderaazul)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_si), banderaazulIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), banderaazulIV, animateFirstListener);
        }

        if (playa.dificultadacceso != null){
            if (playa.dificultadacceso.equals("media")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_media), dificultadaccesoIV, animateFirstListener);
            } else if (playa.dificultadacceso.equals("extrema")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_alta), dificultadaccesoIV, animateFirstListener);
            } else {
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoIV, animateFirstListener);
            }
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoIV, animateFirstListener);
        }

        if (playa.limpieza != null){
            if (playa.limpieza.equals("sucia")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_sucia), limpiezaIV, animateFirstListener);
            } else if (playa.limpieza.equals("mucho")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_limpia), limpiezaIV, animateFirstListener);
            } else {
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaIV, animateFirstListener);
            }
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaIV, animateFirstListener);
        }

        if (playa.tipoarena != null){
            if (playa.tipoarena.equals("blanca")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_blanca), tipoarenaIV, animateFirstListener);
            } else if (playa.tipoarena.equals("rocas")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_rocas), tipoarenaIV, animateFirstListener);
            } else {
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaIV, animateFirstListener);
            }
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaIV, animateFirstListener);
        }

        if ((playa.rompeolas != null) && (playa.rompeolas)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_si), rompeolasIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), rompeolasIV, animateFirstListener);
        }

        if ((playa.hamacas != null) && (playa.hamacas)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_si), hamacasIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), hamacasIV, animateFirstListener);
        }

        if ((playa.sombrillas != null) && (playa.sombrillas)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_si), sombrillasIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), sombrillasIV, animateFirstListener);
        }

        if ((playa.chiringuitos != null) && (playa.chiringuitos)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), chiringuitosIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_no), chiringuitosIV, animateFirstListener);
        }

        if ((playa.duchas != null) && (playa.duchas)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_si), duchasIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), duchasIV, animateFirstListener);
        }

        if ((playa.socorrista != null) && (playa.socorrista)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_si), socorristaIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), socorristaIV, animateFirstListener);
        }

        if ((playa.perros != null) && (playa.perros)) {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.perros_si), perrosIV, animateFirstListener);
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.perros_no), perrosIV, animateFirstListener);
        }
    }

    public void setValoracion(int valoracion){
        if (valoracion == 1){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v5, animateFirstListener);
        } else if (valoracion == 2){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v5, animateFirstListener);
        } else if (valoracion == 3){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v5, animateFirstListener);
        } else if (valoracion == 4){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v5, animateFirstListener);
        } else if (valoracion == 5){
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v2, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v3, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v4, animateFirstListener);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v5, animateFirstListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IconosVerPlayaEffect.addListeners();
    }

    @Override
    public void onPause() {
        super.onPause();
        IconosVerPlayaEffect.removeListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SupportMapFragment f = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (f != null) {
            try {
                getFragmentManager().beginTransaction().remove(f).commit();
            } catch(Exception e){}
        }
    }





}
