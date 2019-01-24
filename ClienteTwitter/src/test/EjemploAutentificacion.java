/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Autentificacion;
import logica.Excepciones;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

/**
 *
 * @author adan
 */
public class EjemploAutentificacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Autentificacion autentificacion = Autentificacion.getInstance();
            // nueva conexion:
            Twitter twitter = TwitterFactory.getSingleton();
            RequestToken requestToken = twitter.getOAuthRequestToken();
            Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
            System.out.print("introduce pin: ");
            String pin = new Scanner(System.in).nextLine(); //se necesita introducir el pin
            autentificacion.nuevaConexion();
            
            
            // cargar ultima conexion
            Twitter ultimaSesion = autentificacion.cargarUltimaSesion();
            
            //cargar sesion guardada
            File sesion = new File("sesiones").listFiles()[0];//primera sesion...mas o menos
            autentificacion.cargarSesion(sesion, sesion.getName(), twitter); //crear objeto twitter antes
            
        } catch (IOException | URISyntaxException | TwitterException ex) {
            Logger.getLogger(EjemploAutentificacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Excepciones.CifradoExcepcion ex) {
            Logger.getLogger(EjemploAutentificacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EjemploAutentificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
