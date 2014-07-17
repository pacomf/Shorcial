package com.odc.beachodc.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.odc.beachodc.Playas;
import com.odc.beachodc.R;
import com.odc.beachodc.activities.EdicionPlaya;
import com.odc.beachodc.adapters.PlayasAdapter;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class MisDatosFragment extends Fragment {

        ListView listView;
        ArrayList<Playa> playas;
        PlayasAdapter playasAdapter;

        public MisDatosFragment() {
            // Se ejecuta antes que el onCreateView
            playas = new ArrayList<Playa>();
        }

        public void setPlayas(ArrayList<Playa> playas){
            this.playas = playas;
            playasAdapter = new PlayasAdapter(getActivity(), playas, true);
            listView.setAdapter(playasAdapter);
            playasAdapter.notifyDataSetChanged();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_misdatos, container, false);
            // Empezar aqui a trabajar con la UI

            ProfilePictureView foto = (ProfilePictureView) rootView.findViewById(R.id.profilePicture);
            TextView nombre = (TextView) rootView.findViewById(R.id.nombreUserTV);
            TextView titleCheckins = (TextView) rootView.findViewById(R.id.title_last_checkins);

            try {
                foto.setCropped(true);
                foto.setProfileId(Utilities.getUserIdFacebook(getActivity()));
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aSongforJenniferBold.ttf");
                nombre.setTypeface(tf);
                titleCheckins.setTypeface(tf);
                nombre.setText(Utilities.getUserNameFacebook(getActivity()));
            } catch (Exception e){}

            listView = (ListView) rootView.findViewById(R.id.listaPlayasUltimosCheckin);

            PlayasAdapter playasAdapter = new PlayasAdapter(getActivity(), playas, true);
            listView.setAdapter(playasAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // TODO: Redirigir a la pagina de VER PLAYA, ahora se esta redirigiendo a la de Editar
                    Intent intent = new Intent(getActivity(), Playas.class);
                    Playa item = (Playa) listView.getItemAtPosition(i);
                    ValidacionPlaya.playa = item;
                    startActivity(intent);
                }
            });

            return rootView;
        }

}
