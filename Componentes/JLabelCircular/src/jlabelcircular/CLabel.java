package jlabelcircular;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.AbstractBorder;
/**
 * @web htpp://www.jc-mouse.net
 * @author Mouse
 */
public class CLabel extends JLabel {
    
   private AbstractBorder circleBorder = new CircleBorder();       
   private int lineBorder=1; 
   private Color lineColor= Color.BLACK;
   private Component component;    
   private CLabelListener cLabelListener;
   
    /** Constructor */
     public CLabel()
     {
        Dimension d = new Dimension(100,100);
        setSize(d);
        setPreferredSize(d);       
        //setText("CLabel");
        setOpaque(true);
        setHorizontalAlignment(CENTER);       
        setVisible(true);       
        setBorder(circleBorder);
        this.addMouseListener(new MouseAdapter() {
         @Override
            public void mouseClicked(MouseEvent me) {
                if (cLabelListener != null) {
                    cLabelListener.realizarAccion(component);
                }
            }
        });
     }
     
    //Color de borde
    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color color) {
        circleBorder = new CircleBorder(color, lineBorder);
        lineColor = color;
        setBorder(circleBorder); 
    }

    //Grosor de borde
    public int getLineBorder() {
        return lineBorder;        
    }

    public void setLineBorder(int lineBorder) {
        circleBorder = new CircleBorder(lineColor, lineBorder);
        this.lineBorder = lineBorder;        
        setBorder(circleBorder); 
    }
    
    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public CLabelListener getcLabelListener() {
        return cLabelListener;
    }

    public void setcLabelListener(CLabelListener cLabelListener) {
        this.cLabelListener = cLabelListener;
    }
}
