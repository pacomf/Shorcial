package com.odc.beachodc;

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

import com.odc.beachodc.activities.BuscarPlaya;
import com.odc.beachodc.activities.EdicionPlaya;
import com.odc.beachodc.activities.MensajesBotellasPlaya;
import com.odc.beachodc.db.models.Checkin;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.fragments.MisDatosFragment;
import com.odc.beachodc.fragments.PlayaDirectoFragment;
import com.odc.beachodc.fragments.VerPlayaFragment;
import com.odc.beachodc.fragments.edit.MensajeBotellaPlayaFragment;
import com.odc.beachodc.fragments.list.MensajesBotellasFragment;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;
import com.odc.beachodc.webservices.Request;

import java.util.Date;
import java.util.Locale;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Utilities.setActionBarCustomize(this);
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
                Intent intent = new Intent(this, EdicionPlaya.class);
                intent.putExtra("nuevo", false);
                startActivity(intent);
                return true;
            case R.id.menu_checkin:
                ProgressDialog pd = ProgressDialog.show(this, getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                pd.setIndeterminate(false);
                pd.setCancelable(true);
                Request.nuevoCheckinPlaya(this, new Checkin(ValidacionPlaya.playa.idserver, new Date(), Utilities.getUserIdFacebook(this)), pd);
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
            switch (position) {
                case 0:
                    return new VerPlayaFragment();
                case 1:
                    return new PlayaDirectoFragment();
                case 2:
                    return new MisDatosFragment();
                case 3:
                    return new MensajesBotellasFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if (Geo.isNearToMe(ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud))
                return 4;
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section_see_beach).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section_webcam_beach).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section_opinion_beach).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section_descubre_beach).toUpperCase(l);
            }
            return null;
        }
    }

}
