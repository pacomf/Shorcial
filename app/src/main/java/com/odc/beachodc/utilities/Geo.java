package com.odc.beachodc.utilities;

import android.content.Context;
import android.util.Log;

import com.odc.beachodc.db.BBDD;
import com.odc.beachodc.db.models.Playa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paco on 07/07/2014.
 */
public class Geo {

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
    }
