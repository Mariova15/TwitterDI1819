/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author sergio
 */
public class Tweet {

    private String nombre;
    private String nombrePantalla;
    private Date fechaPublicacion;
    private String texto;

    public Tweet(String nombre, String nombrePantalla, Date fechaPublicacion, String texto) {
        this.nombre = nombre;
        this.nombrePantalla = nombrePantalla;
        this.fechaPublicacion = fechaPublicacion;
        this.texto = texto;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getNombrePantalla() {
        return nombrePantalla;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getTexto() {
        return texto;
    }

}
