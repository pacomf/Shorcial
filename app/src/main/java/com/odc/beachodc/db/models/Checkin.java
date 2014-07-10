package com.odc.beachodc.db.models;

import android.content.Context;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.odc.beachodc.utilities.Utilities;

import java.util.Date;
import java.util.Random;

/**
 * Created by Paco on 07/07/2014.
 */
@Table(name = "Checkin")
public class Checkin extends Entity {

    @TableField(name = "idplayaserver", datatype = DATATYPE_STRING)
    public String idplayaserver;

    @TableField(name = "fecha", datatype = DATATYPE_DATE_BINARY)
    public Date fecha;

    @TableField(name = "idfbuser", datatype = DATATYPE_STRING)
    public String idfbuser;


    public Checkin(){
        super();
    }

    public Checkin(Context ctx, boolean porDefecto){
        if (porDefecto){ // TODO: Esto es para pruebas, borrar esta funcion al finalizar el desarrollo
            this.idplayaserver = "aaa";
            this.fecha = new Date();
            this.idfbuser = Utilities.getUserIdFacebook(ctx);

        }
    }

    public Checkin(String idplayaserver, Date fecha, String iduserfb){
        this.idplayaserver = idplayaserver;
        this.fecha = fecha;
        this.idfbuser = iduserfb;

    }


}
