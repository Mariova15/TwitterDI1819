/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import logica.Excepciones.CifradoExcepcion;
import org.apache.commons.codec.binary.Base64;
import sun.security.rsa.RSAKeyPairGenerator;

/**
 * clase para la encriptacion y desencriptacion
 * @author adan
 */
public class CifradoRsa {
     private transient String clavePublica = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0Lv5ZGKLImBjd9or4Rugf1uGUdRpUxTgOf8On5eazqd3aNVIH572tz3N2aMYo1e0qA3jeeXlM6YHOE0JWf6C02USRx0YOA99uP6+814de0AE3FnJRsQmeL9/UkhdWSqqEo2YR/HnmkD+EI+XSVXb/qG0LAl3grwErjVAPSHhIvUVL7T/oOEb5WtAdqG+tMMRQyMa+5i0EaKappW7VxYJGPp2WOqV01jTOHfLR3JRcB8kEyCX5QpwFV5Kp9EZo36PyjjaXOJEV9Gbt7/xBsIKUGKvWZUHhaqu+FsDEsnhnZ63pOL5C46M7qaE1XR70WzB94dQpX5gVqmv/HkoM2tVqwIDAQAB";
     private transient String clavePrivada = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDQu/lkYosiYGN32ivhG6B/W4ZR1GlTFOA5/w6fl5rOp3do1Ugfnva3Pc3ZoxijV7SoDeN55eUzpgc4TQlZ/oLTZRJHHRg4D324/r7zXh17QATcWclGxCZ4v39SSF1ZKqoSjZhH8eeaQP4Qj5dJVdv+obQsCXeCvASuNUA9IeEi9RUvtP+g4Rvla0B2ob60wxFDIxr7mLQRopqmlbtXFgkY+nZY6pXTWNM4d8tHclFwHyQTIJflCnAVXkqn0Rmjfo/KONpc4kRX0Zu3v/EGwgpQYq9ZlQeFqq74WwMSyeGdnrek4vkLjozupoTVdHvRbMH3h1ClfmBWqa/8eSgza1WrAgMBAAECggEAMblVw7Al7N6BU3/JjCqEpVIsQToUjRIgCDDWTvZSjoAeVf7Y24+GvkgezYWozOCLzKziT5uQailW9vaDD8ktaSZeoEoyBsjJ7lPhMSBubXnmrodFB3Kap5EWx4ctlZwwP5Mzf5AE2B47Z76ND49AhPwIa6UlryMTClztHKMBes3xNqC3mFXWo3dPXvrD/YFVsSkyFD0JKh4dn9ck1NhGspxBzaonxI2lvfbdfKqmiLR/X3kRZPEWZl2W1DVz3mVZ6p8aZ5sIUbmVZ1uJC3r/XE8GZRcA31w+cQLxRMJO4cH42PG0JSbJ+8+SAAqxOV1hjOaU1xIvAh9y/ktah9pqYQKBgQDvTjNVxaBPFb4ceII9rq34vt9V9sjzqSfhKD72LdmZayZK7DYpIlB+BPRq04WI9L4J+SgV6QXlmj6BwDiUa6ZgotJLNCoFhPn42ozjLM6SDlAe3Jn++YgeZ+rcJjqsJzCsawqs9vbaSmG6JS2pa8qluBjr6mUzO2B+CD/E61+DBwKBgQDfS8wShZF+RyaAkaq0Ptyzd1yTRxKQvNc2Jiww3GlOxVPNVhOjexgJc1Km55/bxrFy+ibugP6mCbfeprja8o+R/9UxlR4LD2q0XH7N+3eWkoNV+sKorkoWFgX9Np48JtrAE7HoqfW0CsLkiaHoCnw3fvx/7IEJnfe2nXmWY7W7PQKBgDa/DC12LcuJ7Nk0lI8p9DDw8cUH1UcxDqeK/oRxTtOzWKpRSb61U/bQiV4bD/SaERPVBV+l1KyHUf97CzahPgnjlfqQb4CdwFY3IX4jVQrC7X9x9mUHjyVtnqRItqmFubPoNSQGx8h+tcbRFJYqy8yHxmPKVuaWkeejWXtiYZJnAoGBAMPjhcp13JZQKsm9gGiRgSH7w338aqAI2i/Juxi+M7AD0imfqqjPMt4PimGbbxTpsQAxzXeMOFlGy0QLNL327tZg/hF6RJnlU3GYdzGSum2gaJFD5AMAq0PG3Egv4w7X8y1v2bJHb6Ibkq/UwfGDSUeSIkbDHsxmBUrxyRmSrXntAoGAC4izobAxNfIiXq18FPrDu43ntm58aICxU9ZEujmqGebSwf+8qIdihI35tITVHWeD6rT/VefxJfj4NAYARIkn6CFnvYnuuCJHKBzeTVWGmaVwYljhJC7pqAQIcC2JLrrVC+QfuXiQkPXSAvtgL57CFtEEZ2vy40GnFj2GPBqNLK0=";
     
     /**
      * devuelve la clave privada
      * @return
      * @throws logica.Excepciones.CifradoExcepcion 
      */
     private PrivateKey getPrivateKey() throws CifradoExcepcion{
         try {
             PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(clavePrivada.getBytes()));
             KeyFactory kf = KeyFactory.getInstance("RSA");
             RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(spec);
             return privKey;
         } catch (Exception ex) {
            throw new Excepciones.CifradoExcepcion(ex);
         }
     }
     
     /**
      * devuelve la clave publica
      * @return
      * @throws logica.Excepciones.CifradoExcepcion 
      */
     private PublicKey getPublicKey() throws CifradoExcepcion{
         try {
             X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(clavePublica.getBytes()));
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
             PublicKey publKey = keyFactory.generatePublic(keySpec);
             return publKey;
         } catch (Exception ex) {
            throw new Excepciones.CifradoExcepcion(ex);
         }
     }
     
     /**
      * saca por pantalla nuevas keys, primero la publica después la privada
      */
     private void nuevasKeys(){
            //volver publico, usarlo, copiar las claves y volverlo a poner privado
            RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            System.out.println(Base64.encodeBase64String(keyPair.getPublic().getEncoded()));
            System.out.println(Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));
     }
     
     /**
      * encripta el string pasado por parametro
      * @param msng
      * @return
      * @throws logica.Excepciones.CifradoExcepcion 
      */
     public byte[] encriptar(byte[] msng) throws CifradoExcepcion {
         try{
         Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
        return cipher.doFinal(msng);
        } catch (Exception ex) {
             throw new Excepciones.CifradoExcepcion(ex);
         } 
     }
     
     /**
      * desencripta el buffer de bytes pasado por parametro
      * @param data
      * @return
      * @throws logica.Excepciones.CifradoExcepcion 
      */
     public byte[] desencriptar(byte[] data) throws CifradoExcepcion{
         try {
             Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
             cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
             return cipher.doFinal(data);
         } catch (Exception ex) {
             throw new Excepciones.CifradoExcepcion(ex);
         } 
     }
     
     /**
      * devuelve el sha256 del string pasado como parametro
      * @param msng
      * @return
      * @throws logica.Excepciones.CifradoExcepcion 
      */
     public String sha256(String msng) throws CifradoExcepcion{
         try {
             MessageDigest digest = MessageDigest.getInstance("SHA-256");
             byte[] hash = digest.digest(msng.getBytes(StandardCharsets.UTF_8));
             return Base64.encodeBase64String(hash);
         } catch (NoSuchAlgorithmException ex) {
             throw new Excepciones.CifradoExcepcion(ex);
         }
     }
}
