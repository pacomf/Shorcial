package com.odc.beachodc.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.odc.beachodc.Home;
import com.odc.beachodc.R;
import com.odc.beachodc.activities.BuscarPlaya;
import com.odc.beachodc.activities.EdicionPlaya;
import com.odc.beachodc.db.models.Checkin;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.fragments.ImagenesPlayaFragment;
import com.odc.beachodc.fragments.MisDatosFragment;
import com.odc.beachodc.fragments.PlayaDirectoFragment;
import com.odc.beachodc.fragments.VerPlayaFragment;
import com.odc.beachodc.fragments.edit.MensajeBotellaPlayaFragment;
import com.odc.beachodc.fragments.list.MensajesBotellasFragment;
import com.odc.beachodc.fragments.list.ValoracionesFragment;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;
import com.odc.beachodc.webservices.Request;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class Playas extends FragmentActivity implements ActionBar.TabListener {

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
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    boolean hayWebCam, isNear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //opening transition animations

        if (ValidacionPlaya.playa == null){
            Intent intent = new Intent(this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);

        setContentView(R.layout.activity_home);

        if ((ValidacionPlaya.playa.webcamURL != null) && (!ValidacionPlaya.playa.webcamURL.equals(""))) {
            hayWebCam = true;
        }

        if (Geo.isNearToMe(ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud)) {
            isNear = true;
        }

        Utilities.setActionBarCustomize(this);
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
            Boolean mensajebotellacreado = getIntent().getExtras().getBoolean("nuevomensajebotella");
            if ((mensajebotellacreado != null) && (mensajebotellacreado)){
                Crouton.makeText(this, getString(R.string.nuevomensajebotella), Style.CONFIRM).show();
            } else {
                Boolean nuevavaloracion = getIntent().getExtras().getBoolean("nuevavaloracion");
                if ((nuevavaloracion != null) && (nuevavaloracion)) {
                    Crouton.makeText(this, getString(R.string.nuevavaloracion), Style.CONFIRM).show();
                } else {
                    Boolean peticionBorrado = getIntent().getExtras().getBoolean("peticionborrado");
                    if ((peticionBorrado != null) && (peticionBorrado)) {
                        Crouton.makeText(this, getString(R.string.peticionborrado), Style.CONFIRM).show();
                    }
                }
            }
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.playas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Si se ha clickado en la opcion de cerrar sesión del menu, mostraremos el fragment que nos permitirá cerrar la sesión.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_editar:
                if (Utilities.haveInternet(this)) {
                    Intent intent = new Intent(this, EdicionPlaya.class);
                    intent.putExtra("nuevo", false);
                    startActivity(intent);
                } else {
                    Crouton.makeText(this, getString(R.string.no_internet), Style.ALERT).show();
                }
                return true;
            case R.id.menu_checkin:
                if (Utilities.haveInternet(this)) {
                    if (isNear) {
                        ProgressDialog pd = ProgressDialog.show(this, getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                        pd.setIndeterminate(false);
                        pd.setCancelable(true);
                        Request.nuevoCheckinPlaya(this, new Checkin(ValidacionPlaya.playa.idserver, new Date(), Utilities.getUserIdFacebook(this)), pd);
                    } else {
                        Crouton.makeText(this, R.string.nocheckin, Style.ALERT).show();
                    }
                } else {
                    Crouton.makeText(this, getString(R.string.no_internet), Style.ALERT).show();
                }
                return true;
            case android.R.id.home:
                Intent intentH = new Intent(this, Home.class);
                // Para eliminar el historial de activities visitadas ya que volvemos al HOME y asi el boton ATRAS no tenga ningun comportamiento, se resetee.
                intentH.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentH);
                finish();
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
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if ((hayWebCam) && (isNear)){
                switch (position) {
                    case 0:
                        return new VerPlayaFragment();
                    case 1:
                        return new PlayaDirectoFragment();
                    case 2:
                        return new ValoracionesFragment();
                    case 3:
                        return new ImagenesPlayaFragment();
                    case 4:
                        return new MensajesBotellasFragment();
                }
            } else if (isNear) {
                switch (position) {
                    case 0:
                        return new VerPlayaFragment();
                    case 1:
                        return new ValoracionesFragment();
                    case 2:
                        return new ImagenesPlayaFragment();
                    case 3:
                        return new MensajesBotellasFragment();
                }
            } else if (hayWebCam) {
                switch (position) {
                    case 0:
                        return new VerPlayaFragment();
                    case 1:
                        return new PlayaDirectoFragment();
                    case 2:
                        return new ValoracionesFragment();
                    case 3:
                        return new ImagenesPlayaFragment();
                }
            } else {
                switch (position) {
                    case 0:
                        return new VerPlayaFragment();
                    case 1:
                        return new ValoracionesFragment();
                    case 2:
                        return new ImagenesPlayaFragment();
                }
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if ((hayWebCam) && (isNear))
                return 5;
            else if ((hayWebCam) || (isNear))
                return 4;
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            if ((hayWebCam) && (isNear)){
                switch (position) {
                    case 0:
                        return getString(R.string.title_section_see_beach).toUpperCase(l);
                    case 1:
                        return getString(R.string.title_section_webcam_beach).toUpperCase(l);
                    case 2:
                        return getString(R.string.title_section_opinion_beach).toUpperCase(l);
                    case 3:
                        return getString(R.string.title_section_images_beach).toUpperCase(l);
                    case 4:
                        return getString(R.string.title_section_descubre_beach).toUpperCase(l);

                }
            } else if (isNear) {
                switch (position) {
                    case 0:
                        return getString(R.string.title_section_see_beach).toUpperCase(l);
                    case 2:
                        return getString(R.string.title_section_opinion_beach).toUpperCase(l);
                    case 3:
                        return getString(R.string.title_section_images_beach).toUpperCase(l);
                    case 4:
                        return getString(R.string.title_section_descubre_beach).toUpperCase(l);
                }
            } else if (hayWebCam) {
                switch (position) {
                    case 0:
                        return getString(R.string.title_section_see_beach).toUpperCase(l);
                    case 1:
                        return getString(R.string.title_section_webcam_beach).toUpperCase(l);
                    case 2:
                        return getString(R.string.title_section_opinion_beach).toUpperCase(l);
                    case 3:
                        return getString(R.string.title_section_images_beach).toUpperCase(l);
                }
            } else {
                switch (position) {
                    case 0:
                        return getString(R.string.title_section_see_beach).toUpperCase(l);
                    case 1:
                        return getString(R.string.title_section_opinion_beach).toUpperCase(l);
                    case 2:
                        return getString(R.string.title_section_images_beach).toUpperCase(l);
                }
            }
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
        System.gc();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Runtime.getRuntime().gc();
    }


}
