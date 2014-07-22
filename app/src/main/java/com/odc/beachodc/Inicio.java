package com.odc.beachodc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.odc.beachodc.db.BBDD;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public class Inicio extends FragmentActivity {

    Context context;

    // Variables usadas para representar los fragments de Login y futuras otras pantallas de inicio.
    private static final int LOGIN = 0;
    private static final int FRAGMENT_COUNT = LOGIN+1;

    // Item del menu que permitira cerrar sesion en Facebook
    private MenuItem logout;

    // Variables para controlar los fragments, cual esta activo, etc.
    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
    private boolean isResumed = false;

    // Variables para controlar el ciclo de vida de la sesion de Facebook
    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Geo.activeGPSLocation(this);

        // Ciclo de vida de la sesion de autenticacion de Facebook
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        context = getApplicationContext();

        setContentView(R.layout.activity_inicio);

        FragmentManager fm = getSupportFragmentManager();
        fragments[LOGIN] = fm.findFragmentById(R.id.splashFragment);
        List<Fragment> fd= fm.getFragments();


        // Ocultamos todos los fragments que maneja nuestra activity para despues mostrarlo si procede o saltar a la siguiente activity.
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fragments[LOGIN]);
        transaction.commit();
        getActionBar().hide();

        //código para mostrar tu clave de desarrollador

        /*try {
            PackageInfo info = getPackageManager().getPackageInfo("com.odc.beachodc", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/

        BBDD.initBBDD(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        isResumed = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
        isResumed = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Session session = Session.getActiveSession();

        // Cada vez que la app se ponga en primer plano, comprobaremos si nuestra sesión de FB aun sigue vigente, para entrar directamente. Si ha caducado o hemos hecho logout, mostramos el fragment de Login.
        if (session != null && session.isOpened()) {
            goToHome(session);
        }  else {
            // otherwise present the splash screen and ask the user to login, unless the user explicitly skipped.
            showFragment(LOGIN, false);
        }
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        // Si hacemos login o logout, el estado de la sesion cambiará, por lo que en esta función se controla y se muestra el fragment correspondiente en funcion de si la sesión esta abierta o cerrada.
        if (isResumed) {
            FragmentManager manager = getSupportFragmentManager();
            int backStackSize = manager.getBackStackEntryCount();
            for (int i = 0; i < backStackSize; i++) {
                manager.popBackStack();
            }
            // check for the OPENED state instead of session.isOpened() since for the
            // OPENED_TOKEN_UPDATED state, the selection fragment should already be showing.
            if (state.equals(SessionState.OPENED)) {
                goToHome(session);
            } else if (state.isClosed()) {
                showFragment(LOGIN, false);
            }
        }
    }

    // Función que a partir del ID (0: Login), muestra el fragment correspondiente, ocultando el resto.
    private void showFragment(int fragmentIndex, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.show(fragments[LOGIN]);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    // Funcion encargada de redireccionar a la pantalla principal de usuario una vez logueado correctamente en Facebook
    public void goToHome(final Session session){
        // Obtenemos nuestros datos para crear el usuario que nos represente
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        // If the response is successfulHome
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                                Utilities.storeRegistrationId(context, user.getId(), user.getName());
                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
        // Empezar aqui a trabajar con la UI

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

}
