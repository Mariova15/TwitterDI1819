/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.GeoLocation;
import twitter4j.Location;
import twitter4j.PagableResponseList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author Mario
 */
public class GestionClienteTwitter {

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
        List<User> followersList = new ArrayList<User>();
        PagableResponseList<User> users;
        long cursor = -1;

        while (cursor != 0) {
            try {
                users = twitter.getFriendsList(twitter.getId(), cursor);
                cursor = users.getNextCursor();
                for (User user : users) {
                    followersList.add(user);
                }
            } catch (TwitterException ex) {
                Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return followersList;
    }

    /**
     * Método que devuelve una lista de usuarios seguidos por el propietario de
     * la cuenta.
     *
     * @param twitter con la información de usuario.
     * @return
     */
    public static List<User> listadoFollows(Twitter twitter) {
        List<User> followsList = new ArrayList<User>();
        PagableResponseList<User> users;
        long cursor = -1;

        while (cursor != 0) {
            try {
                users = twitter.getFriendsList(twitter.getId(), cursor);
                cursor = users.getNextCursor();
                for (User user : users) {
                    followsList.add(user);
                }
            } catch (TwitterException ex) {
                Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return followsList;
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

    public static void mostrarTrendingTopic(Twitter twitter, int place) {
        try {
            /*try {
            twitter.getAvailableTrends();
            twitter.getPlaceTrends();
            } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            //Returns the top 10 trending topics for a specific WOEID
            ResponseList<Location> locations;
            locations = twitter.getAvailableTrends();
            System.out.println("Showing available trends");
            for (Location location : locations) {
                System.out.println(location.getName() + " (woeid:" + location.getWoeid() + ")");
            }

            //get the present trends of a specific location using it's WOEID like below
            Trends trends = twitter.getPlaceTrends(place);
            for (int i = 0; i < trends.getTrends().length; i++) {
                System.out.println(trends.getTrends()[i].getName());
            }
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
