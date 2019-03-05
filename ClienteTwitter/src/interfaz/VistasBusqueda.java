package interfaz;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import twitter4j.User;

/**
 *
 * @author Shaila
 */
public class VistasBusqueda extends javax.swing.JDialog {

    private Twitter twitter;
    private DefaultListModel statuses = new DefaultListModel();
    private String busqueda;

    /**
     * Creates new form User
     */
    public VistasBusqueda(java.awt.Dialog parent, boolean modal, final Twitter twitter, final String busqueda) {
        super(parent, modal);
        initComponents();
        //parent.dispose();//cerramos al padre una vez entrado
        setLocationRelativeTo(null);
        //Establecer el título de la aplicación
        setTitle("TTCSASM");
        ponLaAyuda();
        this.twitter = twitter;
        this.busqueda = busqueda;

        jLabelBusqueda.setText(busqueda);

        pintarTimeLine(twitter);

        //Falta programar acciones
        jListContenidoBúsqueda.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                    
                    Status tweetTL = (Status) statuses.elementAt(jListContenidoBúsqueda.getSelectedIndex());
                    String tweet = tweetTL.getText();
                    String[] opciones = null;
                    
                    String[] usersTweet = getUsersTweet(tweet, tweetTL.getUser().getScreenName());
                    String[] usuariosSiguen = this.getUsuariosSiguen(usersTweet, tweetTL.getUser());
                    String[] usuariosNoSiguen = this.getUsuariosNoSiguen(usersTweet, tweetTL.getUser());
                    opciones = getOpciones(usuariosSiguen, usersTweet);
                    
                    //new JDialogCombobox(VistasBusqueda.this, true, opciones, tweetTL.getId(), usuariosSiguen, usuariosNoSiguen).setVisible(true);
                } catch (TwitterException ex) {
                    Logger.getLogger(VistasBusqueda.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            /**
             * saca una lista de opciones segun las dos listas pasadas por
             * parametro
             *
             * @param usuariosSiguen
             * @param usersTweet
             * @return
             */
            private String[] getOpciones(String[] usuariosSiguen, String[] usersTweet) {
                String[] opciones;
                if (usuariosSiguen.length > 0) {
                    if (usuariosSiguen.length == usersTweet.length) {
                        opciones = new String[]{"Responder", "Retweet", "Favorito", "Dejar de seguir"};
                    } else {
                        opciones = new String[]{"Responder", "Retweet", "Favorito", "Seguir", "Dejar de seguir"};
                    }

                } else if (usersTweet.length > 1) {
                    opciones = new String[]{"Responder", "Retweet", "Favorito", "Seguir"};
                } else {
                    opciones = new String[]{"Responder", "Retweet", "Favorito", "Dejar de seguir"};
                }
                return opciones;
            }

            /**
             * consigue los usuarios en un tweet
             *
             * @param tweet
             * @param userTweet
             * @return
             */
            private String[] getUsersTweet(String tweet, String userTweet) {
                if (tweet.contains("@")) {
                    String[] tweetPartido = tweet.split("@");
                    List<String> usuarios = new ArrayList<>();

                    for (int i = 1; i < tweetPartido.length; i++) {
                        String split = tweetPartido[i];
                        String arroba = null;
                        if (split.contains(":")) {
                            arroba = tweetPartido[i].split(":")[0];
                        } else if (split.contains(" ")) {
                            arroba = tweetPartido[i].split(" ")[0];
                        } else {
                            arroba = split;
                        }

                        if (arroba != null) {
                            usuarios.add(arroba.trim());
                        }
                    }
                    usuarios.add(userTweet);
                    return usuarios.toArray(new String[usuarios.size()]);
                }
                return new String[]{userTweet};
            }

            /**
             * devuelve una lista con los usuarios que le siguen
             *
             * @param usersTweet
             * @param user
             * @return
             * @throws TwitterException
             */
            private String[] getUsuariosSiguen(String[] usersTweet, User user) throws TwitterException {
                List<String> usuarios = new ArrayList<String>();
                if (usersTweet != null && user != null) {
                    for (String string : usersTweet) {
                        if (!user.getScreenName().equals(string)) {
                            if (twitter.showFriendship(twitter.getScreenName(), string).isSourceFollowingTarget()) {

                                usuarios.add(string);
                            }
                        }

                    }
                    usuarios.add(user.getScreenName());
                }

                return usuarios.toArray(new String[usuarios.size()]);
            }

            /**
             * consigue una lista con los usuarios que no le siguen (usa el
             * metodo anterior asi que es algo lento)
             *
             * @param usersTweet
             * @param user
             * @return
             * @throws TwitterException
             */
            private String[] getUsuariosNoSiguen(String[] usersTweet, User user) throws TwitterException {
                if (usersTweet != null) {

                    ArrayList<String> usuarios = new ArrayList<String>(Arrays.asList(usersTweet));
                    String[] usuariosSiguen = this.getUsuariosSiguen(usersTweet, user);
                    List<String> siguiendo = Arrays.asList(usuariosSiguen);
                    usuarios.removeAll(siguiendo);
                    return usuarios.toArray(new String[usuarios.size()]);
                }
                return new String[0];
            }
        });

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
        jPanelTL = new javax.swing.JPanel();
        jScrollPaneHomeTL = new javax.swing.JScrollPane();
        jListContenidoBúsqueda = new javax.swing.JList<>();
        jLabelBusqueda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelBackground.setBackground(new java.awt.Color(244, 244, 244));
        jPanelBackground.setPreferredSize(new java.awt.Dimension(600, 200));

        jPanelTL.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPaneHomeTL.setBackground(new java.awt.Color(255, 255, 255));

        jListContenidoBúsqueda.setModel(statuses);
        jListContenidoBúsqueda.setCellRenderer(new Tweet());
        jScrollPaneHomeTL.setViewportView(jListContenidoBúsqueda);

        jLabelBusqueda.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelBusqueda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBusqueda.setText("jLabel1");

        javax.swing.GroupLayout jPanelTLLayout = new javax.swing.GroupLayout(jPanelTL);
        jPanelTL.setLayout(jPanelTLLayout);
        jPanelTLLayout.setHorizontalGroup(
            jPanelTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTLLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneHomeTL, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addComponent(jLabelBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelTLLayout.setVerticalGroup(
            jPanelTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBusqueda)
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneHomeTL, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelBackgroundLayout = new javax.swing.GroupLayout(jPanelBackground);
        jPanelBackground.setLayout(jPanelBackgroundLayout);
        jPanelBackgroundLayout.setHorizontalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelBackgroundLayout.setVerticalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanelBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 330));

        pack();
    }// </editor-fold>//GEN-END:initComponents
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

    private void pintarTimeLine(Twitter twitter) {

        for (Status status : GestionClienteTwitter.buscarTopic(twitter, busqueda)) {
            statuses.addElement(status);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelBusqueda;
    private javax.swing.JList<String> jListContenidoBúsqueda;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelTL;
    private javax.swing.JScrollPane jScrollPaneHomeTL;
    // End of variables declaration//GEN-END:variables
}
