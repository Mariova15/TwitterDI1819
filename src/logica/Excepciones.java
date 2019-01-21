/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author adan
 */
public class Excepciones {
    /**
     * error en el cifrado
     */
    public static class CifradoExcepcion extends Exception {
         public CifradoExcepcion(Exception ex){
             super("error en el cifrado: "+ex.getMessage());
         }
     }
    
    /**
     * error al cargar la sesion del fichero
     */
    public static class SesionExcepcion extends Exception{

        public SesionExcepcion() {
            super("error al cargar la sesion");
        }
        
    }
}
