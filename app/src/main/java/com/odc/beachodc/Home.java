package com.odc.beachodc;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.odc.beachodc.activities.BuscarPlaya;
import com.odc.beachodc.activities.EdicionPlaya;
import com.odc.beachodc.activities.LocationActivity;
import com.odc.beachodc.db.BBDD;
import com.odc.beachodc.db.models.Checkin;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.fragments.MisDatosFragment;
import com.odc.beachodc.fragments.list.PlayasFragment;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;
import com.odc.beachodc.webservices.Config;
import com.odc.beachodc.webservices.Request;
import com.odc.beachodc.webservices.image.UploadToImgurTask;

import org.json.JSONArray;

import java.util.Date;
import java.util.Locale;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class Home extends LocationActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    PlayasFragment playaFragment;
    MisDatosFragment misDatosFragment;
    Activity activity;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Style_beach);
        Utilities.setActionBarCustomize(this);
        super.onCreate(savedInstanceState);

        //opening transition animations
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);

        setContentView(R.layout.activity_home);

        activity = this;

        playaFragment = new PlayasFragment();
        misDatosFragment = new MisDatosFragment();

        pd = ProgressDialog.show(this, getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
        pd.setIndeterminate(false);
        pd.setCancelable(false);

        ValidacionPlaya.cargadaPlayas=false;
        ValidacionPlaya.cargadosUltimosCheckins=false;

        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (ValidacionPlaya.playas != null) {
                    playaFragment.setPlayas(ValidacionPlaya.playas);
                }
                if (ValidacionPlaya.playasCheckins != null){
                    misDatosFragment.setPlayas(ValidacionPlaya.playasCheckins);
                }
                if (Geo.myLocation == null){
                    Geo.checkGPSandAlert(activity);
                }
            }
        });

        if (Utilities.haveInternet(this)) {
            if (Geo.myLocation == null) {
                // Esperamos 3 segundos para ver si pilla GEOLocalizacion
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(4000);
                            }
                        } catch (InterruptedException ex) {
                        }
                        Request.getPlayasCercanas(activity, pd);
                        if (!Utilities.isAnonymous(activity)){
                            Request.getUltimosCheckins(activity, pd);
                        } else {
                            ValidacionPlaya.cargadosUltimosCheckins=true;
                            if ((ValidacionPlaya.cargadaPlayas) && (pd != null) && (pd.isShowing())) {
                                pd.dismiss();
                            }
                        }

                    }
                };
                thread.start();
            } else {
                Request.getPlayasCercanas(this, pd);
                if (!Utilities.isAnonymous(this)){
                    Request.getUltimosCheckins(this, pd);
                } else {
                    ValidacionPlaya.cargadosUltimosCheckins=true;
                    if ((ValidacionPlaya.cargadaPlayas) && (pd != null) && (pd.isShowing())) {
                        pd.dismiss();
                    }
                }
            }
        } else {
            pd.dismiss();
            Crouton.makeText(this, getString(R.string.no_internet), Style.ALERT).show();
        }

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }

        // Muestra un mensajito si hemos sido redirigidos a este Activity a través de la creación, con éxito, de una Playa
        if (getIntent().getExtras() != null){
            Boolean playacreada = getIntent().getExtras().getBoolean("creaplaya");
            if ((playacreada != null) && (playacreada)){
                Crouton.makeText(this, getString(R.string.playacreada), Style.CONFIRM).show();
            } else {
                Boolean playaeditada = getIntent().getExtras().getBoolean("editaplaya");
                if ((playaeditada != null) && (playaeditada)){
                    Crouton.makeText(this, getString(R.string.playaeditada), Style.CONFIRM).show();
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Si se ha clickado en la opcion de cerrar sesión del menu, mostraremos el fragment que nos permitirá cerrar la sesión.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_logout:
                if (Utilities.isAnonymous(this)) {
                    Crouton.makeText(activity, activity.getString(R.string.need_login), Style.ALERT).show();
                } else {
                    if (Utilities.haveInternet(this)) {
                        Intent intent = new Intent(this, Logout.class);
                        startActivity(intent);
                    } else {
                        Crouton.makeText(this, getString(R.string.no_internet), Style.ALERT).show();
                    }
                }
                return true;

            case R.id.menu_nuevo:
                if (Utilities.isAnonymous(this)){
                    Utilities.goToLoginAsking(this);
                } else {
                    if (Utilities.haveInternet(this)) {
                        Intent intentN = new Intent(this, EdicionPlaya.class);
                        intentN.putExtra("nuevo", true);
                        startActivity(intentN);
                    } else {
                        Crouton.makeText(this, getString(R.string.no_internet), Style.ALERT).show();
                    }
                }
                return true;

            case R.id.menu_search:
                if (Utilities.haveInternet(this)) {
                    Intent intentS = new Intent(this, BuscarPlaya.class);
                    startActivity(intentS);
                } else {
                    Crouton.makeText(this, getString(R.string.no_internet), Style.ALERT).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    return misDatosFragment;
                case 1:
                    return playaFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section_mis_datos).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section_playas_cercanas).toUpperCase(l);
            }
            return null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
        System.gc();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
        System.gc();
        Runtime.getRuntime().gc();
    }



}
