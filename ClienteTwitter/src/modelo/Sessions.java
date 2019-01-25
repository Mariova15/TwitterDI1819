/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Mario
 */
public class Sessions {

    private List<User> sessions;

    public Sessions() {
        sessions = new ArrayList<>();
    }

    public List<User> getSessions() {
        return sessions;
    }
        
    public void addUser(User user) {
        sessions.add(user);
    }

    public void delUser(User user) {
        sessions.remove(user);
    }

}
