/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adan
 */
public class Configuracion {
    private Properties configuracion;
    public static String UBICACION="configuracion.cnf";
    private String comentario;

    public Configuracion() {
        this.configuracion=new Properties();
        
        try {
            File archivoConfiguracion = new File(UBICACION);
        if (!archivoConfiguracion.exists()) archivoConfiguracion.createNewFile();
            this.configuracion.load(new FileInputStream(new File(UBICACION)));
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public String getClave(String clave){
        return this.configuracion.getProperty(clave);
    }
    
    public void setClave(String clave,String valor) throws FileNotFoundException, IOException{
        this.configuracion.setProperty(clave, valor);
        this.configuracion.store(new FileOutputStream
        (new File(UBICACION)), (this.comentario==null)?"":this.comentario);
    }
    
}
