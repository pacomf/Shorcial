package com.odc.beachodc.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.odc.beachodc.R;
import com.odc.beachodc.interfaces.IStandardTaskListener;
import com.odc.beachodc.utilities.AnimateFirstDisplayListener;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;
import com.odc.beachodc.utilities.placeAutocomplete.DetailsPlaceOne;
import com.odc.beachodc.utilities.placeAutocomplete.FillPlace;

import java.util.List;
import java.util.Locale;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class BuscarPlayaFragmentExtras extends Fragment {

        View rootView;
        Button buscarBTN;

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
        RadioGroup perros;
        RadioGroup nudista;
        RadioGroup cerrada;

        ImageView banderazulI;
        ImageView dificultadaccesoI;
        ImageView limpiezaI;
        ImageView tipoarenaI;
        ImageView rompeolasI;
        ImageView hamacasI;
        ImageView sombrillasI;
        ImageView duchasI;
        ImageView socorristaI;
        ImageView chiringuitosI;
        ImageView perrosI;
        ImageView nudistaI;
        ImageView cerradaI;

        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

        public BuscarPlayaFragmentExtras() {
            // Se ejecuta antes que el onCreateView

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // Para evitar problemas al inflar Layout con Map Fragments... y reutilizar mejor los fragments
            if (rootView != null) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (parent != null)
                    parent.removeView(rootView);
            }
            try {
                rootView = inflater.inflate(R.layout.fragment_buscar_playas_extras, container, false);
            } catch (InflateException e) {}

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
            perros = (RadioGroup) rootView.findViewById(R.id.perrosRG);
            nudista = (RadioGroup) rootView.findViewById(R.id.nudistaRG);
            cerrada = (RadioGroup) rootView.findViewById(R.id.cerradaRG);

            banderazulI = (ImageView) rootView.findViewById(R.id.banderaAzulImage);
            dificultadaccesoI = (ImageView) rootView.findViewById(R.id.dificultadAccesoImage);
            limpiezaI = (ImageView) rootView.findViewById(R.id.limpiezaImage);
            tipoarenaI = (ImageView) rootView.findViewById(R.id.tipoArenaImage);
            rompeolasI = (ImageView) rootView.findViewById(R.id.rompeolasImage);
            hamacasI = (ImageView) rootView.findViewById(R.id.hamacasImage);
            sombrillasI = (ImageView) rootView.findViewById(R.id.sombrillasImage);
            chiringuitosI = (ImageView) rootView.findViewById(R.id.chiringuitosImage);
            duchasI = (ImageView) rootView.findViewById(R.id.duchasImage);
            socorristaI = (ImageView) rootView.findViewById(R.id.socorristaImage);
            perrosI = (ImageView) rootView.findViewById(R.id.perrosImage);
            nudistaI = (ImageView) rootView.findViewById(R.id.nudistaImage);
            cerradaI = (ImageView) rootView.findViewById(R.id.cerradaImage);

            setExtras();

            banderazul.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.banderaAzul_si){
                        ValidacionPlaya.playa.banderaazul = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_si), banderazulI, animateFirstListener);
                    }else if (checkedId == R.id.banderaAzul_no){
                        ValidacionPlaya.playa.banderaazul = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), banderazulI, animateFirstListener);
                    }else if (checkedId == R.id.banderaAzul_nose){
                        ValidacionPlaya.playa.banderaazul = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), banderazulI, animateFirstListener);
                    }
                }
            });

            dificultadacceso.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.dificultadAcceso_facil){
                        ValidacionPlaya.playa.dificultadacceso = "facil";
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoI, animateFirstListener);
                    }else if (checkedId == R.id.dificultadAcceso_moderada){
                        ValidacionPlaya.playa.dificultadacceso = "media";
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_media), dificultadaccesoI, animateFirstListener);
                    }else if (checkedId == R.id.dificultadAcceso_extrema){
                        ValidacionPlaya.playa.dificultadacceso = "extrema";
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_alta), dificultadaccesoI, animateFirstListener);
                    }else if (checkedId == R.id.dificultadAcceso_nose){
                        ValidacionPlaya.playa.dificultadacceso = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), dificultadaccesoI, animateFirstListener);
                    }
                }
            });

            limpieza.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.limpieza_mucho){
                        ValidacionPlaya.playa.limpieza = "mucho";
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_limpia), limpiezaI, animateFirstListener);
                    }else if (checkedId == R.id.limpieza_normal){
                        ValidacionPlaya.playa.limpieza = "normal";
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaI, animateFirstListener);
                    }else if (checkedId == R.id.limpieza_sucia){
                        ValidacionPlaya.playa.limpieza = "sucia";
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_sucia), limpiezaI, animateFirstListener);
                    }else if (checkedId == R.id.limpieza_nose){
                        ValidacionPlaya.playa.limpieza = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), limpiezaI, animateFirstListener);
                    }
                }
            });

            tipoarena.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.tipoArena_negra){
                        ValidacionPlaya.playa.tipoarena = "negra";
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaI, animateFirstListener);
                    }else if (checkedId == R.id.tipoArena_blanca){
                        ValidacionPlaya.playa.tipoarena = "blanca";
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_blanca), tipoarenaI, animateFirstListener);
                    }else if (checkedId == R.id.tipoArena_rocas){
                        ValidacionPlaya.playa.tipoarena = "rocas";
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_rocas), tipoarenaI, animateFirstListener);;
                    }else if (checkedId == R.id.tipoArena_nose){
                        ValidacionPlaya.playa.tipoarena = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), tipoarenaI, animateFirstListener);
                    }
                }
            });

            chiringuitos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.chiringuitos_si){
                        ValidacionPlaya.playa.chiringuitos = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), chiringuitosI, animateFirstListener);
                    }else if (checkedId == R.id.chiringuitos_no){
                        ValidacionPlaya.playa.chiringuitos = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_no), chiringuitosI, animateFirstListener);
                    }else if (checkedId == R.id.chiringuitos_nose){
                        ValidacionPlaya.playa.chiringuitos = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), chiringuitosI, animateFirstListener);
                    }
                }
            });

            rompeolas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.rompeolas_si){
                        ValidacionPlaya.playa.rompeolas = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_si), rompeolasI, animateFirstListener);
                    }else if (checkedId == R.id.rompeolas_no){
                        ValidacionPlaya.playa.rompeolas = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), rompeolasI, animateFirstListener);
                    }else if (checkedId == R.id.rompeolas_nose){
                        ValidacionPlaya.playa.rompeolas = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), rompeolasI, animateFirstListener);
                    }
                }
            });

            hamacas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.hamacas_si){
                        ValidacionPlaya.playa.hamacas = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_si), hamacasI, animateFirstListener);
                    }else if (checkedId == R.id.hamacas_no){
                        ValidacionPlaya.playa.hamacas = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), hamacasI, animateFirstListener);
                    }else if (checkedId == R.id.hamacas_nose){
                        ValidacionPlaya.playa.hamacas = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), hamacasI, animateFirstListener);
                    }
                }
            });

            sombrillas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.sombrillas_si){
                        ValidacionPlaya.playa.sombrillas = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_si), sombrillasI, animateFirstListener);
                    }else if (checkedId == R.id.sombrillas_no){
                        ValidacionPlaya.playa.sombrillas = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), sombrillasI, animateFirstListener);
                    }else if (checkedId == R.id.sombrillas_nose){
                        ValidacionPlaya.playa.sombrillas = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), sombrillasI, animateFirstListener);
                    }
                }
            });

            duchas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.duchas_si){
                        ValidacionPlaya.playa.duchas = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_si), duchasI, animateFirstListener);
                    }else if (checkedId == R.id.duchas_no){
                        ValidacionPlaya.playa.duchas = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), duchasI, animateFirstListener);
                    }else if (checkedId == R.id.duchas_nose){
                        ValidacionPlaya.playa.duchas = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), duchasI, animateFirstListener);
                    }
                }
            });

            socorrista.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.socorrista_si){
                        ValidacionPlaya.playa.socorrista = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_si), socorristaI, animateFirstListener);
                    }else if (checkedId == R.id.socorrista_no){
                        ValidacionPlaya.playa.socorrista = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), socorristaI, animateFirstListener);
                    }else if (checkedId == R.id.socorrista_nose){
                        ValidacionPlaya.playa.socorrista = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), socorristaI, animateFirstListener);
                    }
                }
            });

            perros.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.perros_si){
                        ValidacionPlaya.playa.perros = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.perros_si), perrosI, animateFirstListener);
                    }else if (checkedId == R.id.perros_no){
                        ValidacionPlaya.playa.perros = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.perros_no), perrosI, animateFirstListener);
                    }else if (checkedId == R.id.perros_nose){
                        ValidacionPlaya.playa.perros = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), perrosI, animateFirstListener);
                    }
                }
            });

            nudista.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.nudista_si){
                        ValidacionPlaya.playa.nudista = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.nudista_si), nudistaI, animateFirstListener);
                    }else if (checkedId == R.id.nudista_no){
                        ValidacionPlaya.playa.nudista = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.nudista_no), nudistaI, animateFirstListener);
                    }else if (checkedId == R.id.nudista_nose){
                        ValidacionPlaya.playa.nudista = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), nudistaI, animateFirstListener);
                    }
                }
            });

            cerrada.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.cerrada_si){
                        ValidacionPlaya.playa.cerrada = true;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.cerrada), cerradaI, animateFirstListener);
                    }else if (checkedId == R.id.cerrada_no){
                        ValidacionPlaya.playa.cerrada = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.checkin), cerradaI, animateFirstListener);
                    }else if (checkedId == R.id.cerrada_nose){
                        ValidacionPlaya.playa.cerrada = null;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), cerradaI, animateFirstListener);
                    }
                }
            });

            buscarBTN = (Button) rootView.findViewById(R.id.buscarBTN);

            buscarBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.buscarPlaya (ValidacionPlaya.busqueda, ValidacionPlaya.nombrePlaya, getActivity(), ValidacionPlaya.porCercania, ValidacionPlaya.direccion, ValidacionPlaya.playa);
                }
            });

            return rootView;

        }

    private void setExtras (){

        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), banderazulI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), dificultadaccesoI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), limpiezaI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), tipoarenaI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), chiringuitosI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), rompeolasI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), hamacasI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), sombrillasI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), duchasI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), socorristaI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), perrosI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), nudistaI, animateFirstListener);
        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(android.R.drawable.ic_menu_help), cerradaI, animateFirstListener);

    }

}
