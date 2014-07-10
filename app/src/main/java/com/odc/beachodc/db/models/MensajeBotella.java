package com.odc.beachodc.db.models;


import android.content.Context;

import com.odc.beachodc.utilities.Utilities;

import java.util.Date;

/**
 * Created by Paco on 07/07/2014.
 */
public class MensajeBotella {

    public String idserver;

    public String idfbautor;

    public String nombreautor;

    public String idserverplayaorigen;

    public String idserverplayadestino;

    public String nombreplayadestino;

    public String mensaje;

    public Date fecha;


    public MensajeBotella(){
        super();
    }

    public MensajeBotella(Context ctx, boolean porDefecto){
        if (porDefecto){ // TODO: Quitar esto, es solo para crear un MOCK
            this.idserver = "1111";
            this.idfbautor = Utilities.getUserIdFacebook(ctx);
            this.nombreautor = Utilities.getUserNameFacebook(ctx);
            this.nombreplayadestino = "Mi Playa Preferida";
            this.idserverplayadestino = "aaa";
            this.idserverplayaorigen = "bbb";
            this.fecha = new Date();
            this.mensaje = "Es un gran placer siempre tomar el sol en la playa a las 12:00";
        }
    }

    public MensajeBotella(String idserver, String idfbautor, String nombreautor, String idserverplayadestino, String idserverplayaorigen, String nombreplayadestino, Date fecha, String mensaje){
        this.idserver = idserver;
        this.idfbautor = idfbautor;
        this.nombreautor = nombreautor;
        this.idserverplayadestino = idserverplayadestino;
        this.idserverplayaorigen = idserverplayaorigen;
        this.nombreplayadestino = nombreplayadestino;
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public void mostrar(){
        System.out.println("| *****************************************************");
        System.out.println("| IdServer: "+ this.idserver);
        System.out.println("| Autor: "+this.nombreautor);
        System.out.println("| Playa Origen: "+this.idserverplayaorigen);
        System.out.println("| Playa Destino: "+this.idserverplayadestino);
        System.out.println("| Fecha: "+this.fecha);
        System.out.println("| Mensaje: "+this.mensaje);
        System.out.println("| *****************************************************");
    }

}
