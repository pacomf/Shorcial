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

import com.nostra13.universalimageloader.core.ImageLoader;
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

        protected ImageLoader imageLoader = ImageLoader.getInstance();

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
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_si), banderazulI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.banderaAzul_no){
                        ValidacionPlaya.playa.banderaazul = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), banderazulI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.banderaAzul_nose){
                        ValidacionPlaya.playa.banderaazul = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), banderazulI, Utilities.options, animateFirstListener);
                    }
                }
            });

            dificultadacceso.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.dificultadAcceso_facil){
                        ValidacionPlaya.playa.dificultadacceso = "facil";
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.dificultadAcceso_moderada){
                        ValidacionPlaya.playa.dificultadacceso = "media";
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_media), dificultadaccesoI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.dificultadAcceso_extrema){
                        ValidacionPlaya.playa.dificultadacceso = "extrema";
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_alta), dificultadaccesoI, Utilities.options, animateFirstListener);
                    }
                }
            });

            limpieza.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.limpieza_mucho){
                        ValidacionPlaya.playa.limpieza = "mucho";
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_limpia), limpiezaI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.limpieza_normal){
                        ValidacionPlaya.playa.limpieza = "normal";
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.limpieza_sucia){
                        ValidacionPlaya.playa.limpieza = "sucia";
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_sucia), limpiezaI, Utilities.options, animateFirstListener);
                    }
                }
            });

            tipoarena.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.tipoArena_negra){
                        ValidacionPlaya.playa.tipoarena = "negra";
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.tipoArena_blanca){
                        ValidacionPlaya.playa.tipoarena = "blanca";
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_blanca), tipoarenaI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.tipoArena_rocas){
                        ValidacionPlaya.playa.tipoarena = "rocas";
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_rocas), tipoarenaI, Utilities.options, animateFirstListener);;
                    }
                }
            });

            chiringuitos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.chiringuitos_si){
                        ValidacionPlaya.playa.chiringuitos = true;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), chiringuitosI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.chiringuitos_no){
                        ValidacionPlaya.playa.chiringuitos = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_no), chiringuitosI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.chiringuitos_nose){
                        ValidacionPlaya.playa.chiringuitos = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_no), chiringuitosI, Utilities.options, animateFirstListener);
                    }
                }
            });

            rompeolas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.rompeolas_si){
                        ValidacionPlaya.playa.rompeolas = true;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_si), rompeolasI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.rompeolas_no){
                        ValidacionPlaya.playa.rompeolas = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), rompeolasI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.rompeolas_nose){
                        ValidacionPlaya.playa.rompeolas = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), rompeolasI, Utilities.options, animateFirstListener);
                    }
                }
            });

            hamacas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.hamacas_si){
                        ValidacionPlaya.playa.hamacas = true;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_si), hamacasI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.hamacas_no){
                        ValidacionPlaya.playa.hamacas = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), hamacasI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.hamacas_nose){
                        ValidacionPlaya.playa.hamacas = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), hamacasI, Utilities.options, animateFirstListener);
                    }
                }
            });

            sombrillas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.sombrillas_si){
                        ValidacionPlaya.playa.sombrillas = true;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_si), sombrillasI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.sombrillas_no){
                        ValidacionPlaya.playa.sombrillas = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), sombrillasI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.sombrillas_nose){
                        ValidacionPlaya.playa.sombrillas = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), sombrillasI, Utilities.options, animateFirstListener);
                    }
                }
            });

            duchas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.duchas_si){
                        ValidacionPlaya.playa.duchas = true;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_si), duchasI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.duchas_no){
                        ValidacionPlaya.playa.duchas = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), duchasI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.duchas_nose){
                        ValidacionPlaya.playa.duchas = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), duchasI, Utilities.options, animateFirstListener);
                    }
                }
            });

            socorrista.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.socorrista_si){
                        ValidacionPlaya.playa.socorrista = true;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_si), socorristaI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.socorrista_no){
                        ValidacionPlaya.playa.socorrista = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), socorristaI, Utilities.options, animateFirstListener);
                    }else if (checkedId == R.id.socorrista_nose){
                        ValidacionPlaya.playa.socorrista = false;
                        imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), socorristaI, Utilities.options, animateFirstListener);
                    }
                }
            });

            return rootView;
        }

    private void setExtras (){

        if ((ValidacionPlaya.playa.banderaazul != null) && (ValidacionPlaya.playa.banderaazul)) {
            banderazul.check(R.id.banderaAzul_si);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_si), banderazulI, Utilities.options, animateFirstListener);
        } else {
            banderazul.check(R.id.banderaAzul_no);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.bandera_azul_no), banderazulI, Utilities.options, animateFirstListener);
        }

        if (ValidacionPlaya.playa.dificultadacceso != null){
            if (ValidacionPlaya.playa.dificultadacceso.equals("media")){
                dificultadacceso.check(R.id.dificultadAcceso_moderada);
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_media), dificultadaccesoI, Utilities.options, animateFirstListener);
            } else if (ValidacionPlaya.playa.dificultadacceso.equals("extrema")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_alta), dificultadaccesoI, Utilities.options, animateFirstListener);
                dificultadacceso.check(R.id.dificultadAcceso_extrema);
            } else {
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoI, Utilities.options, animateFirstListener);
                dificultadacceso.check(R.id.dificultadAcceso_facil);
            }
        } else {
            dificultadacceso.check(R.id.dificultadAcceso_facil);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.dificultad_baja), dificultadaccesoI, Utilities.options, animateFirstListener);
        }

        if (ValidacionPlaya.playa.limpieza != null){
            if (ValidacionPlaya.playa.limpieza.equals("sucia")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_sucia), limpiezaI, Utilities.options, animateFirstListener);
                limpieza.check(R.id.limpieza_sucia);
            } else if (ValidacionPlaya.playa.limpieza.equals("mucho")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_limpia), limpiezaI, Utilities.options, animateFirstListener);
                limpieza.check(R.id.limpieza_mucho);
            } else {
                limpieza.check(R.id.limpieza_normal);
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaI, Utilities.options, animateFirstListener);
            }
        } else {
            limpieza.check(R.id.limpieza_normal);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.playa_medio_sucia), limpiezaI, Utilities.options, animateFirstListener);
        }

        if (ValidacionPlaya.playa.tipoarena != null){
            if (ValidacionPlaya.playa.tipoarena.equals("blanca")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_blanca), tipoarenaI, Utilities.options, animateFirstListener);
                tipoarena.check(R.id.tipoArena_blanca);
            } else if (ValidacionPlaya.playa.tipoarena.equals("rocas")){
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_rocas), tipoarenaI, Utilities.options, animateFirstListener);;
                tipoarena.check(R.id.tipoArena_rocas);
            } else {
                imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaI, Utilities.options, animateFirstListener);
                tipoarena.check(R.id.tipoArena_negra);
            }
        } else {
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.arena_negra), tipoarenaI, Utilities.options, animateFirstListener);
            tipoarena.check(R.id.tipoArena_negra);
        }

        if ((ValidacionPlaya.playa.rompeolas != null) && (ValidacionPlaya.playa.rompeolas)) {
            rompeolas.check(R.id.rompeolas_si);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_si), rompeolasI, Utilities.options, animateFirstListener);
        } else {
            rompeolas.check(R.id.rompeolas_no);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.rompeolas_no), rompeolasI, Utilities.options, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.hamacas != null) && (ValidacionPlaya.playa.hamacas)) {
            hamacas.check(R.id.hamacas_si);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_si), hamacasI, Utilities.options, animateFirstListener);
        } else {
            hamacas.check(R.id.hamacas_no);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.hamacas_no), hamacasI, Utilities.options, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.sombrillas != null) && (ValidacionPlaya.playa.sombrillas)) {
            sombrillas.check(R.id.sombrillas_si);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_si), sombrillasI, Utilities.options, animateFirstListener);
        } else {
            sombrillas.check(R.id.sombrillas_no);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.sombrillas_no), sombrillasI, Utilities.options, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.chiringuitos != null) && (ValidacionPlaya.playa.chiringuitos)) {
            chiringuitos.check(R.id.chiringuitos_si);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), chiringuitosI, Utilities.options, animateFirstListener);
        } else {
            chiringuitos.check(R.id.chiringuitos_no);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.chiringuito_si), chiringuitosI, Utilities.options, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.duchas != null) && (ValidacionPlaya.playa.duchas)) {
            duchas.check(R.id.duchas_si);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_si), duchasI, Utilities.options, animateFirstListener);
        } else {
            duchas.check(R.id.duchas_no);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.duchas_no), duchasI, Utilities.options, animateFirstListener);
        }

        if ((ValidacionPlaya.playa.socorrista != null) && (ValidacionPlaya.playa.socorrista)) {
            socorrista.check(R.id.socorrista_si);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_si), socorristaI, Utilities.options, animateFirstListener);
        } else {
            socorrista.check(R.id.socorrista_no);
            imageLoader.displayImage(Utilities.getURIDrawable(R.drawable.socorrista_no), socorristaI, Utilities.options, animateFirstListener);
        }

    }

}
