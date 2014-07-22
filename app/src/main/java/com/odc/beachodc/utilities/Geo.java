package com.odc.beachodc.utilities;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.odc.beachodc.R;
import com.odc.beachodc.db.BBDD;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.db.models.Playa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

/**
 * Created by Paco on 07/07/2014.
 */
public class Geo {

    public static Location myLocation; // Ultima posicion conocida

    public static LocationManager mLocationManager;

    //private static final int DEGREE_DISTANCE_AT_EQUATOR = 111329;
    /**
     * the radius of the earth in meters.
     */
    /**
     * the length of one minute of latitude in meters, i.e. one nautical mile in meters.
     */
    private static final double MINUTES_TO_METERS = 1852d;
    /**
     * the amount of minutes in one degree.
     */
    private static final double DEGREE_TO_MINUTES = 60d;


    /**
     * This method extrapolates the endpoint of a movement with a given length from a given starting point using a given
     * course.
     *
     * @param startPointLat the latitude of the starting point in degrees, must not be {@link Double#NaN}.
     * @param startPointLon the longitude of the starting point in degrees, must not be {@link Double#NaN}.
     * @param course        the course to be used for extrapolation in degrees, must not be {@link Double#NaN}.
     * @param distance      the distance to be extrapolated in meters, must not be {@link Double#NaN}.
     *
     * @return the extrapolated point.
     */
    public static ArrayList<Long> extrapolate(final double startPointLat, final double startPointLon, final double course,
                                              final double distance) {
        //
        //lat =asin(sin(lat1)*cos(d)+cos(lat1)*sin(d)*cos(tc))
        //dlon=atan2(sin(tc)*sin(d)*cos(lat1),cos(d)-sin(lat1)*sin(lat))
        //lon=mod( lon1+dlon +pi,2*pi )-pi
        //
        // where:
        // lat1,lon1  -start pointi n radians
        // d          - distance in radians Deg2Rad(nm/60)
        // tc         - course in radians

        final double crs = Math.toRadians(course);
        final double d12 = Math.toRadians(distance / MINUTES_TO_METERS / DEGREE_TO_MINUTES);

        final double lat1 = Math.toRadians(startPointLat);
        final double lon1 = Math.toRadians(startPointLon);

        final double lat = Math.asin(Math.sin(lat1) * Math.cos(d12)
                + Math.cos(lat1) * Math.sin(d12) * Math.cos(crs));
        final double dlon = Math.atan2(Math.sin(crs) * Math.sin(d12) * Math.cos(lat1),
                Math.cos(d12) - Math.sin(lat1) * Math.sin(lat));
        final double lon = (lon1 + dlon + Math.PI) % (2 * Math.PI) - Math.PI;

        ArrayList<Long> ret =  new ArrayList<Long>();
        ret.add(Long.valueOf((String.format("%.7f", Math.toDegrees(lat)).replace(",", ""))));
        ret.add(Long.valueOf((String.format("%.7f", Math.toDegrees(lon)).replace(",", ""))));
        return ret;
    }

    public static ArrayList<Long> getXMeterAreaToPoint(long latitud, long longitud, Double meters){

        ArrayList<Long> ret = new ArrayList<Long>();

        Double lat=Double.valueOf(latitud)/10000000;
        Double lng=Double.valueOf(longitud)/10000000;

        // Derecha
        ArrayList<Long> calc = extrapolate(lat, lng, 0, meters);
        ret.add(calc.get(0)); //lat
        //ret.add(calc.get(1)); //lon

        // Superior
        calc = extrapolate(lat, lng, 90, meters);
        //ret.add(calc.get(0)); //lat
        ret.add(calc.get(1)); //lon

        // Izquierda
        calc = extrapolate(lat, lng, 180, meters);
        ret.add(calc.get(0)); //lat
        //ret.add(calc.get(1)); //lon

        // Debajo
        calc = extrapolate(lat, lng, 270, meters);
        //ret.add(calc.get(0)); //lat
        ret.add(calc.get(1)); //lon

        return ret;
    }

