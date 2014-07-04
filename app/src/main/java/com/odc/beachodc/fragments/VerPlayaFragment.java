package com.odc.beachodc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.odc.beachodc.R;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class VerPlayaFragment extends Fragment {

        ListView listView;

        public VerPlayaFragment() {
            // Se ejecuta antes que el onCreateView

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ver_playa, container, false);
            // Empezar aqui a trabajar con la UI



            return rootView;
        }

}
