/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jTextFieldLimitado;


import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author alumnop
 */
public class LTextField extends JTextField implements Serializable {
    
    private int numeroMaximo;

    public LTextField() {
        numeroMaximo=280;
        setText("");
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                    if(!Character.isDigit(getText().charAt(getText().length()-1))){
                        //hacer algo
                        setCaretColor(Color.red);
                        setEditable(false);
                        
                    }else{
                if(getText().length()==numeroMaximo)
                    setEnabled(false);
                    }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
        
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==8){
                    String texto = getText().substring(0, getText().length()-1);
                    setText(texto);
                    setEditable(true);
                    setCaretColor(null);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public int getNumeroMaximo() {
        return numeroMaximo;
    }

    public void setNumeroMaximo(int numeroMaximo) {
        this.numeroMaximo = numeroMaximo;
    }
    
    
}
