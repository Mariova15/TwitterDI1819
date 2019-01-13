package logica;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
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
                System.out.println("Unable to get the access token");
            } else {
                te.printStackTrace();
            }
            System.exit(-1); //salimos de la aplicación si hay
        }

        System.out.println("acceso con éxito");

        twitter.setOAuthAccessToken(access); //añadimos el access token a la aplicacion

        //Actualizar tu estado
        Status tweetEscrito = twitter.updateStatus("Prueba tweetEscrito.");
        
        while (true) {
            for (Status status : twitter.getHomeTimeline()) {
                System.out.println(status.getUser().getName() + " : " + status.getText()); //timeline

            }
            Thread.sleep(10000); //cada 10 segundos se recarga
        }

    }

}
