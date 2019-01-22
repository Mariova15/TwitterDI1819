/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import logica.Excepciones.CifradoExcepcion;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author adan
 */
public class Autentificacion {

    private final transient CifradoRsa cifrado = new CifradoRsa();
    private transient String oatPublico = "HkM0LS2MQLnh2c4ECo40HHYmP";
    private transient String oatPrivado = "uPhHLyAJahCC4ui9nC2AHvzYZEEGDnNyoshKsXIL845C3MVFWG";

    

    /**
     * crea una nueva conexion
     *
     * @param twitter
     * @param request
     * @param pin
     * @throws TwitterException
     * @throws logica.Excepciones.CifradoExcepcion
     * @throws IOException
     */
    public Twitter nuevaConexion()
            throws TwitterException, CifradoExcepcion, IOException, URISyntaxException {
        Twitter twitter= TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(oatPublico, oatPrivado);
        RequestToken requestToken = twitter.getOAuthRequestToken();
        Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
        String pin = JOptionPane.showInputDialog("introduce pin");
        AccessToken access = twitter.getOAuthAccessToken(requestToken, pin);
        guardarConexion(access);
        return twitter;

    }

    /**
     * guarda la conexion pasada por parametro
     *
     * @param access
     * @throws logica.Excepciones.CifradoExcepcion
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void guardarConexion(AccessToken access)
            throws CifradoExcepcion, FileNotFoundException, IOException {

        if (access == null) {
            throw new IllegalArgumentException("twitter no puede ser nulo");
        }
        String claves = access.getToken() + "," + access.getTokenSecret();
        String sha256 = cifrado.sha256(claves);

        Properties configuracion = new Properties();
        configuracion.setProperty("ultima_sesion", sha256);
        configuracion.store(new FileOutputStream(new File("configuracion.cnf")), "configuracion twitter");

        File sesion = new File("sesiones" + File.separator + sha256);
        sesion.getParentFile().mkdirs();
        FileOutputStream writer = new FileOutputStream(sesion);
        byte[] encriptado = cifrado.encriptar(claves);
        writer.write(encriptado);
        writer.close();
    }

    /**
     * carga la ultima sesion
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception
     */
    public Twitter cargarUltimaSesion() throws FileNotFoundException, IOException, Exception {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(oatPublico, oatPrivado);

        Properties configuracion = new Properties();
        configuracion.load(new FileInputStream(new File("configuracion.cnf")));
        String sha256 = configuracion.getProperty("ultima_sesion");
        if (sha256 == null || "".equals(sha256)) {
            throw new Exception("no hay sesion guardada");
        }

        File sesion = new File("sesiones" + File.separator + sha256);
        if (!sesion.exists()) {
            errorSesion();
        }

        cargarSesion(sesion, sha256, twitter);
        return twitter;
    }

    /**
     * carga una sesion
     *
     * @param sesion archivo con la sesion encriptada
     * @param sha256 nombre del archivo, sha256 del contenido desencriptado
     * @param twitter
     * @throws java.io.FileNotFoundException
     * @throws logica.Excepciones.CifradoExcepcion
     *
     */
    public void cargarSesion(File sesion, String sha256, Twitter twitter)
            throws FileNotFoundException, IOException, CifradoExcepcion, Excepciones.SesionExcepcion {

        AccessToken accessToken = getAccessToken(sesion);
        twitter.setOAuthConsumer(oatPublico, oatPrivado);
        twitter.setOAuthAccessToken(accessToken);
        guardarConexion(accessToken);
    }
    
    public void cargarSesion (AccessToken accessToken,Twitter twitter) throws CifradoExcepcion, IOException{
        twitter.setOAuthConsumer(oatPublico, oatPrivado);
        twitter.setOAuthAccessToken(accessToken);
        guardarConexion(accessToken);
    }

    public List<AccessToken> getAllConexiones() throws CifradoExcepcion, IOException, FileNotFoundException {
        List<AccessToken> conexiones = new ArrayList();
        File padre = new File("sesiones");
        padre.mkdirs();
        File[] conexionesFile = padre.listFiles();
        for (File file : conexionesFile) {
            try {
                conexiones.add(getAccessToken(file));
            } catch (Excepciones.SesionExcepcion ex) {
                file.delete();
                ex.printStackTrace();
            }
        }
        return conexiones;
    }

    private AccessToken getAccessToken(File file) throws FileNotFoundException, CifradoExcepcion, IOException, Excepciones.SesionExcepcion {
        FileInputStream reader = new FileInputStream(file);
        byte[] encriptado = new byte[reader.available()];
        reader.read(encriptado);
        String desencriptado = cifrado.desencriptar(encriptado);
        if (!cifrado.sha256(desencriptado).equals(file.getName())) {
            errorSesion();
        }
        //0 publica, 1 privada
        String[] claves = desencriptado.split(",");
        if (claves.length < 2) {
            errorSesion();
        }
        return new AccessToken(claves[0], claves[1]);
    }

    private void errorSesion() throws Excepciones.SesionExcepcion, FileNotFoundException, IOException {
        Properties configuracion = new Properties();
        configuracion.setProperty("ultima_sesion", "");
        configuracion.store(new FileOutputStream(new File("configuracion.cnf")), "configuracion twitter");
        throw new Excepciones.SesionExcepcion();
    }
}
