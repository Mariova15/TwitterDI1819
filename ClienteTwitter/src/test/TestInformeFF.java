/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import logica.GestionClienteTwitter;
import modelo.Usuario;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import utils.DesktopApi;

/**
 *
 * @author sergio
 */
public class TestInformeFF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //TODO: ver dónde está incluída la librería JasperReports

        try {

            //CONEXION            
            Twitter twitter = TwitterFactory.getSingleton();

            twitter.setOAuthConsumer("HkM0LS2MQLnh2c4ECo40HHYmP", "uPhHLyAJahCC4ui9nC2AHvzYZEEGDnNyoshKsXIL845C3MVFWG"); //poco seguro

            RequestToken request = twitter.getOAuthRequestToken();

            try {
                Desktop.getDesktop().browse(new URI(request.getAuthorizationURL()));
            } catch (UnsupportedOperationException ex) {
                //ex.printStackTrace();     // evitamos warnings rojos
                DesktopApi.browse(new URI(request.getAuthorizationURL()));
            }

            String pin = JOptionPane.showInputDialog("introduce pin");

            AccessToken access = twitter.getOAuthAccessToken(request, pin);
            twitter.setOAuthAccessToken(access);

            String screenName;
            Scanner sc = new Scanner(System.in);
            System.out.println(twitter.getScreenName());
    

            // Creación de informe a partir de listas
            List<Usuario> listaFollowers = GestionClienteTwitter.listadoFollowersUsuariodeterminado(twitter, twitter.getScreenName());
            List<Usuario> listaFollows = GestionClienteTwitter.listadoFollowsUsuariodeterminado(twitter, twitter.getScreenName());
            Usuario usuario = new Usuario(twitter.getScreenName(), listaFollowers, listaFollows);
            
            for (Usuario listaFollow : usuario.getListaFollows()) {
                System.out.println(listaFollow.getName());
            }
            
            List<Usuario> listaUsuarios = new ArrayList<Usuario>();
            listaUsuarios.add(usuario);

            //La encapsulamos en el objeto adecuado
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaUsuarios);
            //Creamos el map para los parámetros
            Map parametros = new HashMap();
            parametros.put("NOM_PANTALLA", twitter.getScreenName());       // Si no metemos esta línea el map va sin parametros (vacío)
            JasperPrint print = JasperFillManager.fillReport("informes/informe_twitter_listaFF.jasper", parametros, dataSource);
            JasperExportManager.exportReportToPdfFile(print, "informes/informe_twitter_listaFF.pdf");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("El informe ha sido generado satisfactoriamente.");

    }

}
