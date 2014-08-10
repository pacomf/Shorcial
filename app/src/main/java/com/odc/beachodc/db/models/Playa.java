package com.odc.beachodc.db.models;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

import java.util.Date;
import java.util.Random;

/**
 * Created by Paco on 07/07/2014.
 */
@Table(name = "Playas")
public class Playa extends Entity {

    @TableField(name = "idserver", datatype = DATATYPE_STRING)
    public String idserver;

    @TableField(name = "nombre", datatype = DATATYPE_STRING)
    public String nombre;

    @TableField(name = "latitud", datatype = DATATYPE_DOUBLE)
    public Double latitud;

    @TableField(name = "longitud", datatype = DATATYPE_DOUBLE)
    public Double longitud;

    @TableField(name = "banderaazul", datatype = DATATYPE_BOOLEAN)
    public Boolean banderaazul;

    @TableField(name = "dificultadacceso", datatype = DATATYPE_STRING)
    public String dificultadacceso;

    @TableField(name = "limpieza", datatype = DATATYPE_STRING)
    public String limpieza;

    @TableField(name = "tipoarena", datatype = DATATYPE_STRING)
    public String tipoarena;

    @TableField(name = "valoracion", datatype = DATATYPE_DOUBLE)
    public Double valoracion;

    @TableField(name = "rompeolas", datatype = DATATYPE_BOOLEAN)
    public Boolean rompeolas;

    @TableField(name = "hamacas", datatype = DATATYPE_BOOLEAN)
    public Boolean hamacas;

    @TableField(name = "sombrillas", datatype = DATATYPE_BOOLEAN)
    public Boolean sombrillas;

    @TableField(name = "chiringuitos", datatype = DATATYPE_BOOLEAN)
    public Boolean chiringuitos;

    @TableField(name = "duchas", datatype = DATATYPE_BOOLEAN)
    public Boolean duchas;

    @TableField(name = "socorrista", datatype = DATATYPE_BOOLEAN)
    public Boolean socorrista;

    @TableField(name = "perros", datatype = DATATYPE_BOOLEAN)
    public Boolean perros;

    @TableField(name = "nudista", datatype = DATATYPE_BOOLEAN)
    public Boolean nudista;

    @TableField(name = "cerrada", datatype = DATATYPE_BOOLEAN)
    public Boolean cerrada;

    @TableField(name = "checkin", datatype = DATATYPE_DATE_BINARY)
    public Date checkin;

    @TableField(name = "webcamURL", datatype = DATATYPE_STRING)
    public String webcamURL;

    public Playa(){
        super();
    }

    public Playa(boolean porDefecto){
        if (porDefecto){
            this.banderaazul = false;
            this.dificultadacceso = "facil";
            this.limpieza = "limpia";
            this.tipoarena = "negra";
            this.rompeolas = false;
            this.hamacas = false;
            this.sombrillas = false;
            this.chiringuitos = false;
            this.duchas = false;
            this.socorrista = false;
            this.webcamURL = "";
            this.perros = false;
            this.nudista = false;
            this.cerrada = false;
        } else { // TODO: Quitar esto, es solo para crear un MOCK
            this.nombre = "Playaza del Carajo";
            Random random = new Random();
            this.idserver = String.valueOf(random.nextInt());
            this.latitud = 28.4824608;
            this.longitud = -16.320505;
            this.banderaazul = true;
            this.dificultadacceso = "facil";
            this.limpieza = "mucho";
            this.tipoarena = "rocas";
            this.rompeolas = true;
            this.hamacas = false;
            this.sombrillas = false;
            this.chiringuitos = true;
            this.duchas = true;
            this.socorrista = true;
            this.perros = true;
            this.nudista = false;
            this.cerrada = false;
        }
    }

    public Playa(String idserver, String nombre, Double latitud, Double longitud, Boolean banderaazul, String dificultadacceso,
                 String limpieza, String tipoarena, Double valoracion, Boolean rompeolas, Boolean hamacas, Boolean sombrillas,
                 Boolean chiringuitos, Boolean duchas, Boolean socorrista, Date checkin, String webcamURL, Boolean perros, Boolean nudista, Boolean cerrada){
        this.idserver = idserver;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.banderaazul = banderaazul;
        this.dificultadacceso = dificultadacceso;
        this.limpieza = limpieza;
        this.tipoarena = tipoarena;
        this.valoracion = valoracion;
        this.rompeolas = rompeolas;
        this.hamacas = hamacas;
        this.sombrillas = sombrillas;
        this.chiringuitos = chiringuitos;
        this.duchas = duchas;
        this.socorrista = socorrista;
        this.checkin = checkin;
        this.webcamURL = webcamURL;
        this.perros = perros;
        this.nudista = nudista;
        this.cerrada = cerrada;
    }

    /*public void mostrar(){
        System.out.println("| *****************************************************");
        System.out.println("| IdServer: "+ this.idserver);
        System.out.println("| Nombre: "+this.nombre);
        System.out.println("| Geo: "+this.latitud+":"+this.longitud);
        System.out.println("| Bandera Azul: "+this.banderaazul);
        System.out.println("| Dificultad Acceso: "+this.dificultadacceso);
        System.out.println("| Limpieza: "+this.limpieza);
        System.out.println("| Tipo de Arena: "+this.tipoarena);
        System.out.println("| Rompeolas: "+this.rompeolas);
        System.out.println("| Hamacas: "+this.hamacas);
        System.out.println("| Sombrillas: "+this.sombrillas);
        System.out.println("| Chiringuitos: "+this.chiringuitos);
        System.out.println("| Duchas: "+this.duchas);
        System.out.println("| Socorrista: "+this.socorrista);
        System.out.println("| Perros: "+this.perros);
        System.out.println("| webcamURL: "+this.webcamURL);
        System.out.println("| *****************************************************");
    }*/

}
