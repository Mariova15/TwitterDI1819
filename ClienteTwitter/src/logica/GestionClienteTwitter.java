/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.PagableResponseList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author Mario
 */
public class GestionClienteTwitter {
    
    private Configuracion configuracion;

    public GestionClienteTwitter() {
        configuracion=new Configuracion();
    }
    
    

    /**
     * Metodo que twittea un mensaje.
     *
     * @param twitter con la información de usuario.
     * @param twit Cadena con el mensaje a twittear.
     */
    public static void publicarTwit(Twitter twitter, String twit) {
        try {
            twitter.updateStatus(twit);

        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método que borra un twit o un retweet por su ID.
     *
     * @param twitter con la información de usuario.
     * @param statusID ID del twit a borrar.
     */
    public static void borrarTwitRetweet(Twitter twitter, long statusID) {
        try {
            twitter.destroyStatus(statusID);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que retwitea un twit por su ID.
     *
     * @param twitter con la información de usuario.
     * @param statusID ID del twit a retwitear.
     */
    public static void retwitear(Twitter twitter, long statusID) {
        try {
            twitter.retweetStatus(statusID);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que hace un twit favorito por su ID.
     *
     * @param twitter con la información de usuario.
     * @param statusID ID del twit a hacer favorito.
     */
    public static void hacerFavorito(Twitter twitter, long statusID) {
        try {
            twitter.createFavorite(statusID);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que borra un twit de la lista de favoritos.
     *
     * @param twitter con la información de usuario.
     * @param statusID ID del twit favorito borrar.
     */
    public static void borrarFavorito(Twitter twitter, long statusID) {
        try {
            twitter.destroyFavorite(statusID);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que responde a un twit segun su status.
     *
     * @param twitter con la información de usuario.
     * @param respuesta cadena con el twit a responder.
     * @param statusID ID del twit favorito borrar.
     */
    public static void responderTwit(Twitter twitter, String respuesta, long statusID) {
        try {
            StatusUpdate respuestaTwit = new StatusUpdate(respuesta);
            respuestaTwit.setInReplyToStatusId(statusID);

            twitter.updateStatus(respuestaTwit);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que devuelve una lista de seguidores del propietario de la cuenta.
     *
     * @param twitter con la información de usuario.
     * @return
     */
    public static List<User> listadoFollowers(Twitter twitter) {
        List<User> followers = new ArrayList<User>();
        PagableResponseList<User> page;
        long cursor = -1;

        try {
            while (cursor != 0) {
                page = twitter.getFollowersList(twitter.getId(), cursor, 200);
                followers.addAll(page);
                cursor = page.getNextCursor();
                GestionClienteTwitter.handleRateLimit(page.getRateLimitStatus());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return followers;
    }

    /**
     * Método que devuelve una lista de usuarios seguidos por el propietario de
     * la cuenta.
     *
     * @param twitter con la información de usuario.
     * @return
     */
    public static List<User> listadoFollows(Twitter twitter) {
        List<User> friends = new ArrayList<User>();
        PagableResponseList<User> page;
        long cursor = -1;

        try {
            while (cursor != 0) {
                page = twitter.getFriendsList(twitter.getId(), cursor, 200);
                friends.addAll(page);
                cursor = page.getNextCursor();
                GestionClienteTwitter.handleRateLimit(page.getRateLimitStatus());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return friends;
    }

    /**
     * Método que controla el limite de conexiones con API
     * 
     * @param rls Limite de conexiones dle usuario.
     */
    public static void handleRateLimit(RateLimitStatus rls) {
        int remaining = rls.getRemaining();
        System.out.println("Rate Limit Remaining: " + remaining);
        if (remaining == 0) {
            int resetTime = rls.getSecondsUntilReset() + 5;
            int sleep = (resetTime * 1000);
            try {
                if (sleep > 0) {
                    System.out.println("Rate Limit Exceeded. Sleep for " + (sleep / 1000) + " seconds..");
                    Thread.sleep(sleep);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método que devuelve una lista de twits con un topic determinado.
     *
     * @param twitter con la información de usuario.
     * @param tema a buscar.
     * @return
     */
    public static List<Status> buscarTopic(Twitter twitter, String tema) {
        List<Status> resultadoBusqueda = null;
        try {
            Query temaBuscar = new Query(tema);

            QueryResult search = twitter.search(temaBuscar);

            resultadoBusqueda = search.getTweets();
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoBusqueda;
    }

    /**
     * Método que devuelve una lista de los usuarios que coinciden con la
     * busqueda.
     *
     * @param twitter con la información de usuario.
     * @param usuario a buscar.
     * @return
     */
    public static ResponseList<User> buscarUsuario(Twitter twitter, String usuario) {
        ResponseList<User> usuariosEncontrados = null;
        try {
            usuariosEncontrados = twitter.searchUsers(usuario, 0);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuariosEncontrados;
    }

    /**
     * Método que devuelve una lista de las tendencias mas populares de un lugar
     * determinado.
     *
     * @param twitter con la información de usuario.
     * @param place identificador WOEID para la zona (1 para sacar las
     * tendencias generales).
     * @return
     */
    public static Trend[] listarTrendingTopic(Twitter twitter, int place) {
        Trends placeTrends = null;
        try {
            placeTrends = twitter.getPlaceTrends(place);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return placeTrends.getTrends();
    }

    /**
     * Método que devuelve una lista de los últimos 20 twits de las personas que
     * sigue el usuario.
     *
     * @param twitter con la información de usuario.
     * @return
     */
    public static ResponseList<Status> listarTimeLineSeguidos(Twitter twitter) {
        ResponseList<Status> timeLine = null;
        try {
            timeLine = twitter.getHomeTimeline();
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return timeLine;
    }

    /**
     * Método que devuelve una lista de los últimos 20 twits que ha realizado el
     * usuario.
     *
     * @param twitter con la información de usuario.
     * @return
     */
    public static ResponseList<Status> listarTimeLineUsuario(Twitter twitter) {
        ResponseList<Status> userTimeLine = null;
        try {
            userTimeLine = twitter.getUserTimeline();
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userTimeLine;
    }

    
    /**
     * Método que descarga las img dle usuario cuardandolas en un directorio con su screen name.
     * 
     * @param twitter
     * @param screenName 
     */
    public static void descargarUserIMG(Twitter twitter, String screenName) {
        try {

            File dirUser = new File("src" + File.separator + "imgs"
                    + File.separator + "users" + File.separator + screenName);
            
            dirUser.mkdirs();
            
            FileOutputStream fos = new FileOutputStream("src" + File.separator + "imgs"
                    + File.separator + "users" +File.separator + screenName + File.separator + screenName + "-profile.png");
            fos.write(GestionClienteTwitter.descargaRecursos(
                    twitter.showUser(twitter.getId()).get400x400ProfileImageURL()));
            fos.close();
            fos = new FileOutputStream("src" + File.separator + "imgs"
                    + File.separator + "users" +File.separator + screenName + File.separator + screenName + "-banner.png");
            fos.write(GestionClienteTwitter.descargaRecursos(
                    twitter.showUser(twitter.getId()).getProfileBanner1500x500URL()));
            fos.close();

        } catch (IOException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método que descarga archivos pasandole una url.
     * 
     * @param url dle archivo a descargar
     * @return 
     */
    public static byte[] descargaRecursos(String url) {
        byte[] response = null;
        if(url==null||"".equals(url)) return new byte[0];//o añadir una excepción
        try {
            InputStream in = new BufferedInputStream(new URL(url).openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];
            int n = 0;

            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }

            out.close();
            in.close();
            response = out.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
}
