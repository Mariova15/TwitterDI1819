/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario
 */
public class Fecha {

    public static Date fechaParse(String fechaParse) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            Date fecha = null;
            fecha = sdf.parse(fechaParse);

            return fecha;
        } catch (ParseException ex) {
            Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String fechaFormat(Date fechaFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        return sdf.format(fechaFormat);
    }

    public static String timeFormat(Date fechaFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        return sdf.format(fechaFormat);
    }

}