    //Radio en Metros
    public static List<Playa> getPlayasCercanas(Context ctx, long latitud, long longitud, Double radio){
        ArrayList<Long> dimensiones = getXMeterAreaToPoint(latitud, longitud, radio);
        if (dimensiones.size()>0){
            List ret = new ArrayList<Playa>();
            try {
                ret = BBDD.getApplicationDataContext(ctx).playasDao.search(false, "longitud > ? and longitud < ? and latitud > ? and latitud < ?", new String[]{dimensiones.get(3).toString(), dimensiones.get(1).toString(), dimensiones.get(2).toString(), dimensiones.get(0).toString()}, null, null, null, null, null);
            } catch (Exception e){
                Log.i("BBDD", "Fallo al intentar buscar Playas cercanas");
            }
            return ret;
        } else
            return new ArrayList<Playa>();
    }

    private static final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Geo.myLocation = location;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public static void activeGPSLocation (Context ctx) {
        Geo.mLocationManager = (LocationManager) ctx.getSystemService(ctx.LOCATION_SERVICE);

        // Cada segundo renovamos la posicion, aunque no nos hayamos movido nada (0 metros)
        Geo.mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 0, mLocationListener);
        Geo.mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, mLocationListener);
        Geo.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);
    }

    public static float getDistanceInMetersToMe (Double latitud, Double longitud){
        if (myLocation != null){
            Location destiny = new Location ("destino");
            destiny.setLatitude(latitud);
            destiny.setLongitude(longitud);

            return Geo.myLocation.distanceTo(destiny);
        } else {
            return -1;
        }
    }

    public static float getDistanceInMetersTo (Double latitudD, Double longitudD, Double latitudO, Double longitudO){
        Location destiny = new Location ("destino");
        destiny.setLatitude(latitudD);
        destiny.setLongitude(longitudD);

        Location origin = new Location ("origen");
        origin.setLatitude(latitudO);
        origin.setLongitude(longitudO);

        return origin.distanceTo(destiny);
    }

    public static String getDistanceToPrint (Context ctx, Double latitud, Double longitud){
        float distance = Geo.getDistanceInMetersToMe(latitud, longitud);

        if (distance == -1)
            return ctx.getString(R.string.question)+" "+ctx.getString(R.string.meters);
        else {
            if ((distance / 1000) >= 1) { // Formato en km.
                DecimalFormat df = new DecimalFormat("#.#");
                return df.format((distance/1000))+" "+ctx.getString(R.string.kilometers);
            } else { // Formato en m.
                return ((int) distance)+" "+ctx.getString(R.string.meters);
            }
        }
    }

    public static String getDistanceToPrint (Context ctx, Double latitudO, Double longitudO, Double latitudD, Double longitudD){
        float distance = Geo.getDistanceInMetersTo(latitudD, longitudD, latitudO, longitudO);

        if (distance == -1)
            return ctx.getString(R.string.question)+" "+ctx.getString(R.string.meters);
        else {
            if ((distance / 1000) >= 1) { // Formato en km.
                DecimalFormat df = new DecimalFormat("#.#");
                return df.format((distance/1000))+" "+ctx.getString(R.string.kilometers);
            } else { // Formato en m.
                return ((int) distance)+" "+ctx.getString(R.string.meters);
            }
        }
    }

    public static List<Playa> orderByDistance (List<Playa> playas){
        Collections.sort(playas, new PlayasDistanceComparator());
        return playas;
    }

    public static List<Playa> orderByDistanceTo (List<Playa> playas, LatLng origin){
        Collections.sort(playas, new PlayasDistanceComparator(false, origin));
        return playas;
    }

    public static boolean isNearToMe (Double latitud, Double longitud){
        if (Geo.myLocation != null) {
            float distance = Geo.getDistanceInMetersTo(latitud, longitud, Geo.myLocation.getLatitude(), Geo.myLocation.getLongitude());
            if ((distance > -1) && (distance < 501)){
                return true;
            }
        }
        return false;
    }
}
