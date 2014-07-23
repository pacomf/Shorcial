package com.odc.beachodc.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.AnimateFirstDisplayListener;
import com.odc.beachodc.utilities.Geo;
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
        protected ImageLoader imageLoader = ImageLoader.getInstance();
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
                mapa.setMyLocationEnabled(true);
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

            setIconsExtraInfo(ValidacionPlaya.playa);

            v1 = (ImageView) rootView.findViewById(R.id.v1);
            v2 = (ImageView) rootView.findViewById(R.id.v2);
            v3 = (ImageView) rootView.findViewById(R.id.v3);
            v4 = (ImageView) rootView.findViewById(R.id.v4);
            v5 = (ImageView) rootView.findViewById(R.id.v5);

            setValoracion(ValidacionPlaya.playa.valoracion.intValue());

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

            banderaazulIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.banderaazul != null) && (ValidacionPlaya.playa.banderaazul)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_banderaazul_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_banderaazul_no), Style.INFO).show();
                    }
                }
            });

            dificultadaccesoIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                }
            });

            limpiezaIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crouton.cancelAllCroutons();
                    System.out.println("Pl: "+ValidacionPlaya.playa.limpieza);
                    if (ValidacionPlaya.playa.limpieza != null) {
                        if (ValidacionPlaya.playa.limpieza.equals("sucia")) {
                            Crouton.makeText(getActivity(), getString(R.string.info_limpieza_baja), Style.INFO).show();
                        } else if (ValidacionPlaya.playa.limpieza.equals("mucho")) {
                            Crouton.makeText(getActivity(), getString(R.string.info_limpieza_alta), Style.INFO).show();
                        } else {
                            Crouton.makeText(getActivity(), getString(R.string.info_limpieza_media), Style.INFO).show();
                        }
                    }
                }
            });

            tipoarenaIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crouton.cancelAllCroutons();
                    if (ValidacionPlaya.playa.tipoarena != null){
                        if (ValidacionPlaya.playa.tipoarena.equals("blanca")){
                            Crouton.makeText(getActivity(), getString(R.string.info_arena_blanca), Style.INFO).show();
                        } else if (ValidacionPlaya.playa.tipoarena.equals("rocas")){
                            Crouton.makeText(getActivity(), getString(R.string.info_arena_roca), Style.INFO).show();
                        } else {
                            Crouton.makeText(getActivity(), getString(R.string.info_arena_negra), Style.INFO).show();
                        }
                    }
                }
            });

            rompeolasIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.rompeolas != null) && (ValidacionPlaya.playa.rompeolas)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_rompeolas_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_rompeolas_no), Style.INFO).show();
                    }
                }
            });

            hamacasIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.hamacas != null) && (ValidacionPlaya.playa.hamacas)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_hamacas_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_hamacas_no), Style.INFO).show();
                    }
                }
            });

            sombrillasIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.sombrillas != null) && (ValidacionPlaya.playa.sombrillas)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_sombrillas_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_sombrillas_no), Style.INFO).show();
                    }
                }
            });

            duchasIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.duchas != null) && (ValidacionPlaya.playa.duchas)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_duchas_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_duchas_no), Style.INFO).show();
                    }
                }
            });

            chiringuitosIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.chiringuitos != null) && (ValidacionPlaya.playa.chiringuitos)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_chiringuitos_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_chiringuitos_no), Style.INFO).show();
                    }
                }
            });

            socorristaIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crouton.cancelAllCroutons();
                    if ((ValidacionPlaya.playa.socorrista != null) && (ValidacionPlaya.playa.socorrista)) {
                        Crouton.makeText(getActivity(), getString(R.string.info_socorrista_si), Style.INFO).show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.info_socorrista_no), Style.INFO).show();
                    }
                }
            });

            return rootView;
        }

    private void setIconsExtraInfo (Playa playa){

        if ((playa.banderaazul != null) && (playa.banderaazul)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_si), banderaazulIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), banderaazulIV, Utilities.options, animateFirstListener);
        }

        if (playa.dificultadacceso != null){
            if (playa.dificultadacceso.equals("media")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_media), dificultadaccesoIV, Utilities.options, animateFirstListener);
            } else if (playa.dificultadacceso.equals("extrema")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_alta), dificultadaccesoIV, Utilities.options, animateFirstListener);
            } else {
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoIV, Utilities.options, animateFirstListener);
            }
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoIV, Utilities.options, animateFirstListener);
        }

        if (playa.limpieza != null){
            if (playa.limpieza.equals("sucia")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_sucia), limpiezaIV, Utilities.options, animateFirstListener);
            } else if (playa.limpieza.equals("mucho")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_limpia), limpiezaIV, Utilities.options, animateFirstListener);
            } else {
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaIV, Utilities.options, animateFirstListener);
            }
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaIV, Utilities.options, animateFirstListener);
        }

        if (playa.tipoarena != null){
            if (playa.tipoarena.equals("blanca")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_blanca), tipoarenaIV, Utilities.options, animateFirstListener);
            } else if (playa.tipoarena.equals("rocas")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_rocas), tipoarenaIV, Utilities.options, animateFirstListener);
            } else {
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaIV, Utilities.options, animateFirstListener);
            }
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaIV, Utilities.options, animateFirstListener);
        }

        if ((playa.rompeolas != null) && (playa.rompeolas)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_si), rompeolasIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), rompeolasIV, Utilities.options, animateFirstListener);
        }

        if ((playa.hamacas != null) && (playa.hamacas)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_si), hamacasIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), hamacasIV, Utilities.options, animateFirstListener);
        }

        if ((playa.sombrillas != null) && (playa.sombrillas)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_si), sombrillasIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), sombrillasIV, Utilities.options, animateFirstListener);
        }

        if ((playa.chiringuitos != null) && (playa.chiringuitos)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), chiringuitosIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_no), chiringuitosIV, Utilities.options, animateFirstListener);
        }

        if ((playa.duchas != null) && (playa.duchas)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_si), duchasIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), duchasIV, Utilities.options, animateFirstListener);
        }

        if ((playa.socorrista != null) && (playa.socorrista)) {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_si), socorristaIV, Utilities.options, animateFirstListener);
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), socorristaIV, Utilities.options, animateFirstListener);
        }
    }

    public void setValoracion(int valoracion){
        if (valoracion == 1){
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v2, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v3, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v4, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v5, Utilities.options, animateFirstListener);
        } else if (valoracion == 2){
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v2, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v3, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v4, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v5, Utilities.options, animateFirstListener);
        } else if (valoracion == 3){
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v2, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v3, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v4, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v5, Utilities.options, animateFirstListener);
        } else if (valoracion == 4){
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v2, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v3, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v4, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_off), v5, Utilities.options, animateFirstListener);
        } else if (valoracion == 5){
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v1, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v2, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v3, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v4, Utilities.options, animateFirstListener);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.star_on), v5, Utilities.options, animateFirstListener);
        }
    }

}
