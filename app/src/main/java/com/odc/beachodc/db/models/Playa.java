package com.odc.beachodc.db.models;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

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

    @TableField(name = "limpieza", datatype = DATATYPE_DOUBLE)
    public Double limpieza;

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

    public Playa(){
        super();
    }

    public Playa(String idserver, String nombre, Double latitud, Double longitud, Boolean banderaazul, String dificultadacceso,
                 Double limpieza, String tipoarena, Double valoracion, Boolean rompeolas, Boolean hamacas, Boolean sombrillas,
                 Boolean chiringuitos, Boolean duchas, Boolean socorrista){
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
    }

}
