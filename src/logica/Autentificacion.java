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
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
    private String oatPublico = "HkM0LS2MQLnh2c4ECo40HHYmP";
    private String oatPrivado = "uPhHLyAJahCC4ui9nC2AHvzYZEEGDnNyoshKsXIL845C3MVFWG";
    
    public RequestToken getRequestToken(Twitter twitter) throws URISyntaxException, TwitterException, IOException{
        if (twitter==null) throw new IllegalArgumentException("twitter no puede ser nulo");
        twitter.setOAuthConsumer(oatPublico, oatPrivado);
        return twitter.getOAuthRequestToken();
    }
    
    public void nuevaConexion(Twitter twitter,RequestToken request,String pin)
            throws TwitterException, NoSuchAlgorithmException, IOException, 
            FileNotFoundException, InvalidKeySpecException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        
        
        if (twitter==null||request==null) throw new IllegalArgumentException("twitter y/o request no pueden ser nulos");
        AccessToken access = twitter.getOAuthAccessToken(request, pin);
        guardarConexion(twitter,request);
        
    }

    private void guardarConexion(Twitter twitter,RequestToken request) throws TwitterException,
            NoSuchAlgorithmException, FileNotFoundException, IOException, 
            InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException
            , IllegalBlockSizeException, BadPaddingException {
        
        if (twitter==null) throw new IllegalArgumentException("twitter no puede ser nulo");
        String claves = request.getToken()+","+request.getTokenSecret();
        String sha256 = cifrado.sha256(claves);
        
        Properties configuracion = new Properties();
        configuracion.setProperty("ultima_sesion", sha256);
        configuracion.store(new FileOutputStream(new File("configuracion.cnf")), "configuracion twitter");
        
        File sesion = new File("sesiones"+File.separator+sha256);
        sesion.getParentFile().mkdirs();
        FileOutputStream writer = new FileOutputStream(sesion);
        byte[] encriptado = cifrado.encriptar(claves);
        writer.write(encriptado);
        writer.close();
    }
    
    /**
     * 
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception 
     */
    public Twitter cargarUltimaSesion() throws FileNotFoundException, IOException, Exception{
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(oatPublico, oatPrivado);
        
        Properties configuracion = new Properties();
        configuracion.load(new FileInputStream(new File("configuracion.cnf")));
        String ultimaSesion = configuracion.getProperty("ultima_sesion");
        if(ultimaSesion==null||"".equals(ultimaSesion)) throw new Exception("no hay sesion guardada");
        
        File sesion = new File("sesiones"+File.separator+ultimaSesion);
        if(!sesion.exists()) {
            errorSesion();
        }
        
        
        cargarSesion(sesion, ultimaSesion, twitter);
        return twitter;
    }

    /**
     * mirar que tokens se necesita para guardar la sesion y como cargarlos luego
     * @param sesion
     * @param sha256
     * @param twitter
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws Exception
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws FileNotFoundException
     * @throws TwitterException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException 
     */
    private void cargarSesion(File sesion, String sha256, Twitter twitter) throws BadPaddingException, NoSuchAlgorithmException, IOException, Exception, NoSuchPaddingException, InvalidKeySpecException, FileNotFoundException, TwitterException, IllegalBlockSizeException, InvalidKeyException {
        FileInputStream reader = new FileInputStream(sesion);
        byte[] encriptado = new byte[reader.available()];
        reader.read(encriptado);
        String desencriptado = cifrado.desencriptar(encriptado);
        System.out.println(desencriptado);
        System.out.println(sha256);
        if(!cifrado.sha256(desencriptado).equals(sha256)){
            errorSesion();
        }
        //0 publica, 1 privada
        String[] claves = desencriptado.split(",");
        if(claves.length<2){
            errorSesion();
        }
        RequestToken token = twitter.getOAuthRequestToken(claves[0], claves[1]);
        
        twitter.getOAuthAccessToken(token, "");
    }
    
    private void errorSesion() throws Exception{
        Properties configuracion = new Properties();
        configuracion.setProperty("ultima_sesion", "");
        configuracion.store(new FileOutputStream(new File("configuracion.cnf")), "configuracion twitter");
        throw new Exception("error al cargar la sesion");
    }
}
