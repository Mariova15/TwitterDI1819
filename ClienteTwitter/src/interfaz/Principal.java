package interfaz;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import jlabelaccion.JLabelAccion;
import jlabelaccion.JLabelAccionListener;
import jlabelcircular.CLabelListener;
import logica.GestionClienteTwitter;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import utils.Fecha;

public class Principal extends javax.swing.JDialog {

    private static final String RUTA_ICON = ".." + File.separator + "imgs"
            + File.separator + "favicon-96x96.png";

    private Twitter twitter;

    /**
     * Creates new form DialogConfiguracion
     */
    public Principal(java.awt.Frame parent, boolean modal, Twitter twitter) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        //Establecer el título de la aplicación
        setTitle("TTCSASM");

        this.twitter = twitter;

        pintarTimeLine(twitter);

        try {
            jLabelScName.setText("@" + twitter.getScreenName());
            jLabelName.setText(twitter.users().showUser(twitter.getId()).getName());

            String imgUser = twitter.showUser(twitter.getId()).get400x400ProfileImageURL();

            URL url = new URL(imgUser);
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
            FileOutputStream fos = new FileOutputStream("user.png");
            fos.write(response);
            fos.close();

            Image userProfileIMG = new ImageIcon("user.png").getImage().getScaledInstance(
                    jLabelUserImg.getWidth(), jLabelUserImg.getHeight(), Image.SCALE_SMOOTH);

            //jLabelUserImg.setIcon(new ImageIcon("user.png"));
            jLabelUserImg.setIcon(new ImageIcon(userProfileIMG));

            jLabelUserImg.setComponent(this);

            jLabelUserImg.setcLabelListener(new CLabelListener() {
                @Override
                public void realizarAccion(Component component) {
                    new User((Dialog) component, true, twitter).setVisible(true);
                }
            });

            jLabelAccionTT1.setjLabelAccionListener(new JLabelAccionListener() {
                @Override
                public void realizarAccion() {
                    
                    List<Status> buscarTopic = GestionClienteTwitter.buscarTopic(twitter, jLabelAccionTT1.getText());
                    for (Status status : buscarTopic) {
                        System.out.println(status.getUser().getScreenName());
                        System.out.println(status.getText());
                        System.out.println("----------------");
                    }

                }
            });                       
            Trend[] listarTrendingTopic = GestionClienteTwitter.listarTrendingTopic(twitter, 1);
                
            jLabelAccionTT1.setText(listarTrendingTopic[0].getName());
               
           

        } catch (TwitterException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Establecer el logo del a aplicación
        setIconImage(new ImageIcon(getClass().getResource(RUTA_ICON)).getImage());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBackground = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jPanelUser = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jLabelScName = new javax.swing.JLabel();
        jLabelUserImg = new jlabelcircular.CLabel();
        jButtonAnnadirCuenta = new javax.swing.JButton();
        jButtonLogOut = new javax.swing.JButton();
        jPanelAcciones = new javax.swing.JPanel();
        jTextFieldPublicarTwit = new javax.swing.JTextField();
        jButtonPublicarTwit = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jPanelTimeLine = new javax.swing.JPanel();
        jButtonRefresh = new javax.swing.JButton();
        jScrollPaneHomeTL = new javax.swing.JScrollPane();
        jTextAreaHomeTL = new javax.swing.JTextArea();
        jLabelHomeTL = new javax.swing.JLabel();
        jPanelTT = new javax.swing.JPanel();
        jLabelTT = new javax.swing.JLabel();
        jLabelAccionTT1 = new jlabelaccion.JLabelAccion();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));

        jPanelBackground.setBackground(new java.awt.Color(244, 244, 244));

        jPanelHeader.setBackground(new java.awt.Color(56, 161, 243));

        jPanelUser.setBackground(new java.awt.Color(56, 161, 243));
        jPanelUser.setPreferredSize(new java.awt.Dimension(454, 122));

        jLabelName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelName.setForeground(new java.awt.Color(255, 255, 255));

        jLabelScName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelScName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelScName.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabelUserImg.setBackground(new java.awt.Color(255, 255, 255));
        jLabelUserImg.setLineBorder(4);
        jLabelUserImg.setLineColor(new java.awt.Color(255, 255, 255));

        jButtonAnnadirCuenta.setBackground(new java.awt.Color(255, 255, 255));
        jButtonAnnadirCuenta.setForeground(new java.awt.Color(56, 161, 243));
        jButtonAnnadirCuenta.setText("Añadir cuenta");
        jButtonAnnadirCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnadirCuentaActionPerformed(evt);
            }
        });

        jButtonLogOut.setBackground(new java.awt.Color(255, 255, 255));
        jButtonLogOut.setForeground(new java.awt.Color(56, 161, 243));
        jButtonLogOut.setText("Cambiar cuenta");
        jButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelUserLayout = new javax.swing.GroupLayout(jPanelUser);
        jPanelUser.setLayout(jPanelUserLayout);
        jPanelUserLayout.setHorizontalGroup(
            jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelUserImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelScName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelUserLayout.createSequentialGroup()
                        .addComponent(jButtonAnnadirCuenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonLogOut)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelUserLayout.setVerticalGroup(
            jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelUserLayout.createSequentialGroup()
                        .addComponent(jLabelName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelScName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAnnadirCuenta)
                            .addComponent(jButtonLogOut)))
                    .addComponent(jLabelUserImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelAcciones.setBackground(new java.awt.Color(56, 161, 243));

        jTextFieldPublicarTwit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPublicarTwitActionPerformed(evt);
            }
        });

        jButtonPublicarTwit.setBackground(new java.awt.Color(255, 255, 255));
        jButtonPublicarTwit.setForeground(new java.awt.Color(56, 161, 243));
        jButtonPublicarTwit.setText("Twittear");
        jButtonPublicarTwit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPublicarTwitActionPerformed(evt);
            }
        });

        jButtonBuscar.setBackground(new java.awt.Color(255, 255, 255));
        jButtonBuscar.setForeground(new java.awt.Color(56, 161, 243));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAccionesLayout = new javax.swing.GroupLayout(jPanelAcciones);
        jPanelAcciones.setLayout(jPanelAccionesLayout);
        jPanelAccionesLayout.setHorizontalGroup(
            jPanelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPublicarTwit, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAccionesLayout.createSequentialGroup()
                        .addGap(0, 97, Short.MAX_VALUE)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonPublicarTwit, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelAccionesLayout.setVerticalGroup(
            jPanelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPublicarTwit, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPublicarTwit)
                    .addComponent(jButtonBuscar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelUser, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelAcciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelAcciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelUser, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelTimeLine.setBackground(new java.awt.Color(255, 255, 255));

        jButtonRefresh.setBackground(new java.awt.Color(56, 161, 243));
        jButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setText("Refrescar");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        jTextAreaHomeTL.setEditable(false);
        jTextAreaHomeTL.setColumns(20);
        jTextAreaHomeTL.setRows(5);
        jScrollPaneHomeTL.setViewportView(jTextAreaHomeTL);

        jLabelHomeTL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelHomeTL.setText("Home timeline");

        javax.swing.GroupLayout jPanelTimeLineLayout = new javax.swing.GroupLayout(jPanelTimeLine);
        jPanelTimeLine.setLayout(jPanelTimeLineLayout);
        jPanelTimeLineLayout.setHorizontalGroup(
            jPanelTimeLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTimeLineLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTimeLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneHomeTL, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                    .addComponent(jLabelHomeTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelTimeLineLayout.setVerticalGroup(
            jPanelTimeLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTimeLineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelHomeTL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneHomeTL, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRefresh)
                .addContainerGap())
        );

        jPanelTT.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabelTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTT.setText("Trending topics");

        jLabelAccionTT1.setText("#namm2019");
        jLabelAccionTT1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanelTTLayout = new javax.swing.GroupLayout(jPanelTT);
        jPanelTT.setLayout(jPanelTTLayout);
        jPanelTTLayout.setHorizontalGroup(
            jPanelTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTTLayout.createSequentialGroup()
                        .addComponent(jLabelTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanelTTLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelAccionTT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanelTTLayout.setVerticalGroup(
            jPanelTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelAccionTT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelBackgroundLayout = new javax.swing.GroupLayout(jPanelBackground);
        jPanelBackground.setLayout(jPanelBackgroundLayout);
        jPanelBackgroundLayout.setHorizontalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                        .addComponent(jPanelTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelTimeLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelBackgroundLayout.setVerticalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelTimeLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPublicarTwitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPublicarTwitActionPerformed
        GestionClienteTwitter.publicarTwit(twitter, jTextFieldPublicarTwit.getText());
        JOptionPane.showMessageDialog(this, "Twit publicado");

        //JOptionPane.showMessageDialog(this, "Error al publicar el twit");

    }//GEN-LAST:event_jButtonPublicarTwitActionPerformed

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        pintarTimeLine(twitter);
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonAnnadirCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnadirCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAnnadirCuentaActionPerformed

    private void jButtonLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogOutActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonLogOutActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        //new User(this, true, twitter).setVisible(true);
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jTextFieldPublicarTwitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPublicarTwitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPublicarTwitActionPerformed

    private void pintarTimeLine(Twitter twitter) {

        GestionClienteTwitter.listarTimeLineSeguidos(twitter);

        //String userTimeLine = "";
        String homeTimeLine = "";

        /*for (Status status : GestionClienteTwitter.listarTimeLineUsuario(twitter)) {
        userTimeLine += status.getUser().getName() + "\n";
        userTimeLine += "@" + status.getUser().getScreenName() + "\n";
        userTimeLine += Fecha.timeFormat(status.getCreatedAt()) + "\n";
        userTimeLine += status.getText() + "\n";
        userTimeLine += "--------------------------------------" + "\n";
        }*/
        for (Status status : GestionClienteTwitter.listarTimeLineSeguidos(twitter)) {
            homeTimeLine += status.getUser().getName() + "\n";
            homeTimeLine += "@" + status.getUser().getScreenName() + "\n";
            homeTimeLine += Fecha.timeFormat(status.getCreatedAt()) + "\n";
            homeTimeLine += status.getText() + "\n";
            homeTimeLine += "--------------------------------------" + "\n";
        }

        jTextAreaHomeTL.setText(homeTimeLine);
        //jTextAreaUserTimeLine.setText(userTimeLine);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnadirCuenta;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonLogOut;
    private javax.swing.JButton jButtonPublicarTwit;
    private javax.swing.JButton jButtonRefresh;
    private jlabelaccion.JLabelAccion jLabelAccionTT1;
    private javax.swing.JLabel jLabelHomeTL;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelScName;
    private javax.swing.JLabel jLabelTT;
    private jlabelcircular.CLabel jLabelUserImg;
    private javax.swing.JPanel jPanelAcciones;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelTT;
    private javax.swing.JPanel jPanelTimeLine;
    private javax.swing.JPanel jPanelUser;
    private javax.swing.JScrollPane jScrollPaneHomeTL;
    private javax.swing.JTextArea jTextAreaHomeTL;
    private javax.swing.JTextField jTextFieldPublicarTwit;
    // End of variables declaration//GEN-END:variables
}
