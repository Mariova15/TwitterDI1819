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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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

    private final transient CifradoRSAObjeto cifrado = new CifradoRSAObjeto();
    private final transient String OAUTHPUBLICO = "HkM0LS2MQLnh2c4ECo40HHYmP";
    private final transient String OAUTHPRIVADO = "uPhHLyAJahCC4ui9nC2AHvzYZEEGDnNyoshKsXIL845C3MVFWG";
    private static Autentificacion AUTENTIFICACION;
    private Configuracion configuracion;
    private RequestToken request;

    private Autentificacion() {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(OAUTHPUBLICO, OAUTHPRIVADO);
        this.configuracion = new Configuracion();
        this.configuracion.setComentario("configuracion twitter");
    }

    public static Autentificacion getInstance() {
        if (AUTENTIFICACION == null) {
            AUTENTIFICACION = new Autentificacion();
        }
        return AUTENTIFICACION;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    /**
     * crea una nueva conexion
     * null si el usuario cancela
     *
     * @param twitter
     * @param request
     * @param pin
     * @throws TwitterException
     * @throws logica.Excepciones.CifradoExcepcion
     * @throws IOException
     */
    public Twitter nuevaConexion(Boolean activarGuardado)
            throws TwitterException, CifradoExcepcion, IOException, URISyntaxException {
        Twitter twitter = TwitterFactory.getSingleton();
        if (request == null)
        this.request = twitter.getOAuthRequestToken();
        Desktop.getDesktop().browse(new URI(this.request.getAuthorizationURL()));
        String pin = JOptionPane.showInputDialog("introduce pin");
        if(pin==null) return null;
        AccessToken access = twitter.getOAuthAccessToken(this.request, pin);

        if (activarGuardado) {
            File profile = new File(".." + File.separator + "imgs"
                    + File.separator + "user" + File.separator + access.getScreenName());
            if (!profile.exists()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GestionClienteTwitter.descargarUserIMG(twitter, access.getScreenName());
                    }
                }).start();
                
            }
            guardarConexion(access,access.getScreenName());
        }
        return twitter;

    }

    /**
     * guarda la conexion pasada por parametro
     * @param access
     * @throws logica.Excepciones.CifradoExcepcion
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void guardarConexion(AccessToken access,String screenName)
            throws CifradoExcepcion, FileNotFoundException, IOException {

        if (access == null) {
            throw new IllegalArgumentException("twitter no puede ser nulo");
        }
        String claves = access.getToken() + "," + access.getTokenSecret();
        this.configuracion.setClave("ultima_sesion", screenName);

        File sesion = new File("sesiones" + File.separator + screenName);
        sesion.getParentFile().mkdirs();
        FileOutputStream writer = new FileOutputStream(sesion);
        byte[] encriptado = cifrado.encriptar(claves.getBytes());
        writer.write(encriptado);
        writer.close();
    }

    /**
     * borra la conexion pasada por parametro
     *
     * @throws logica.Excepciones.CifradoExcepcion
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void borrarUltimaSesion()
            throws CifradoExcepcion, FileNotFoundException, IOException {
        this.borrarConexion(this.configuracion.getClave("ultima_sesion"));
    }
    
    /**
     * borra la conexión con el screenName que se le meta por parametro
     * @param screenName
     * @throws IOException 
     */
    public void borrarConexion (String screenName) throws IOException{
        File sesiones = new File("sesiones"+File.separator+screenName);
        if(sesiones.exists()) sesiones.delete();
        File imagenes = new File("src"+File.separator+"imgs"+File.separator+
                "users"+File.separator+screenName);
        
        if (imagenes.exists()){
            //no os creais que soy un genio lo encontré por internet
            Path path = imagenes.toPath();
            Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
        }
        
        String userConfiguracion = this.configuracion.getClave("ultima_sesion");
        if(screenName.equals(userConfiguracion)){
            this.configuracion.setClave("ultima_sesion", "");
        }
    }

    /**
     * carga la ultima sesion
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception
     */
    public Twitter cargarUltimaSesion() throws FileNotFoundException,
            IOException, CifradoExcepcion, Excepciones.SesionExcepcion {
        Twitter twitter = TwitterFactory.getSingleton();

        String screenName = this.configuracion.getClave("ultima_sesion");
        if (screenName == null || "".equals(screenName)) {
            errorSesion();
        }

        File sesion = new File("sesiones" + File.separator + screenName);
        if (!sesion.exists()) {
            errorSesion();
        }

        cargarSesion(twitter,screenName);
        return twitter;
    }

    /**
     * carga una sesion
     *
     * @param sesion archivo con la sesion encriptada
     * @param twitter
     * @throws java.io.FileNotFoundException
     * @throws logica.Excepciones.CifradoExcepcion
     *
     */
    public void cargarSesion(Twitter twitter,String screenName)
            throws FileNotFoundException, IOException, CifradoExcepcion, Excepciones.SesionExcepcion {
        File file = new File ("sesiones"+File.separator+screenName);
        AccessToken accessToken = getAccessToken(file);
        twitter.setOAuthAccessToken(accessToken);

        guardarConexion(accessToken,screenName);
    }

    public void cargarSesion(AccessToken accessToken, Twitter twitter,String screenName) throws CifradoExcepcion, IOException {
        twitter.setOAuthAccessToken(accessToken);
        guardarConexion(accessToken,screenName);
    }

    public List<AccessToken> getAllConexiones() throws IOException, FileNotFoundException, Excepciones.SesionExcepcion {
        List<AccessToken> conexiones = new ArrayList();
        File padre = new File("sesiones");
        padre.mkdirs();
        File[] conexionesFile = padre.listFiles();
        for (File file : conexionesFile) {
            try {
                conexiones.add(getAccessToken(file));
            } catch (CifradoExcepcion ex) {
                file.delete();
                ex.printStackTrace();
            }
        }
        return conexiones;
    }

    private AccessToken getAccessToken(File file) throws FileNotFoundException,
            CifradoExcepcion, IOException, Excepciones.SesionExcepcion {
        FileInputStream reader = new FileInputStream(file);
        byte[] encriptado = new byte[reader.available()];
        reader.read(encriptado);
        String desencriptado = new String(cifrado.desencriptar(encriptado));
        //0 publica, 1 privada
        String[] claves = desencriptado.split(",");
        if (claves.length < 2) {
            errorSesion();
        }
        return new AccessToken(claves[0], claves[1]);
    }

    private void errorSesion() throws Excepciones.SesionExcepcion, FileNotFoundException, IOException {
        this.configuracion.setClave("ultima_sesion", "");
        throw new Excepciones.SesionExcepcion();
    }
    
    public void cargarSesionesComboBox (JComboBox combo) throws FileNotFoundException{
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        File[] ficheros = new File("sesiones").listFiles();
        for (File fichero : ficheros) {
            modelo.addElement(fichero.getName());
            combo.setModel(modelo);
        }
    }
     
}
