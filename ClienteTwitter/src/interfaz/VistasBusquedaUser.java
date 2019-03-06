package interfaz;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 *
 * @author Shaila
 */
public class VistasBusquedaUser extends javax.swing.JDialog {

    private Twitter twitter;
    private DefaultListModel users = new DefaultListModel();
    private String user;
    List<User> listaUsuarios;

    /**
     * Creates new form User
     */
    public VistasBusquedaUser(java.awt.Dialog parent, boolean modal, final Twitter twitter, final String user) {
        super(parent, modal);
        initComponents();
        //parent.dispose();//cerramos al padre una vez entrado
        setLocationRelativeTo(null);
        //Establecer el título de la aplicación
        setTitle("TTCSASM");
        ponLaAyuda();
        this.twitter = twitter;
        this.user = user;

        jLabelBusqueda.setText(user);

        pintarTimeLine(twitter);

        //Falta programar acciones
        jListContenidoBúsqueda.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                    
                    User tweetTL = (User) users.elementAt(jListContenidoBúsqueda.getSelectedIndex());
                    String opcion = this.opciones(tweetTL);
                    new JDialogCombobox(VistasBusquedaUser.this, rootPaneCheckingEnabled, opcion,tweetTL.getScreenName()).setVisible(true);
                    
                } catch (TwitterException ex) {
                    JOptionPane.showMessageDialog(VistasBusquedaUser.this, "ha ocurrido un error:\n"+ex.getMessage());
                }
                
               
            }
            
            private String opciones(User tUser) throws TwitterException{
                String opcion = new String();
                Twitter twitter = TwitterFactory.getSingleton();
                if (twitter.showFriendship(twitter.getScreenName(), tUser.getScreenName()).isSourceFollowingTarget())
                    opcion="dejar de seguir";
                else
                    opcion="seguir";
                return opcion;
            }
        });

    }

    public VistasBusquedaUser(java.awt.Dialog parent, boolean modal, final Twitter twitter, String cadenaTitulo, List<User> listaUsuarios) {
        super(parent, modal);
        initComponents();
        //parent.dispose();//cerramos al padre una vez entrado
        setLocationRelativeTo(null);
        //Establecer el título de la aplicación
        setTitle("TTCSASM");
        this.twitter = twitter;
        this.listaUsuarios = listaUsuarios;
        pintarUsuarios();

        jLabelBusqueda.setText("Usuarios " + cadenaTitulo);

        //Falta programar acciones
        jListContenidoBúsqueda.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.

                Status tweetTL = (Status) users.elementAt(jListContenidoBúsqueda.getSelectedIndex());

                String accion = (String) JOptionPane.showInputDialog(
                        e.getComponent().getParent(),
                        "Seleccione opcion",
                        "Selector de acciones sobre tweet",
                        JOptionPane.QUESTION_MESSAGE, null,
                        new Object[]{"Responder", "Retweet", "Favorito"}, "opcion 2");

                if (accion != null) {
                    switch (accion) {
                        case "Responder":
                            String respuesta = JOptionPane.showInputDialog("Escribe tu respuesta");
                            GestionClienteTwitter.responderTwit(twitter, respuesta,
                                    tweetTL.getId());
                            break;
                        case "Retweet":
                            GestionClienteTwitter.retwitear(twitter, tweetTL.getId());
                            break;
                        case "Favorito":
                            GestionClienteTwitter.hacerFavorito(twitter, tweetTL.getId());
                            break;
                    }
                }

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

        jListContenidoBúsqueda.setModel(users);
        jListContenidoBúsqueda.setCellRenderer(new VistaUser());
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
                .addComponent(jScrollPaneHomeTL, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
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

        for (User userannadir : GestionClienteTwitter.buscarUsuario(twitter, user)) {
            users.addElement(userannadir);
        }
    }
    
    private void pintarUsuarios() {
        for (User userannadir : listaUsuarios) {
            users.addElement(userannadir);
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
