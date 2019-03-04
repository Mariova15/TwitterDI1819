/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.GestionClienteTwitter;
import twitter4j.AccountSettings;
import twitter4j.FilterQuery;
import twitter4j.Location;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Mario
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //https://github.com/kantega/storm-twitter-workshop/wiki/Basic-Twitter-stream-reading-using-Twitter4j

            /*ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setOAuthConsumerKey("HkM0LS2MQLnh2c4ECo40HHYmP")
            .setOAuthConsumerSecret("uPhHLyAJahCC4ui9nC2AHvzYZEEGDnNyoshKsXIL845C3MVFWG")
            .setOAuthAccessToken("1083393956451442688-PkSdwCWSYN3k8DG26ROC5nJyXAmUNJ")
            .setOAuthAccessTokenSecret("pdyxQHkDtjonuLaUu4qZkuNmC8Nq3Jk8E4EH7n1sIThV6");
            TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
            
            twitterStream.addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
            System.out.println(status.getText());
            }
            
            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void onTrackLimitationNotice(int i) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void onScrubGeo(long l, long l1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void onStallWarning(StallWarning sw) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void onException(Exception excptn) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            });
            
            FilterQuery tweetFilterQuery = new FilterQuery(); // See
            tweetFilterQuery.track(new String[]{"Bieber", "Teletubbies"}); // OR on keywords
            tweetFilterQuery.locations(new double[][]{new double[]{-126.562500, 30.448674},
            new double[]{-61.171875, 44.087585
            }});
            
            tweetFilterQuery.language(new String[]{"en"});
            
            twitterStream.filter(tweetFilterQuery);*/
            Twitter twitter = TwitterFactory.getSingleton();
            twitter.setOAuthConsumer("HkM0LS2MQLnh2c4ECo40HHYmP", "uPhHLyAJahCC4ui9nC2AHvzYZEEGDnNyoshKsXIL845C3MVFWG"); //poco seguro

            RequestToken request = twitter.getOAuthRequestToken();
            //Desktop.getDesktop().browse(new URI(request.getAuthorizationURL()));

            System.out.println("Introduce el pin: "); //se introduce el pin
            //String pin = new Scanner(System.in).nextLine();
            AccessToken access = new AccessToken("183983304-vBo41cqFoY8t9KUybo1sdbV90WcbBC7TWB7jDVum", "vm0SSRvelmzBMfdkX4d73AndC7iW8UoILWT8gtg4OOT1l");
            /*try {
            access = twitter.getOAuthAccessToken(request, pin);
            } catch (TwitterException te) { //comprobamos errores
            if (te.getStatusCode() == 401) {
            System.out.println("Unable to get the access token.");
            } else {
            te.printStackTrace();
            }
            System.exit(-1); //salimos de la aplicación si hay
            }*/

            System.out.println("acceso con éxito");
            twitter.setOAuthAccessToken(access);
            System.out.println(access.getScreenName());

            //GestionClienteTwitter.publicarTwit(twitter, "TEST");
            //GestionClienteTwitter.borrarTwitRetweet(twitter, 1084859566444199938L);
            //GestionClienteTwitter.retwitear(twitter, 1084744008398557184L);
            /*Query usuarioBuscar = new Query("dinocazares");
            QueryResult search = twitter.search(usuarioBuscar);*/
            /*List<Status> tweets = GestionClienteTwitter.buscarTopic(twitter, "dinocazares");
            for (Status tweet : tweets) {
            System.out.println(tweet.getText());
            System.out.println(tweet.getId());
            }*/
            /*System.out.println(GestionClienteTwitter.listadoFollowers(twitter).size());
            System.out.println(GestionClienteTwitter.listadoFollows(twitter).size());*/
            //String temp = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In in dapibus erat. Mauris iaculis pulvinar eros. Donec euismod ante a orci mattis, eget suscipit mi porttitor. Integer erat sapien, laoreet sed purus nec, feugiat commodo lorem. Nulla nunc leo, ultrices sit amet quam eu di";
            /*ResponseList<Status> homeTimeline = twitter.getHomeTimeline(new Paging(1));
            for (Status status : homeTimeline) {
            System.out.println(status.getUser().getScreenName());
            }
            System.out.println("-------------------------------------");
            Desktop.getDesktop().browse(new URI(request.getAuthorizationURL()));
            System.out.println("Introduce el pin: "); //se introduce el pin
            String pin = new Scanner(System.in).nextLine();
            access = twitter.getOAuthAccessToken(request, pin);
            twitter.setOAuthAccessToken(access);
            System.out.println(access.getScreenName());
            System.out.println(access.getToken());
            System.out.println(access.getTokenSecret());
            ResponseList<Status> pruebaTL = twitter.getHomeTimeline(new Paging(1));
            for (Status status : pruebaTL) {
            System.out.println(status.getUser().getScreenName());
            }*/
            /*ResponseList<User> searchUsers = GestionClienteTwitter.buscarUsuario(twitter, "akillatem");
            for (User searchUser : searchUsers) {
            System.out.println(searchUser.toString());
            }
            ResponseList<Status> userTimeline = twitter.getUserTimeline(twitter.getId());
            for (Status status : userTimeline) {
            System.out.println(status.getId()+" "+status.getUser().getName());
            }*/
            /*Trends placeTrends = twitter.getPlaceTrends(1);
            for (Trend trend : placeTrends.getTrends()) {
            System.out.println(trend.getName());
            }*/
            
            
            for (User user : GestionClienteTwitter.buscarUsuario(twitter, "dinocazares")) {
                user.get400x400ProfileImageURL();
                user.getScreenName();
                user.getCreatedAt();
                user.getFollowersCount();
                user.getFriendsCount();
                user.getStatusesCount();
            }
            
        } catch (TwitterException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
