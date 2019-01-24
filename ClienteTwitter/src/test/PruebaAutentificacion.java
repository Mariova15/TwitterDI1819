package test;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.api.FavoritesResources;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author adan
 */
public class PruebaAutentificacion {

    public static void main(String[] args) throws TwitterException, IOException, URISyntaxException, InterruptedException {
        //añadimos los token de nuestra aplicacion
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("HkM0LS2MQLnh2c4ECo40HHYmP", "uPhHLyAJahCC4ui9nC2AHvzYZEEGDnNyoshKsXIL845C3MVFWG"); //poco seguro

        //pedimos un login
        RequestToken request = twitter.getOAuthRequestToken();
        Desktop.getDesktop().browse(new URI(request.getAuthorizationURL())); //esto lanza el navegador por defecto con la pagina de login

        System.out.println("Introduce el pin: "); //se introduce el pin
        String pin = new Scanner(System.in).nextLine();
        AccessToken access = null;
        try {
            access = twitter.getOAuthAccessToken(request, pin);
        } catch (TwitterException te) { //comprobamos errores
            if (te.getStatusCode() == 401) {
                System.out.println("Unable to get the access token.");
            } else {
                te.printStackTrace();
            }
            System.exit(-1); //salimos de la aplicación si hay
        }

        System.out.println("acceso con éxito");
        twitter.setOAuthAccessToken(access); //añadimos el access token a la aplicacion

        //Actualizar tu estado
        //OJO HAY QUE CAMBIARLO CADA VEZ QUE SE EJECUTE PORQUE CASCA en gráfico 
        //con poner un getText serviría
        Status tweetEscrito = twitter.updateStatus("Prueba tweet Escrito 13.");
        /*//para el usuario
        status.getUser().getScreenName();*/

        //Recuperar listado de ultimos tweets escritos y publicar los últimos tweets
        //Para publicar más de 20 tweets
        Paging pagina = new Paging(1, 100);//page number, number per page

        //ConcurrentLinkedQueue<Long> retweetsQueue = new ConcurrentLinkedQueue<>(); Para retweetear
        while (true) {
            for (Status status : twitter.getHomeTimeline(pagina)) {
                System.out.println(status.getUser().getName() + " : " + status.getText()); //timeline
                /*//Para retweetear
                Long toRetweetId = retweetsQueue.poll();
                if (toRetweetId != null) {
                try {
                Status retweetStatus = twitter.retweetStatus(toRetweetId);
                System.out.println("retweetStatus "+retweetStatus);
                } catch (TwitterException e) {
                //log.error(e.getMessage());
                }
                }*/
                /*//Para borrar un retweet
                    List<Status> retweets = twitter.getRetweets(statusID);
                for (Status retweet : retweets)
                        if (retweet.getUser().getId() == twitter.getId())
                        twitter.destroyStatus(retweet.getId());
                 */
 /*//Para grafico para mostrar foto del usuario que publica el tweet
                User usuario = twitter.showUser(twitter.getId());
                URL url = new URL(usuario.getProfileImageURL());
                ImageIcon img = new ImageIcon(url);
                jLabelImagenUsuario.setTcon(img);
                actualizar();//llevaria el método que muestra el timeline
                 */
            }
            Thread.sleep(10000); //cada 10 segundos se recarga
            System.out.println("");
        }

    }

}
