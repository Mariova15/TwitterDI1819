package interfaz;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import logica.GestionClienteTwitter;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author Mario
 */
public class User extends javax.swing.JDialog {

    //Atributos
    private Twitter twitter;
    private DefaultListModel statuses = new DefaultListModel();

    /**
     * Creates new form User
     */
    public User(java.awt.Dialog parent, boolean modal, final Twitter twitter) {
        super(parent, modal);
        initComponents();
        ponLaAyuda();
        //parent.dispose();//cerramos al padre una vez entrado
        setLocationRelativeTo(null);
        //Establecer el título de la aplicación
        setTitle("TTCSASM");
        this.twitter = twitter;
        try {
            pintarTimeLine(twitter);

            File profile = new File(".." + File.separator + "imgs"
                    + File.separator + "user" + File.separator + twitter.showUser(twitter.getId()).getScreenName());

            if (profile.exists()) {
                File userBannerFile = new File("src" + File.separator + "imgs"
                        + File.separator + "users" + File.separator + twitter.showUser(twitter.getId()).getScreenName() + File.separator
                        + twitter.showUser(twitter.getId()).getScreenName() + "-banner.png");
                Image userBannerImage = ImageIO.read(userBannerFile).getScaledInstance(jLabelBanner.getWidth(),
                        jLabelBanner.getHeight(), Image.SCALE_SMOOTH);
                jLabelBanner.setIcon(new ImageIcon(userBannerImage));

                File userProfileFile = new File("src" + File.separator + "imgs"
                        + File.separator + "users" + File.separator + twitter.showUser(twitter.getId()).getScreenName() + File.separator
                        + twitter.showUser(twitter.getId()).getScreenName() + "-profile.png");
                Image userProfileImage = ImageIO.read(userProfileFile).getScaledInstance(jLabelUserImg.getWidth(),
                        jLabelUserImg.getHeight(), Image.SCALE_SMOOTH);
                jLabelUserImg.setIcon(new ImageIcon(userProfileImage));
            } else {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String urlBanner = twitter.showUser(twitter.getId()).getProfileBanner1500x500URL();
                            String urlProfile = twitter.showUser(twitter.getId()).get400x400ProfileImageURL();
                            //sino compruebas si es nulo cuando no tiene banner el programa rompe
                            if (urlBanner != null && !"".equals(urlBanner)) {
                                Image userBannerImage = ImageIO.read(new URL(urlBanner))
                                        .getScaledInstance(jLabelBanner.getWidth(),
                                                jLabelBanner.getHeight(), Image.SCALE_SMOOTH);
                                jLabelBanner.setIcon(new ImageIcon(userBannerImage));
                            }
                            if (urlProfile != null && !"".equals(urlProfile)) {
                                Image userProfileImage = ImageIO.read(new URL(urlProfile))
                                        .getScaledInstance(jLabelUserImg.getWidth(),
                                                jLabelUserImg.getHeight(), Image.SCALE_SMOOTH);
                                jLabelUserImg.setIcon(new ImageIcon(userProfileImage));
                            }
                        } catch (TwitterException | IllegalStateException | IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();

                jListHomeTL.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.

                        Status tweetHomeTL = (Status) statuses.elementAt(jListHomeTL.getSelectedIndex());

                        //Fallo al elegir las opciones de interaccion 
                        String follow, rt = "Retweet", fav = "Favorito";

                        if (tweetHomeTL.isRetweetedByMe()) {
                            rt = "Borrar rt";
                        }
                        if (tweetHomeTL.isFavorited()) {
                            fav = "Borrar fav";
                        }

                        String accion = (String) JOptionPane.showInputDialog(
                                e.getComponent().getParent(),
                                "Seleccione opcion",
                                "Selector de acciones sobre tweet",
                                JOptionPane.QUESTION_MESSAGE, null,
                                new Object[]{"Responder", rt, fav, "Borrar"}, "opcion 2");

                        if (accion != null) {
                            switch (accion) {
                                case "Responder":
                                    String respuesta = JOptionPane.showInputDialog("Escribe tu respuesta");
                                    GestionClienteTwitter.responderTwit(twitter, respuesta,
                                            tweetHomeTL.getId());
                                    pintarTimeLine(twitter);
                                    break;
                                case "Borrar retweet":
                                    GestionClienteTwitter.borrarTwitRetweet(twitter, tweetHomeTL.getId());
                                    pintarTimeLine(twitter);
                                    break;
                                case "Favorito":
                                    GestionClienteTwitter.hacerFavorito(twitter, tweetHomeTL.getId());
                                    pintarTimeLine(twitter);
                                    break;
                                case "Borrar":
                                    GestionClienteTwitter.borrarTwitRetweet(twitter, tweetHomeTL.getId());
                                    pintarTimeLine(twitter);
                                    break;
                            }
                        }

                    }
                });

            }

        } catch (TwitterException | IllegalStateException | IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * Método que incorpora la ayuda en nuestro proyecto.
     */
    private void ponLaAyuda() {
        try {
            //Carga el fichero de ayuda
            File fichero = new File("help" + File.separator + "help_set.hs");
            URL hsURL = fichero.toURI().toURL();

            //Si metemos la carpeta help en src tenemos que quitar lo anterior y poner
            /**
             * URL ayuda = getClass().getResource("ruta"); File
             * ficheroAyudaEnJar = new File(ayuda.toURI());
             */
            //Crea el HelpSet y el HelpBroker
            HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
            HelpBroker hb = helpset.createHelpBroker();

            /**
             * Pone ayuda a item de menu al pulsarlo y a F1 en ventana ppal y
             * secundaria.
             */
            //hb.enableHelpOnButton(jMenuItemAyuda, "aplicacion", helpset);
   
            
            //Al pulsar F1 salta la ayuda
            hb.enableHelpKey(getRootPane(), "aplicacion", helpset);
            // Pone ayuda a item de menu al pulsarlo y a F1 en ventana
            // principal y secundaria.


        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (HelpSetException ex) {
            System.out.println(ex.getMessage());
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
        jButtonRefresh = new javax.swing.JButton();
        jScrollPaneHomeTL = new javax.swing.JScrollPane();
        jListHomeTL = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelBackground.setBackground(new java.awt.Color(244, 244, 244));
        jPanelBackground.setPreferredSize(new java.awt.Dimension(600, 200));

        jPanelUserInfo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelUserInfo.setPreferredSize(new java.awt.Dimension(580, 180));
        jPanelUserInfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelUserImg.setBackground(new java.awt.Color(255, 255, 255));
        jLabelUserImg.setLineBorder(4);
        jLabelUserImg.setLineColor(new java.awt.Color(255, 255, 255));
        jLabelUserImg.setPreferredSize(new java.awt.Dimension(158, 158));
        jPanelUserInfo.add(jLabelUserImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));
        jPanelUserInfo.add(jLabelBanner, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -2, 660, 200));

        jPanelTL.setBackground(new java.awt.Color(255, 255, 255));

        jButtonRefresh.setBackground(new java.awt.Color(56, 161, 243));
        jButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setText("Refrescar");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        jScrollPaneHomeTL.setBackground(new java.awt.Color(255, 255, 255));

        jListHomeTL.setModel(statuses);
        jListHomeTL.setCellRenderer(new Tweet(twitter));
        jScrollPaneHomeTL.setViewportView(jListHomeTL);

        javax.swing.GroupLayout jPanelTLLayout = new javax.swing.GroupLayout(jPanelTL);
        jPanelTL.setLayout(jPanelTLLayout);
        jPanelTLLayout.setHorizontalGroup(
            jPanelTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTLLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addComponent(jScrollPaneHomeTL))
                .addContainerGap())
        );
        jPanelTLLayout.setVerticalGroup(
            jPanelTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneHomeTL, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(jPanelTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelBackgroundLayout.setVerticalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanelBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        pintarTimeLine(twitter);
    }//GEN-LAST:event_jButtonRefreshActionPerformed
    /**
     * Método privado para pintar en un JList el TimeLine.
     *
     * @param twitter
     */
    private void pintarTimeLine(Twitter twitter) {

        for (Status status : GestionClienteTwitter.listarTimeLineUsuario(twitter)) {
            statuses.addElement(status);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JLabel jLabelBanner;
    private jlabelcircular.CLabel jLabelUserImg;
    private javax.swing.JList<String> jListHomeTL;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelTL;
    private javax.swing.JPanel jPanelUserInfo;
    private javax.swing.JScrollPane jScrollPaneHomeTL;
    // End of variables declaration//GEN-END:variables
}
