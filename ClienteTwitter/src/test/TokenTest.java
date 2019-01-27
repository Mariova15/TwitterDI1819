/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 *
 * @author Mario
 */
public class TokenTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TwitterException {
        AccessToken accessAk = new AccessToken("183983304-vBo41cqFoY8t9KUybo1sdbV90WcbBC7TWB7jDVum", "vm0SSRvelmzBMfdkX4d73AndC7iW8UoILWT8gtg4OOT1l");
        System.out.println(accessAk.toString());
        AccessToken accessKy = new AccessToken("1036565687924412416-1IjfMcs8eP89aBDnOv17BtAj3UCr7N", "IglgpOtTKzs26ON2y0HZjfcFbvRPeKNdW4cN4ggWllveo");
        System.out.println(accessKy.toString());
        
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("HkM0LS2MQLnh2c4ECo40HHYmP", "uPhHLyAJahCC4ui9nC2AHvzYZEEGDnNyoshKsXIL845C3MVFWG");

        twitter.setOAuthAccessToken(accessAk);
        System.out.println(twitter.users().showUser(accessAk.getUserId()));
        twitter.setOAuthAccessToken(accessKy);
        System.out.println(twitter.users().showUser(accessKy.getUserId()));
        
    }

}
