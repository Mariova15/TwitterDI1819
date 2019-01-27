/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import logica.GestionClienteTwitter;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import utils.Fecha;

/**
 *
 * @author Mario
 */
public class User extends javax.swing.JDialog {

    private Twitter twitter;
    
    /**
     * Creates new form User
     */
    public User(java.awt.Dialog parent, boolean modal, Twitter twitter) {
        super(parent, modal);
        setLocationRelativeTo(null);
        this.twitter = twitter;
        initComponents();
        try {            
            pintarTimeLine(twitter);
            
            String bannerUser = twitter.showUser(twitter.getId()).getProfileBanner600x200URL();
            
            URL url = new URL(bannerUser);
            InputStream in = new BufferedInputStream(url.openStream());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            System.out.println("descargando");
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            System.out.println("descargado");
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            FileOutputStream fos = new FileOutputStream("banner.png");
            fos.write(response);
            fos.close();
            
             Image userBannerIMG = new ImageIcon("banner.png").getImage().getScaledInstance(
                    jLabelBanner.getWidth(), jLabelBanner.getHeight(), Image.SCALE_SMOOTH);
             
            jLabelBanner.setIcon(new ImageIcon(userBannerIMG));
            
            Image userProfileIMG = new ImageIcon("user.png").getImage().getScaledInstance(
                    jLabelUserImg.getWidth(), jLabelUserImg.getHeight(), Image.SCALE_SMOOTH);
            
            jLabelUserImg.setIcon(new ImageIcon(userProfileIMG));
        } catch (TwitterException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBackground = new javax.swing.JPanel();
        jPanelUserInfo = new javax.swing.JPanel();
        jLabelUserImg = new jlabelcircular.CLabel();
        jLabelBanner = new javax.swing.JLabel();
        jPanelTL = new javax.swing.JPanel();
        jLabelTL = new javax.swing.JLabel();
        jScrollPaneUserTimeline = new javax.swing.JScrollPane();
        jTextAreaUserTimeLine = new javax.swing.JTextArea();
        jButtonRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelBackground.setBackground(new java.awt.Color(244, 244, 244));
        jPanelBackground.setPreferredSize(new java.awt.Dimension(600, 200));

        jPanelUserInfo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelUserInfo.setPreferredSize(new java.awt.Dimension(580, 180));

        jLabelUserImg.setPreferredSize(new java.awt.Dimension(158, 158));

        javax.swing.GroupLayout jPanelUserInfoLayout = new javax.swing.GroupLayout(jPanelUserInfo);
        jPanelUserInfo.setLayout(jPanelUserInfoLayout);
        jPanelUserInfoLayout.setHorizontalGroup(
            jPanelUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelUserImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelUserInfoLayout.setVerticalGroup(
            jPanelUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelUserImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanelTL.setBackground(new java.awt.Color(255, 255, 255));

        jLabelTL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTL.setText("Time line");

        jTextAreaUserTimeLine.setEditable(false);
        jTextAreaUserTimeLine.setColumns(20);
        jTextAreaUserTimeLine.setLineWrap(true);
        jTextAreaUserTimeLine.setRows(5);
        jTextAreaUserTimeLine.setWrapStyleWord(true);
        jScrollPaneUserTimeline.setViewportView(jTextAreaUserTimeLine);

        jButtonRefresh.setBackground(new java.awt.Color(56, 161, 243));
        jButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setText("Refrescar");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTLLayout = new javax.swing.GroupLayout(jPanelTL);
        jPanelTL.setLayout(jPanelTLLayout);
        jPanelTLLayout.setHorizontalGroup(
            jPanelTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTLLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneUserTimeline, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanelTLLayout.setVerticalGroup(
            jPanelTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneUserTimeline, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButtonRefresh)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelBackgroundLayout = new javax.swing.GroupLayout(jPanelBackground);
        jPanelBackground.setLayout(jPanelBackgroundLayout);
        jPanelBackgroundLayout.setHorizontalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelBackgroundLayout.setVerticalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        pintarTimeLine(twitter);
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void pintarTimeLine(Twitter twitter) {

        GestionClienteTwitter.listarTimeLineSeguidos(twitter);
        
        String userTimeLine = "";
        //String homeTimeLine = "";

        for (Status status : GestionClienteTwitter.listarTimeLineUsuario(twitter)) {
        userTimeLine += status.getUser().getName() + "\n";
        userTimeLine += "@" + status.getUser().getScreenName() + "\n";
        userTimeLine += Fecha.timeFormat(status.getCreatedAt()) + "\n";
        userTimeLine += status.getText() + "\n";
        userTimeLine += "--------------------------------------" + "\n";
        }
        
        /*for (Status status : GestionClienteTwitter.listarTimeLineSeguidos(twitter)) {
        homeTimeLine += status.getUser().getName() + "\n";
        homeTimeLine += "@" + status.getUser().getScreenName() + "\n";
        homeTimeLine += Fecha.timeFormat(status.getCreatedAt()) + "\n";
        homeTimeLine += status.getText() + "\n";
        homeTimeLine += "--------------------------------------" + "\n";
        }*/

        //jTextAreaHomeTL.setText(homeTimeLine);
        jTextAreaUserTimeLine.setText(userTimeLine);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JLabel jLabelBanner;
    private javax.swing.JLabel jLabelTL;
    private jlabelcircular.CLabel jLabelUserImg;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelTL;
    private javax.swing.JPanel jPanelUserInfo;
    private javax.swing.JScrollPane jScrollPaneUserTimeline;
    private javax.swing.JTextArea jTextAreaUserTimeLine;
    // End of variables declaration//GEN-END:variables
}
