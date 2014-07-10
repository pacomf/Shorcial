package com.odc.beachodc.fragments.edit;


import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.odc.beachodc.R;
import com.odc.beachodc.activities.EdicionPlaya;


import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class ValoracionPlayaFragment extends Fragment {

        View rootView;
        ImageView v1, v2, v3, v4, v5;
        EditText comentario;
        Button enviar;
        int valoracion;

        public ValoracionPlayaFragment() {
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
                rootView = inflater.inflate(R.layout.fragment_edit_valoracion, container, false);
            } catch (InflateException e) {}

            // Empezar aqui a trabajar con la UI

            v1 = (ImageView) rootView.findViewById(R.id.v1);
            v2 = (ImageView) rootView.findViewById(R.id.v2);
            v3 = (ImageView) rootView.findViewById(R.id.v3);
            v4 = (ImageView) rootView.findViewById(R.id.v4);
            v5 = (ImageView) rootView.findViewById(R.id.v5);

            initValoracion();

            comentario = (EditText) rootView.findViewById(R.id.comentarioET);

            enviar = (Button) rootView.findViewById(R.id.nuevoComentarioBTN);

            enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validacionValoracion()){
                        // TODO: Enviar al servidor y si responde positivamente retornar a la pagina de ver Playa
                        // TODO: En el Intent mandar un atributo para que cuando llegue al activity encuestion muestre un mensaje con que se ha añadido la valoracion
                        // Parametros: valoracion y comentario.getText().toString() y new Date()
                        Intent intent = new Intent(getActivity(), EdicionPlaya.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                }
            });

            return rootView;
        }

    public void initValoracion(){

        v1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                valoracion=1;
                setValoracion(true, false, false, false, false);
            }
        });

        v2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                valoracion=2;
                setValoracion(false, true, false, false, false);
            }
        });

        v3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                valoracion=3;
                setValoracion(false, false, true, false, false);
            }
        });

        v4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                valoracion=4;
                setValoracion(false, false, false, true, false);
            }
        });

        v5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                valoracion=5;
                setValoracion(false, false, false, false, true);
            }
        });

    }

    public void setValoracion(boolean uno, boolean dos, boolean tres, boolean cuatro, boolean cinco){
        if (uno){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_off);
            v3.setImageResource(android.R.drawable.star_off);
            v4.setImageResource(android.R.drawable.star_off);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (dos){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_on);
            v3.setImageResource(android.R.drawable.star_off);
            v4.setImageResource(android.R.drawable.star_off);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (tres){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_on);
            v3.setImageResource(android.R.drawable.star_on);
            v4.setImageResource(android.R.drawable.star_off);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (cuatro){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_on);
            v3.setImageResource(android.R.drawable.star_on);
            v4.setImageResource(android.R.drawable.star_on);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (cinco){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_on);
            v3.setImageResource(android.R.drawable.star_on);
            v4.setImageResource(android.R.drawable.star_on);
            v5.setImageResource(android.R.drawable.star_on);
        }
    }

    public boolean validacionValoracion(){
        if (valoracion == 0){
            Crouton.makeText(getActivity(), R.string.error_valoracion, Style.ALERT).show();
            return false;
        }
        return true;
    }


}
