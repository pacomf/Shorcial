package com.odc.beachodc.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.odc.beachodc.webservices.Request;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class MisDatosFragment extends Fragment {

        ListView listView;
        ArrayList<Playa> playas;
        PlayasAdapter playasAdapter;
        View rootView;
        RelativeLayout noCheckins;

        public MisDatosFragment() {
            // Se ejecuta antes que el onCreateView
            playas = new ArrayList<Playa>();
        }

        public void setPlayas(ArrayList<Playa> playas){
            this.playas = playas;
            if (this.listView != null) {
                playasAdapter = new PlayasAdapter(getActivity(), Utilities.orderByDateCheckins(playas), true);
                this.listView.setAdapter(playasAdapter);
                playasAdapter.notifyDataSetChanged();
            }
            if (noCheckins != null){
                if ((playas == null) || (playas.size() == 0)){
                    noCheckins.setVisibility(View.VISIBLE);
                } else {
                    noCheckins.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_misdatos, container, false);
            // Empezar aqui a trabajar con la UI

            ProfilePictureView foto = (ProfilePictureView) rootView.findViewById(R.id.profilePicture);
            TextView nombre = (TextView) rootView.findViewById(R.id.nombreUserTV);
            TextView titleCheckins = (TextView) rootView.findViewById(R.id.title_last_checkins);

            noCheckins = (RelativeLayout) rootView.findViewById(R.id.nocheckins);

            try {
                foto.setCropped(true);
                foto.setProfileId(Utilities.getUserIdFacebook(getActivity()));
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aSongforJenniferBold.ttf");
                nombre.setTypeface(tf);
                titleCheckins.setTypeface(tf);
                nombre.setText(Utilities.getUserNameFacebook(getActivity()));
            } catch (Exception e){}

            listView = (ListView) rootView.findViewById(R.id.listaPlayasUltimosCheckin);

            playasAdapter = new PlayasAdapter(getActivity(), Utilities.orderByDateCheckins(playas), true);
            if ((playas == null) || (playas.size() == 0)){
                noCheckins.setVisibility(View.VISIBLE);
            } else {
                noCheckins.setVisibility(View.GONE);
            }

            listView.setAdapter(playasAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), Playas.class);
                    Playa item = (Playa) listView.getItemAtPosition(i);
                    ValidacionPlaya.playa = item;
                    ValidacionPlaya.lanzadaVerPlaya = false;
                    ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                    pd.setIndeterminate(false);
                    pd.setCancelable(true);

                    Request.getTemp(getActivity(), item.latitud, item.longitud, pd, intent);

                    Request.getComentariosPlaya(getActivity(), item.idserver, pd, intent);
                    if (Geo.isNearToMe(item.latitud, item.longitud))
                        Request.getMensajesBotella(getActivity(), item.idserver, pd, intent);
                    else
                        ValidacionPlaya.cargadosMensajesPlaya=true;
                }
            });

            ImageView recargar = (ImageView) rootView.findViewById(R.id.recargarIV);

            recargar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utilities.haveInternet(getActivity())) {
                        ValidacionPlaya.cargadaPlayas= true;
                        ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                        pd.setIndeterminate(false);
                        pd.setCancelable(false);
                        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                if (ValidacionPlaya.playasCheckins != null) {
                                    setPlayas(ValidacionPlaya.playasCheckins);
                                    playasAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        Request.getUltimosCheckins(getActivity(), pd);
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.no_internet), Style.ALERT).show();
                    }
                }
            });

            return rootView;
        }

}
