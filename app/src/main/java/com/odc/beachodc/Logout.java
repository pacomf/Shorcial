package com.odc.beachodc;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.odc.beachodc.utilities.Utilities;

public class Logout extends FragmentActivity {

    Fragment logout;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ciclo de vida de la sesion de autenticacion de Facebook
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logout);

        FragmentManager fm = getSupportFragmentManager();
        logout = fm.findFragmentById(R.id.logOutFragment);

        // Ocultamos todos los fragments que maneja nuestra activity para despues mostrarlo si procede o saltar a la siguiente activity.
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.show(logout);
        transaction.commit();
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
            // Si esta abierta la sesion no hacer nada
        }  else {
            // Si no está abierta, está cerrada, redirigir a Login
            goToLogin();
        }
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        // Si hacemos login o logout, el estado de la sesion cambiará, por lo que en esta función se controla y se muestra el fragment correspondiente en funcion de si la sesión esta abierta o cerrada.
        if (isResumed) {
            if (state.equals(SessionState.OPENED)) {
                // Si esta abierta la sesion no hacer nada
            } else if (state.isClosed()) {
                // Si no está abierta, está cerrada, redirigir a Login
                goToLogin();
            }
        }
    }


    // Funcion encargada de redireccionar a la pantalla de login tras el cierre de sesion de usuario en Facebook
    public void goToLogin(){
        Intent intent = new Intent(this, Inicio.class);
        // Para borrar el historial del botón atrás y no permitir hacer nada en eso boton ya que nos hemos deslogueado
        Utilities.storeRegistrationId(this, "", "");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}