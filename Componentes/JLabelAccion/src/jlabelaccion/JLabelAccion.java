/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlabelaccion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Mario
 */
public class JLabelAccion extends JLabel implements Serializable {

    private JLabelAccionListener jLabelAccionListener;
    
    public JLabelAccion() {
        this.addMouseListener(new MouseAdapter() {
         @Override
            public void mouseClicked(MouseEvent me) {
                if (jLabelAccionListener != null) {
                    jLabelAccionListener.realizarAccion();
                }
            }
        });
    }

    public JLabelAccionListener getjLabelAccionListener() {
        return jLabelAccionListener;
    }

    public void setjLabelAccionListener(JLabelAccionListener jLabelAccionListener) {
        this.jLabelAccionListener = jLabelAccionListener;
    }
    
        
}
