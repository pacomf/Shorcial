package com.odc.beachodc.fragments.edit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.odc.beachodc.R;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class MapPlayaFragment extends Fragment {

        View rootView;

        public MapPlayaFragment() {
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
                rootView = inflater.inflate(R.layout.fragment_edit_mapplaya, container, false);
            } catch (InflateException e) {}

            // Empezar aqui a trabajar con la UI



            return rootView;
        }

}
