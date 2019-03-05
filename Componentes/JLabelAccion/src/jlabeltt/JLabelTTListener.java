/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlabeltt;

import java.awt.Component;
import twitter4j.Twitter;

/**
 *
 * @author Mario
 */
public interface JLabelTTListener {
    
    public void realizarAccion(Component component,Twitter twitter);
    
}
