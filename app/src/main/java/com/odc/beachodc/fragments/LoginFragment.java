package com.odc.beachodc.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.odc.beachodc.Home;
import com.odc.beachodc.R;
import com.odc.beachodc.utilities.Utilities;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class LoginFragment extends Fragment {

        public LoginFragment() {
            // Se ejecuta antes que el onCreateView

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            // Empezar aqui a trabajar con la UI

            TextView login_anonymous = (TextView) rootView.findViewById(R.id.login_button_anonymous);
            login_anonymous.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
            login_anonymous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.storeRegistrationId(getActivity(), "", "");
                    Intent intent = new Intent(getActivity(), Home.class);
                    startActivity(intent);
                }
            });

            return rootView;
        }
}
