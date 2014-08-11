package com.odc.beachodc.activities;

/**
 * Created by Paco on 11/08/2014.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.odc.beachodc.BuildConfig;
import com.odc.beachodc.Home;
import com.odc.beachodc.Logout;
import com.odc.beachodc.R;
import com.odc.beachodc.utilities.Utilities;

//import org.sufficientlysecure.donations.DonationsFragment;

public class DonationsActivity extends FragmentActivity {

    /**
     * Google
     */
    /*private static final String GOOGLE_PUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnEwD4f3GPD8oA7nt0fC3HN5EuAVXLWpdOMmF7+tFQ/ZReK34WJLX3pooU2XxjAspUKszSxr4XzviPsUVLu6keI4Zbz/m7DpfV4Hk8QEcTBl0uhI0XAhPKzw3htMNHM7KM+IaATaAB6EGEpdRBFDj+aQlVrgsGgF5AEkP8EvShVhLdTlYoQbZinOFvgLmDzNz1qkn2PZGhB+0pE8zt5Y791mOZhRVdqoZGf8Lyo0SSO2YRijy/b4ZmiaDsaxpNimE2IgtKNHqJBF4WvCs8QMPdWKpFqFei3HjdyRXUmxjX9A0Dr76Rz9MAJEJJA7qUnAN2hxYYqJcPLyzHeKoD7ZjeQIDAQAB";
    private static final String[] GOOGLE_CATALOG = new String[]{"shorcial.donation.1",
            "shorcial.donation.2", "shorcial.donation.3", "shorcial.donation.5", "shorcial.donation.8",
            "shorcial.donation.13"};
    */
    /**
     * PayPal
     */
    private static final String PAYPAL_USER = "erespia2@gmail.com";
    private static final String PAYPAL_CURRENCY_CODE = "EUR";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Utilities.setActionBarCustomize(this);
        super.onCreate(savedInstanceState);

        //opening transition animations
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);

        /*setContentView(R.layout.donations_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DonationsFragment donationsFragment;
        if (BuildConfig.DONATIONS_GOOGLE) {
            donationsFragment = DonationsFragment.newInstance(BuildConfig.DEBUG, true, GOOGLE_PUBKEY, GOOGLE_CATALOG,
                    getResources().getStringArray(R.array.donation_google_catalog_values), true, PAYPAL_USER,
                    PAYPAL_CURRENCY_CODE, getString(R.string.donation_paypal_item), false, null, null, false, null);
        } else {
            finish();
        }

        ft.replace(R.id.donations_activity_container, donationsFragment, "donationsFragment");
        ft.commit();*/
    }

    /**
     * Needed for Google Play In-app Billing. It uses startIntentSenderForResult(). The result is not propagated to
     * the Fragment like in startActivityForResult(). Thus we need to propagate manually to our Fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("donationsFragment");
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Si se ha clickado en la opcion de cerrar sesión del menu, mostraremos el fragment que nos permitirá cerrar la sesión.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_logout:
                Intent intent = new Intent(this, Logout.class);
                startActivity(intent);
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
    public void onPause() {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

}
