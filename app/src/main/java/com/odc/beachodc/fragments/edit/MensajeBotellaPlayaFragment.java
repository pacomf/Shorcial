package com.odc.beachodc.fragments.edit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
public class MensajeBotellaPlayaFragment extends Fragment {

        View rootView;
        EditText mensaje;
        Button enviar;
        CheckBox aleatorio;

        public MensajeBotellaPlayaFragment() {
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
                rootView = inflater.inflate(R.layout.fragment_edit_mensaje_botella, container, false);
            } catch (InflateException e) {}

            // Empezar aqui a trabajar con la UI



            mensaje = (EditText) rootView.findViewById(R.id.mensajeET);

            enviar = (Button) rootView.findViewById(R.id.nuevoMensajeBTN);

            aleatorio = (CheckBox) rootView.findViewById(R.id.mensajeAleatorioCB);

            enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validacionMensaje()){
                        // TODO: Enviar al servidor y si responde positivamente retornar a la pagina de ver Playa
                        // TODO: En el Intent mandar un atributo para que cuando llegue al activity encuestion muestre un mensaje con que se ha añadido el mensaje en la botella
                        // Parametros: valoracion, mensaje.getText().toString(), aleatorio.isChecked(), new Date() y nombre e idFB de quien lo lanzó
                        Intent intent = new Intent(getActivity(), EdicionPlaya.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                }
            });

            return rootView;
        }



    public boolean validacionMensaje(){
        if (mensaje.getText().toString().equals("")){
            Crouton.makeText(getActivity(), R.string.error_mensaje, Style.ALERT).show();
            return false;
        }
        return true;
    }


}
