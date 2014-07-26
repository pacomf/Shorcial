package com.odc.beachodc.fragments.edit;


import android.app.Activity;
import android.app.ProgressDialog;
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
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;
import com.odc.beachodc.webservices.Request;

import java.util.Date;

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
        Activity activity;
        String idPlaya;

        public MensajeBotellaPlayaFragment() {
            // Se ejecuta antes que el onCreateView
        }

        public void setParams(Activity activity, String idPlaya) {
            // Se ejecuta antes que el onCreateView
            this.activity = activity;
            this.idPlaya = idPlaya;
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
                    enviar.setClickable(false);
                    ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                    pd.setIndeterminate(false);
                    pd.setCancelable(false);
                    if (validacionMensaje()){
                        if (Utilities.haveInternet(getActivity())) {
                            // TODO: Para futuro: Parametros: aleatorio.isChecked()
                            MensajeBotella mensajeB = new MensajeBotella(Utilities.getUserIdFacebook(activity), Utilities.getUserNameFacebook(activity), idPlaya, idPlaya, ValidacionPlaya.playa.nombre, new Date(), mensaje.getText().toString());
                            Request.mensajeBotellaPlaya(activity, mensajeB, pd);
                            enviar.setClickable(true);
                        } else {
                            pd.dismiss();
                            Crouton.makeText(getActivity(), getString(R.string.no_internet), Style.ALERT).show();
                            enviar.setClickable(true);
                        }
                    } else {
                        pd.dismiss();
                        enviar.setClickable(true);
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
