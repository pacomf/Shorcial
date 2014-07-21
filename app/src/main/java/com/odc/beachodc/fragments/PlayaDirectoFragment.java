package com.odc.beachodc.fragments;


import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.odc.beachodc.R;
import com.odc.beachodc.activities.ValoracionPlaya;
import com.odc.beachodc.utilities.BitmapLruCache;
import com.odc.beachodc.utilities.ValidacionPlaya;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class PlayaDirectoFragment extends Fragment {

    View rootView;

    public PlayaDirectoFragment() {
        // Se ejecuta antes que el onCreateView

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_directo_playa, container, false);
        } catch (InflateException e) {}

        // Empezar aqui a trabajar con la UI

        if (!ValidacionPlaya.playa.webcamURL.equals("")) {
            NetworkImageView webcam = (NetworkImageView) rootView.findViewById(R.id.webcamView);
            ImageLoader.ImageCache imageCache = new BitmapLruCache();
            ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getActivity()), imageCache);
            webcam.setImageUrl(ValidacionPlaya.playa.webcamURL,imageLoader);

        }
        return rootView;
    }

}
