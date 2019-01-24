/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaneltweet;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Mario
 */
public class JPanelTweet extends JPanel {

    private String screenName, name, time, text;
    
    private JLabel jLabelScreenName , jLabelName, jLabelTime, jLabelText;
    
    public JPanelTweet() {
        
        this.setLayout(new BorderLayout());
        
        jLabelScreenName = new JLabel(screenName);        
        jLabelName = new JLabel(name);
        jLabelTime = new JLabel(time);
        jLabelText = new JLabel(text);
        
        this.add(jLabelScreenName , BorderLayout.NORTH);
        this.add(jLabelName);
        this.add(jLabelTime);
        this.add(jLabelText);
        
    }

    public String getsCreenName() {
        return screenName;
    }

    public void setsCreenName(String sCreenName) {
        this.screenName = sCreenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
}
