package com.odc.beachodc.fragments.edit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

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
import com.odc.beachodc.utilities.ValidacionPlaya;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class OnlyExtrasPlayaFragment extends Fragment {

        RadioGroup banderazul;
        RadioGroup dificultadacceso;
        RadioGroup limpieza;
        RadioGroup tipoarena;
        RadioGroup rompeolas;
        RadioGroup hamacas;
        RadioGroup sombrillas;
        RadioGroup duchas;
        RadioGroup socorrista;
        RadioGroup chiringuitos;
        GoogleMap mapa;

        public OnlyExtrasPlayaFragment() {
            // Se ejecuta antes que el onCreateView

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_edit_onlyextras, container, false);

            // Empezar aqui a trabajar con la UI

            banderazul = (RadioGroup) rootView.findViewById(R.id.banderaAzulRG);
            dificultadacceso = (RadioGroup) rootView.findViewById(R.id.dificultadAccesoRG);
            limpieza = (RadioGroup) rootView.findViewById(R.id.limpiezaRG);
            tipoarena = (RadioGroup) rootView.findViewById(R.id.tipoArenaRG);
            rompeolas = (RadioGroup) rootView.findViewById(R.id.rompeolasRG);
            hamacas = (RadioGroup) rootView.findViewById(R.id.hamacasRG);
            sombrillas = (RadioGroup) rootView.findViewById(R.id.sombrillasRG);
            chiringuitos = (RadioGroup) rootView.findViewById(R.id.chiringuitosRG);
            duchas = (RadioGroup) rootView.findViewById(R.id.duchasRG);
            socorrista = (RadioGroup) rootView.findViewById(R.id.socorristaRG);

            setExtras();

            try {
                MapsInitializer.initialize(getActivity());
                mapa = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud), 15.0f);
                mapa.moveCamera(cameraUpdate);
            } catch (GooglePlayServicesNotAvailableException e) {}

            banderazul.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.banderaAzul_si){
                        ValidacionPlaya.playa.banderaazul = true;
                    }else if (checkedId == R.id.banderaAzul_no){
                        ValidacionPlaya.playa.banderaazul = false;
                    }else if (checkedId == R.id.banderaAzul_nose){
                        ValidacionPlaya.playa.banderaazul = false;
                    }
                }
            });

            dificultadacceso.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.dificultadAcceso_facil){
                        ValidacionPlaya.playa.dificultadacceso = "facil";
                    }else if (checkedId == R.id.dificultadAcceso_moderada){
                        ValidacionPlaya.playa.dificultadacceso = "media";
                    }else if (checkedId == R.id.dificultadAcceso_extrema){
                        ValidacionPlaya.playa.dificultadacceso = "extrema";
                    }
                }
            });

            limpieza.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.limpieza_mucho){
                        ValidacionPlaya.playa.limpieza = "mucho";
                    }else if (checkedId == R.id.limpieza_normal){
                        ValidacionPlaya.playa.limpieza = "normal";
                    }else if (checkedId == R.id.limpieza_sucia){
                        ValidacionPlaya.playa.limpieza = "sucia";
                    }
                }
            });

            tipoarena.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.tipoArena_negra){
                        ValidacionPlaya.playa.tipoarena = "negra";
                    }else if (checkedId == R.id.tipoArena_blanca){
                        ValidacionPlaya.playa.tipoarena = "blanca";
                    }else if (checkedId == R.id.tipoArena_rocas){
                        ValidacionPlaya.playa.tipoarena = "rocas";
                    }
                }
            });

            chiringuitos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.chiringuitos_si){
                        ValidacionPlaya.playa.chiringuitos = true;
                    }else if (checkedId == R.id.chiringuitos_no){
                        ValidacionPlaya.playa.chiringuitos = false;
                    }else if (checkedId == R.id.chiringuitos_nose){
                        ValidacionPlaya.playa.chiringuitos = false;
                    }
                }
            });

            rompeolas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.rompeolas_si){
                        ValidacionPlaya.playa.rompeolas = true;
                    }else if (checkedId == R.id.rompeolas_no){
                        ValidacionPlaya.playa.rompeolas = false;
                    }else if (checkedId == R.id.rompeolas_nose){
                        ValidacionPlaya.playa.rompeolas = false;
                    }
                }
            });

            hamacas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.hamacas_si){
                        ValidacionPlaya.playa.hamacas = true;
                    }else if (checkedId == R.id.hamacas_no){
                        ValidacionPlaya.playa.hamacas = false;
                    }else if (checkedId == R.id.hamacas_nose){
                        ValidacionPlaya.playa.hamacas = false;
                    }
                }
            });

            sombrillas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.sombrillas_si){
                        ValidacionPlaya.playa.sombrillas = true;
                    }else if (checkedId == R.id.sombrillas_no){
                        ValidacionPlaya.playa.sombrillas = false;
                    }else if (checkedId == R.id.sombrillas_nose){
                        ValidacionPlaya.playa.sombrillas = false;
                    }
                }
            });

            duchas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.duchas_si){
                        ValidacionPlaya.playa.duchas = true;
                    }else if (checkedId == R.id.duchas_no){
                        ValidacionPlaya.playa.duchas = false;
                    }else if (checkedId == R.id.duchas_nose){
                        ValidacionPlaya.playa.duchas = false;
                    }
                }
            });

            socorrista.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.socorrista_si){
                        ValidacionPlaya.playa.socorrista = true;
                    }else if (checkedId == R.id.socorrista_no){
                        ValidacionPlaya.playa.socorrista = false;
                    }else if (checkedId == R.id.socorrista_nose){
                        ValidacionPlaya.playa.socorrista = false;
                    }
                }
            });

            return rootView;
        }

        private void setExtras (){

            if ((ValidacionPlaya.playa.banderaazul != null) && (ValidacionPlaya.playa.banderaazul))
                banderazul.check(R.id.banderaAzul_si);
            else
                banderazul.check(R.id.banderaAzul_no);

            if (ValidacionPlaya.playa.dificultadacceso != null){
                if (ValidacionPlaya.playa.dificultadacceso.equals("media")){
                    dificultadacceso.check(R.id.dificultadAcceso_moderada);
                } else if (ValidacionPlaya.playa.dificultadacceso.equals("extrema")){
                    dificultadacceso.check(R.id.dificultadAcceso_extrema);
                } else {
                    dificultadacceso.check(R.id.dificultadAcceso_facil);
                }
            } else {
                dificultadacceso.check(R.id.dificultadAcceso_facil);
            }

            if (ValidacionPlaya.playa.limpieza != null){
                if (ValidacionPlaya.playa.limpieza.equals("sucia")){
                    limpieza.check(R.id.limpieza_sucia);
                } else if (ValidacionPlaya.playa.limpieza.equals("mucho")){
                    limpieza.check(R.id.limpieza_mucho);
                } else {
                    limpieza.check(R.id.limpieza_normal);
                }
            } else {
                limpieza.check(R.id.limpieza_normal);
            }

            if (ValidacionPlaya.playa.tipoarena != null){
                if (ValidacionPlaya.playa.tipoarena.equals("blanca")){
                    tipoarena.check(R.id.tipoArena_blanca);
                } else if (ValidacionPlaya.playa.tipoarena.equals("rocas")){
                    tipoarena.check(R.id.tipoArena_rocas);
                } else {
                    tipoarena.check(R.id.tipoArena_negra);
                }
            } else {
                tipoarena.check(R.id.tipoArena_negra);
            }

            if ((ValidacionPlaya.playa.rompeolas != null) && (ValidacionPlaya.playa.rompeolas))
                rompeolas.check(R.id.rompeolas_si);
            else
                rompeolas.check(R.id.rompeolas_no);

            if ((ValidacionPlaya.playa.hamacas != null) && (ValidacionPlaya.playa.hamacas))
                hamacas.check(R.id.hamacas_si);
            else
                hamacas.check(R.id.hamacas_no);

            if ((ValidacionPlaya.playa.sombrillas != null) && (ValidacionPlaya.playa.sombrillas))
                sombrillas.check(R.id.sombrillas_si);
            else
                sombrillas.check(R.id.sombrillas_no);

            if ((ValidacionPlaya.playa.chiringuitos != null) && (ValidacionPlaya.playa.chiringuitos))
                chiringuitos.check(R.id.chiringuitos_si);
            else
                chiringuitos.check(R.id.chiringuitos_no);

            if ((ValidacionPlaya.playa.duchas != null) && (ValidacionPlaya.playa.duchas))
                duchas.check(R.id.duchas_si);
            else
                duchas.check(R.id.duchas_no);

            if ((ValidacionPlaya.playa.socorrista != null) && (ValidacionPlaya.playa.socorrista))
                socorrista.check(R.id.socorrista_si);
            else
                socorrista.check(R.id.socorrista_no);

        }

}
