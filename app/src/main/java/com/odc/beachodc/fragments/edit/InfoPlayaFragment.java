package com.odc.beachodc.fragments.edit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.odc.beachodc.R;
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

}
