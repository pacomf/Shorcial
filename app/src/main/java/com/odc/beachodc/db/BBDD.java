package com.odc.beachodc.db;

import android.content.Context;
import android.util.Log;

import com.odc.beachodc.db.dao.AppDataContext;

/**
 * Created by Paco on 07/07/2014.
 */
public class BBDD {

    public static AppDataContext appDataContext;

    public static void initBBDD (Context ctx){
        getApplicationDataContext(ctx);
    }

    public static AppDataContext getApplicationDataContext(Context ctx) {
        if (BBDD.appDataContext == null) {
            try {
                BBDD.appDataContext = new AppDataContext(ctx);
            } catch (Exception e) {
                Log.e("BBDD", "Error inicializando AppDataContext: " + e.getMessage());
            }
        }
        return BBDD.appDataContext;
    }
}
