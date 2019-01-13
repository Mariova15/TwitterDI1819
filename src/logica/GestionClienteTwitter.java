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
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author Mario
 */
public class GestionClienteTwitter {

    /**
     * Método que devuelve una lista de seguidores del propietario de la cuenta.
     *
     * @param twitter
     * @return
     */
    public static List<User> ListaFollowers(Twitter twitter) {
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
     * @param twitter
     * @return
     */
    public static List<User> ListaFollows(Twitter twitter) {
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

}
