/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlabeltt;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import twitter4j.Twitter;

/**
 *
 * @author Mario
 */
public class JLabelTT extends JLabel implements Serializable {

    private JLabelTTListener jLabelAccionListener;
    private Component component;
    private Twitter twitter;
    
    public JLabelTT() {
        
        //final String text = this.getText();
        
        this.addMouseListener(new MouseAdapter() {
         @Override
            public void mouseClicked(MouseEvent me) {
                if (jLabelAccionListener != null) {
                    jLabelAccionListener.realizarAccion(component, twitter);
                }
            }
        });
    }

    public JLabelTTListener getjLabelAccionListener() {
        return jLabelAccionListener;
    }

    public void setjLabelAccionListener(JLabelTTListener jLabelAccionListener) {
        this.jLabelAccionListener = jLabelAccionListener;
    }
    
    public Component getComponent() {
        return component;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }
    
    

    public void setComponent(Component component) {
        this.component = component;
    }
        
}
