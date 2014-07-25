package com.odc.beachodc.fragments.edit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.odc.beachodc.R;
import com.odc.beachodc.utilities.AnimateFirstDisplayListener;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class InfoPlayaFragment extends Fragment {

        EditText nombre;
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

        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

        public InfoPlayaFragment() {
            // Se ejecuta antes que el onCreateView

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_edit_infoplaya, container, false);

            // Empezar aqui a trabajar con la UI

            nombre = (EditText) rootView.findViewById(R.id.nombreET);
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

            setExtras();

            nombre.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    ValidacionPlaya.playa.nombre = nombre.getText().toString();
                }
            });

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
                        ValidacionPlaya.playa.banderaazul = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), banderazulI, animateFirstListener);
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
                        ValidacionPlaya.playa.chiringuitos = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_no), chiringuitosI, animateFirstListener);
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
                        ValidacionPlaya.playa.rompeolas = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), rompeolasI, animateFirstListener);
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
                        ValidacionPlaya.playa.hamacas = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), hamacasI, animateFirstListener);
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
                        ValidacionPlaya.playa.sombrillas = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), sombrillasI, animateFirstListener);
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
                        ValidacionPlaya.playa.duchas = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), duchasI, animateFirstListener);
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
                        ValidacionPlaya.playa.socorrista = false;
                        Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), socorristaI, animateFirstListener);
                    }
                }
            });

            return rootView;
        }

    private void setExtras (){

        if ((ValidacionPlaya.playa.banderaazul != null) && (ValidacionPlaya.playa.banderaazul)) {
            banderazul.check(R.id.banderaAzul_si);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_si), banderazulI, animateFirstListener);
        } else {
            banderazul.check(R.id.banderaAzul_no);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), banderazulI, animateFirstListener);
        }

        if (ValidacionPlaya.playa.dificultadacceso != null){
            if (ValidacionPlaya.playa.dificultadacceso.equals("media")){
                dificultadacceso.check(R.id.dificultadAcceso_moderada);
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_media), dificultadaccesoI, animateFirstListener);
            } else if (ValidacionPlaya.playa.dificultadacceso.equals("extrema")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_alta), dificultadaccesoI, animateFirstListener);
                dificultadacceso.check(R.id.dificultadAcceso_extrema);
            } else {
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoI, animateFirstListener);
                dificultadacceso.check(R.id.dificultadAcceso_facil);
            }
        } else {
            dificultadacceso.check(R.id.dificultadAcceso_facil);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoI, animateFirstListener);
        }

        if (ValidacionPlaya.playa.limpieza != null){
            if (ValidacionPlaya.playa.limpieza.equals("sucia")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_sucia), limpiezaI, animateFirstListener);
                limpieza.check(R.id.limpieza_sucia);
            } else if (ValidacionPlaya.playa.limpieza.equals("mucho")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_limpia), limpiezaI, animateFirstListener);
                limpieza.check(R.id.limpieza_mucho);
            } else {
                limpieza.check(R.id.limpieza_normal);
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaI, animateFirstListener);
            }
        } else {
            limpieza.check(R.id.limpieza_normal);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaI, animateFirstListener);
        }

        if (ValidacionPlaya.playa.tipoarena != null){
            if (ValidacionPlaya.playa.tipoarena.equals("blanca")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_blanca), tipoarenaI, animateFirstListener);
                tipoarena.check(R.id.tipoArena_blanca);
            } else if (ValidacionPlaya.playa.tipoarena.equals("rocas")){
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_rocas), tipoarenaI, animateFirstListener);;
                tipoarena.check(R.id.tipoArena_rocas);
            } else {
                Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaI, animateFirstListener);
                tipoarena.check(R.id.tipoArena_negra);
            }
        } else {
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaI, animateFirstListener);
            tipoarena.check(R.id.tipoArena_negra);
        }

        if ((ValidacionPlaya.playa.rompeolas != null) && (ValidacionPlaya.playa.rompeolas)) {
            rompeolas.check(R.id.rompeolas_si);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_si), rompeolasI, animateFirstListener);
        } else {
            rompeolas.check(R.id.rompeolas_no);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), rompeolasI, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.hamacas != null) && (ValidacionPlaya.playa.hamacas)) {
            hamacas.check(R.id.hamacas_si);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_si), hamacasI, animateFirstListener);
        } else {
            hamacas.check(R.id.hamacas_no);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), hamacasI, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.sombrillas != null) && (ValidacionPlaya.playa.sombrillas)) {
            sombrillas.check(R.id.sombrillas_si);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_si), sombrillasI, animateFirstListener);
        } else {
            sombrillas.check(R.id.sombrillas_no);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), sombrillasI, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.chiringuitos != null) && (ValidacionPlaya.playa.chiringuitos)) {
            chiringuitos.check(R.id.chiringuitos_si);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), chiringuitosI, animateFirstListener);
        } else {
            chiringuitos.check(R.id.chiringuitos_no);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), chiringuitosI, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.duchas != null) && (ValidacionPlaya.playa.duchas)) {
            duchas.check(R.id.duchas_si);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_si), duchasI, animateFirstListener);
        } else {
            duchas.check(R.id.duchas_no);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), duchasI, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.socorrista != null) && (ValidacionPlaya.playa.socorrista)) {
            socorrista.check(R.id.socorrista_si);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_si), socorristaI, animateFirstListener);
        } else {
            socorrista.check(R.id.socorrista_no);
            Utilities.imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), socorristaI, animateFirstListener);
        }

    }

}
