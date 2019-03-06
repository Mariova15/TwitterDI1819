/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.JDialogCombobox;
import interfaz.Principal;
import java.awt.Dialog;
import static java.awt.image.ImageObserver.WIDTH;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import modelo.Tweet;
import modelo.Usuario;
import twitter4j.Location;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 *
 * @author Mario
 */
public class GestionClienteTwitter {

    private Configuracion configuracion;

    public GestionClienteTwitter() {
        configuracion = new Configuracion();
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

    public static User buscarPrimerUsuario(Twitter twitter, String usuario) {
        ResponseList<User> usuariosEncontrados = null;
        try {
            usuariosEncontrados = twitter.searchUsers(usuario, 0);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuariosEncontrados.get(0);
    }

    public static boolean comprobarSiSigue(Twitter twitter, long usuarioComprobar) {
        boolean targetFollowingSource = false;
        try {
            System.out.println(twitter.getId());
            System.out.println("PRUEBA " + usuarioComprobar);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Relationship showFriendship = twitter.showFriendship(twitter.getId(), usuarioComprobar);
            targetFollowingSource = showFriendship.isSourceFollowingTarget();
            //targetFollowingSource = showFriendship.isSourceFollowedByTarget(); MUTUALS
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return targetFollowingSource;
    }

    /**
     * Método que sigue a un usuario por su id.
     *
     * @param twitter
     * @param idUsuarioSeguir
     */
    public static void seguirUsuario(Twitter twitter, long idUsuarioSeguir) {
        try {
            twitter.createFriendship(idUsuarioSeguir);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que deja de seguir a un usuario por su id.
     *
     * @param twitter
     * @param idUsuarioSeguir
     */
    public static void dejarDeSeguirUsuario(Twitter twitter, long idUsuarioSeguir) {
        try {
            twitter.destroyFriendship(idUsuarioSeguir);
        } catch (TwitterException ex) {
            Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * * Método que devuelve una lista de las 10 tendencias mas populares
     * pasandole una busqueda previa.
     *
     * @param arrayTrend
     * @return
     */
    public static DefaultListModel listar10TrendingTopic(Trend[] arrayTrend) {
        DefaultListModel listaModeloTT = new DefaultListModel();

        for (int i = 0; i < 10; i++) {
            listaModeloTT.addElement(arrayTrend[i].getName());
        }
        return listaModeloTT;
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
     * Método que descarga las img dle usuario cuardandolas en un directorio con
     * su screen name.
     *
     * @param twitter
     * @param screenName
     */
    public static void descargarUserIMG(Twitter twitter, String screenName) {
        try {
            File dirUser = new File("imgs"
                    + File.separator + "users" + File.separator + screenName);

            dirUser.mkdirs();

            FileOutputStream fos = new FileOutputStream("imgs"
                    + File.separator + "users" + File.separator + screenName + File.separator + screenName + "-profile.png");
            fos.write(GestionClienteTwitter.descargaRecursos(
                    twitter.showUser(twitter.getId()).get400x400ProfileImageURL()));
            fos.close();
            fos = new FileOutputStream("imgs"
                    + File.separator + "users" + File.separator + screenName + File.separator + screenName + "-banner.png");
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
        if (url == null || "".equals(url)) {
            return new byte[0];//o añadir una excepción
        }
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

    /**
     * Método que devuelve todos los seguidores de un usuario.
     *
     * @param twitter con la info del usuario que usa la aplicación.
     * @param screenName del usuario del que se quiere buscar los seguidores.
     * @return
     */
    public static List<Usuario> listadoFollowersUsuariodeterminado(Twitter twitter, String screenName) {
        List<User> followers = new ArrayList<User>();
        List<Usuario> followersCambio = new ArrayList<Usuario>();
        PagableResponseList<User> page;
        long cursor = -1;

        try {
            while (cursor != 0) {
                page = twitter.getFollowersList(screenName, cursor, 200);
                followers.addAll(page);
                cursor = page.getNextCursor();
                GestionClienteTwitter.handleRateLimit(page.getRateLimitStatus());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        for (User follower : followers) {
            followersCambio.add(new Usuario(follower.getScreenName(), follower.getName()));
        }

        return followersCambio;
    }

    /**
     * Método que devuelve todos los seguidos de un usuario.
     *
     * @param twitter con la info del usuario que usa la aplicación.
     * @param screenName del usuario del que se quiere buscar los seguidos.
     * @return
     */
    public static List<Usuario> listadoFollowsUsuariodeterminado(Twitter twitter, String screenName) {
        List<User> friends = new ArrayList<User>();
        List<Usuario> followersCambio = new ArrayList<Usuario>();
        PagableResponseList<User> page;
        long cursor = -1;

        try {
            while (cursor != 0) {
                page = twitter.getFriendsList(screenName, cursor, 200);
                friends.addAll(page);
                cursor = page.getNextCursor();
                GestionClienteTwitter.handleRateLimit(page.getRateLimitStatus());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        for (User follower : friends) {
            followersCambio.add(new Usuario(follower.getScreenName(), follower.getName()));
        }

        return followersCambio;
    }

    /**
     * Método que devuelve una lista de objetos Tweet con el name, screenName,
     * fecha de publicación y texto del mismo.
     *
     * @param twitter con la info del usuario que usa la aplicación.
     * @param screenName del usuario del que se quiere buscar el timeline.
     * @param fechaComienzo
     * @param fechaFin
     * @return lista de objetos Tweet con el name, screenName, fecha de
     * publicación y texto del mismo.
     */
    public static List<Tweet> listarTodoTimeLineUsuarioEntreFechas(Twitter twitter, String screenName, Date fechaComienzo, Date fechaFin) {
        List<Status> statuses = new ArrayList<>();
        List<Status> statusesFecha = new ArrayList<>();
        List<Tweet> listaTweets = new ArrayList<>();
        int pageno = 1;
        while (true) {
            try {
                System.out.println("getting tweets");
                int size = statuses.size(); // actual tweets count we got
                Paging page = new Paging(pageno, 200);
                statuses.addAll(twitter.getUserTimeline(screenName, page));
                System.out.println("total got : " + statuses.size());
                if (statuses.size() == size) {
                    break;
                } // we did not get new tweets so we have done the job
                pageno++;
                Thread.sleep(1000); // 900 rqt / 15 mn <=> 1 rqt/s
            } catch (TwitterException e) {
                System.out.println(e.getErrorMessage());
            } catch (InterruptedException ex) {
                Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        for (Status status : statuses) {
            if (status.getCreatedAt().getTime() >= fechaFin.getTime() && status.getCreatedAt().getTime() <= fechaComienzo.getTime()) {
                statusesFecha.add(status);
            }
        }

        for (Status status : statusesFecha) {
            listaTweets.add(new Tweet(status.getUser().getName(), status.getUser().getScreenName(),
                    status.getCreatedAt(), status.getText()));
        }

        return listaTweets;
    }

    /**
     * Método que devuelve una lista de objetos Tweet con el name, screenName,
     *
     * @param twitter con la info del usuario que usa la aplicación.
     * @param screenName del usuario del que se quiere buscar el timeline.
     * @return lista de objetos Tweet con el name, screenName
     */
    public static List<Tweet> listarTodoTimeLineUsuario(Twitter twitter, String screenName) {
        List<Status> statuses = new ArrayList<>();
        List<Tweet> listaTweets = new ArrayList<>();
        int pageno = 1;
        while (true) {
            try {
                System.out.println("getting tweets");
                int size = statuses.size(); // actual tweets count we got
                Paging page = new Paging(pageno, 200);
                statuses.addAll(twitter.getUserTimeline(screenName, page));
                System.out.println("total got : " + statuses.size());
                if (statuses.size() == size) {
                    break;
                } // we did not get new tweets so we have done the job
                pageno++;
                Thread.sleep(1000); // 900 rqt / 15 mn <=> 1 rqt/s
            } catch (TwitterException e) {
                System.out.println(e.getErrorMessage());
            } catch (InterruptedException ex) {
                Logger.getLogger(GestionClienteTwitter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        for (Status status : statuses) {
            listaTweets.add(new Tweet(status.getUser().getName(), status.getUser().getScreenName(),
                    status.getCreatedAt(), status.getText()));
        }

        return listaTweets;
    }
    
    /**
     * lanza un dialogo con las opciones del tweet clickeado
     * @param tweetTL
     * @param tweet
     * @param dialogo
     * @throws TwitterException 
     */
    public static void mostrarOpcionesTweet(Status tweetTL, Dialog dialogo) throws TwitterException {
        String[] opciones = null;
        Twitter twitter = TwitterFactory.getSingleton();
        String tweet = tweetTL.getText();
        //aun no está hecho
        //no tiene funcionalidad aun
        String[] usersTweet = getUsersTweet(tweet, tweetTL.getUser().getScreenName());
        String[] usuariosSiguen = getUsuariosSiguen(usersTweet, tweetTL.getUser(), twitter);
        String[] usuariosNoSiguen = getUsuariosNoSiguen(usersTweet, tweetTL.getUser(), twitter);
        opciones = getOpciones(usuariosSiguen, usersTweet);

        new JDialogCombobox(dialogo, true, opciones, tweetTL.getId(), usuariosSiguen, usuariosNoSiguen).setVisible(true);
    }

    /**
     * saca una lista de opciones segun las dos listas pasadas por parametro
     *
     * @param usuariosSiguen
     * @param usersTweet
     * @return
     */
    private static String[] getOpciones(String[] usuariosSiguen, String[] usersTweet) {
        String[] opciones;
        if (usuariosSiguen.length > 0) {
            if (usuariosSiguen.length == usersTweet.length) {
                opciones = new String[]{"Responder", "Retweet", "Favorito", "Dejar de seguir"};
            } else {
                opciones = new String[]{"Responder", "Retweet", "Favorito", "Seguir", "Dejar de seguir"};
            }

        } else if (usersTweet.length > 1) {
            opciones = new String[]{"Responder", "Retweet", "Favorito", "Seguir"};
        } else {
            opciones = new String[]{"Responder", "Retweet", "Favorito", "Dejar de seguir"};
        }
        return opciones;
    }

    /**
     * consigue los usuarios en un tweet
     *
     * @param tweet
     * @param userTweet
     * @return
     */
    private static String[] getUsersTweet(String tweet, String userTweet) {
        if (tweet.contains("@")) {
            String[] tweetPartido = tweet.split("@");
            List<String> usuarios = new ArrayList<>();

            for (int i = 1; i < tweetPartido.length; i++) {
                String split = tweetPartido[i];
                String arroba = null;
                if (split.contains(":")) {
                    arroba = tweetPartido[i].split(":")[0];
                } else if (split.contains(" ")) {
                    arroba = tweetPartido[i].split(" ")[0];
                } else {
                    arroba = split;
                }

                if (arroba != null) {
                    usuarios.add(arroba.trim());
                }
            }
            usuarios.add(userTweet);
            return usuarios.toArray(new String[usuarios.size()]);
        }
        return new String[]{userTweet};
    }

    /**
     * devuelve una lista con los usuarios que le siguen
     *
     * @param usersTweet
     * @param user
     * @return
     * @throws TwitterException
     */
    private static String[] getUsuariosSiguen(String[] usersTweet, User user, Twitter twitter) throws TwitterException {
        List<String> usuarios = new ArrayList<String>();
        if (usersTweet != null && user != null) {
            for (String string : usersTweet) {
                if (!user.getScreenName().equals(string)) {
                    if (twitter.showFriendship(twitter.getScreenName(), string).isSourceFollowingTarget()) {

                        usuarios.add(string);
                    }
                }

            }
            usuarios.add(user.getScreenName());
        }

        return usuarios.toArray(new String[usuarios.size()]);
    }

    /**
     * consigue una lista con los usuarios que no le siguen (usa el metodo
     * anterior asi que es algo lento)
     *
     * @param usersTweet
     * @param user
     * @return
     * @throws TwitterException
     */
    private static String[] getUsuariosNoSiguen(String[] usersTweet, User user, Twitter twitter) throws TwitterException {
        //TODO: cambiar
        if (usersTweet != null) {

            List<String> usuarios = new ArrayList<String>();
            if (usersTweet != null && user != null) {
                for (String string : usersTweet) {
                    if (!user.getScreenName().equals(string)) {
                        if (!twitter.showFriendship(twitter.getScreenName(), string).isSourceFollowingTarget()) {
                            usuarios.add(string);
                        }
                    }

                }
                usuarios.add(user.getScreenName());
            }

            return usuarios.toArray(new String[usuarios.size()]);
        }
        return new String[0];
    }

}
