package interfaz;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import jlabelcircular.CLabelListener;
import logica.GestionClienteTwitter;
import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class Principal extends javax.swing.JDialog {

    private static final String RUTA_ICON ="/imgs/favicon-96x96.png";

    private Twitter twitter;
    private DefaultListModel statuses = new DefaultListModel();

    /**
     * Creates new form DialogConfiguracion
     */
    public Principal(java.awt.Frame parent, boolean modal, final Twitter twitter) {
        super(parent, modal);
        initComponents();
        //parent.dispose();//cerramos al padre una vez entrado
        setLocationRelativeTo(null);
        //Establecer el título de la aplicación
        setTitle("TTCSASM");
        ponLaAyuda();
        this.twitter = twitter;

        pintarTimeLine(twitter);

        try {
            jLabelScName.setText("@" + twitter.users().showUser(twitter.getId()).getScreenName());
            jLabelName.setText(twitter.users().showUser(twitter.getId()).getName());

            File profile = new File(".." + File.separator + "imgs"
                    + File.separator + "user" + File.separator + twitter.showUser(twitter.getId()).getScreenName());
            if (profile.exists()) {
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

                            String url = twitter.showUser(twitter.getId()).getBiggerProfileImageURL();
                            if (url != null || !"".equals(url)) {
                                Image userProfileImage = ImageIO.read(new URL(url))
                                        .getScaledInstance(jLabelUserImg.getWidth(),
                                                jLabelUserImg.getHeight(), Image.SCALE_SMOOTH);
                                jLabelUserImg.setIcon(new ImageIcon(userProfileImage));
                            }

                        } catch (TwitterException | IllegalStateException | IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();

            }

            jLabelUserImg.setComponent(this);
            jLabelUserImg.setcLabelListener(new CLabelListener() {
                @Override
                public void realizarAccion(Component component) {
                    // new User((Dialog) component, true, twitter).setVisible(true);
                    String[] objeto = new String[]{"Añadir cuenta", "Cambiar cuenta", "Ir al perfil"};
                    new JDialogCombobox(Principal.this, true, objeto).setVisible(true);

                    //añadir cuenta, volver al login principal, solo el login
                    //cambiar cuenta boton en on
                    //ir al perfil, abrir el dialogo comentado
                }
            });

            //no poner botones en tweet y al hacer click mostrar opciones para interactuar.
            jListTL.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.

                        Status tweetTL = (Status) statuses.elementAt(jListTL.getSelectedIndex());
                        String tweet = tweetTL.getText();
                        String[] opciones = null;
                        //aun no está hecho
                        //no tiene funcionalidad aun
                        String[] usersTweet = getUsersTweet(tweet, tweetTL.getUser().getScreenName());
                        String[] usuariosSiguen = this.getUsuariosSiguen(usersTweet, tweetTL.getUser());
                        String[] usuariosNoSiguen = this.getUsuariosNoSiguen(usersTweet, tweetTL.getUser());
                        opciones = getOpciones(usuariosSiguen, usersTweet);

                        new JDialogCombobox(Principal.this, true, opciones, tweetTL.getId(), usuariosSiguen, usuariosNoSiguen).setVisible(true);
                    } catch (TwitterException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
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
                            opciones = new String[]{"Responder", "Retweet", "Favorito", "dejar de seguir"};
                        } else {
                            opciones = new String[]{"Responder", "Retweet", "Favorito", "seguir", "dejar de seguir"};
                        }

                    } else if (usersTweet.length > 1) {
                        opciones = new String[]{"Responder", "Retweet", "Favorito", "seguir"};
                    } else {
                        opciones = new String[]{"Responder", "Retweet", "Favorito", "dejar de seguir"};
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
                            } else
                                arroba=split;

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

            final ResponseList<Location> availableTrends = twitter.getAvailableTrends();
            DefaultComboBoxModel<String> lugarTendencia = new DefaultComboBoxModel<>();

            for (Location availableTrend : availableTrends) {
                lugarTendencia.addElement(availableTrend.getName());
            }

            jComboBoxTT.setModel((ComboBoxModel<String>) lugarTendencia);

            jComboBoxTT.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jListTT.setModel(GestionClienteTwitter.listar10TrendingTopic(
                            GestionClienteTwitter.listarTrendingTopic(twitter,
                                    availableTrends.get(jComboBoxTT.getSelectedIndex()).getWoeid())));
                }
            });

            //RELLENA JLIST CON 10 TT
            jListTT.setModel(GestionClienteTwitter.listar10TrendingTopic(
                    GestionClienteTwitter.listarTrendingTopic(twitter, 1)));

            jListTT.addMouseListener((new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.

                    VistasBusqueda busqueda = new VistasBusqueda((Dialog) Principal.this, true, twitter, jListTT.getSelectedValue());
                    busqueda.setVisible(true);

                }
            }));

        } catch (TwitterException | IllegalStateException | IOException ex) {
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
        jPanelAcciones = new javax.swing.JPanel();
        jButtonPublicarTwit = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextFieldPublicarTwit = new jTextFieldLimitado.LTextField();
        jPanelTimeLine = new javax.swing.JPanel();
        jButtonRefresh = new javax.swing.JButton();
        jLabelHomeTL = new javax.swing.JLabel();
        jScrollPaneTL = new javax.swing.JScrollPane();
        jListTL = new javax.swing.JList<>();
        jPanelTT = new javax.swing.JPanel();
        jLabelTT = new javax.swing.JLabel();
        jScrollPaneTT = new javax.swing.JScrollPane();
        jListTT = new javax.swing.JList<>();
        jComboBoxTT = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuGenerarInformes = new javax.swing.JMenu();
        jMenuItemGenerarInformes = new javax.swing.JMenuItem();
        jMenuItemAyuda = new javax.swing.JMenuItem();
        jMenuItemSalirAplicacion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(244, 244, 244));

        jPanelBackground.setBackground(new java.awt.Color(244, 244, 244));

        jPanelHeader.setBackground(new java.awt.Color(56, 161, 243));

        jPanelUser.setBackground(new java.awt.Color(56, 161, 243));
        jPanelUser.setPreferredSize(new java.awt.Dimension(454, 122));
        jPanelUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelName.setForeground(new java.awt.Color(255, 255, 255));
        jPanelUser.add(jLabelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 11, 234, 32));

        jLabelScName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelScName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelScName.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanelUser.add(jLabelScName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 49, 234, 32));

        jLabelUserImg.setBackground(new java.awt.Color(255, 255, 255));
        jLabelUserImg.setLineBorder(4);
        jLabelUserImg.setLineColor(new java.awt.Color(255, 255, 255));
        jPanelUser.add(jLabelUserImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 11, -1, -1));

        jPanelAcciones.setBackground(new java.awt.Color(56, 161, 243));
        jPanelAcciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonPublicarTwit.setBackground(new java.awt.Color(255, 255, 255));
        jButtonPublicarTwit.setForeground(new java.awt.Color(56, 161, 243));
        jButtonPublicarTwit.setText("Twittear");
        jButtonPublicarTwit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPublicarTwitActionPerformed(evt);
            }
        });
        jPanelAcciones.add(jButtonPublicarTwit, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 77, -1));

        jButtonBuscar.setBackground(new java.awt.Color(255, 255, 255));
        jButtonBuscar.setForeground(new java.awt.Color(56, 161, 243));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });
        jPanelAcciones.add(jButtonBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 77, -1));

        jTextFieldPublicarTwit.setColumns(20);
        jTextFieldPublicarTwit.setLineWrap(true);
        jTextFieldPublicarTwit.setRows(5);
        jTextFieldPublicarTwit.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextFieldPublicarTwit);

        jPanelAcciones.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 400, 70));

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelUser, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelAcciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelAcciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

        jLabelHomeTL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelHomeTL.setText("Home timeline");

        jScrollPaneTL.setBackground(new java.awt.Color(255, 255, 255));

        jListTL.setModel(statuses);
        jListTL.setCellRenderer(new Tweet(twitter));
        jScrollPaneTL.setViewportView(jListTL);

        javax.swing.GroupLayout jPanelTimeLineLayout = new javax.swing.GroupLayout(jPanelTimeLine);
        jPanelTimeLine.setLayout(jPanelTimeLineLayout);
        jPanelTimeLineLayout.setHorizontalGroup(
            jPanelTimeLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTimeLineLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTimeLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelHomeTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneTL))
                .addContainerGap())
        );
        jPanelTimeLineLayout.setVerticalGroup(
            jPanelTimeLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTimeLineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelHomeTL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneTL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRefresh)
                .addContainerGap())
        );

        jPanelTT.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabelTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTT.setText("Trending topics");

        jScrollPaneTT.setBackground(new java.awt.Color(255, 255, 255));

        jListTT.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPaneTT.setViewportView(jListTT);

        jComboBoxTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanelTTLayout = new javax.swing.GroupLayout(jPanelTT);
        jPanelTT.setLayout(jPanelTTLayout);
        jPanelTTLayout.setHorizontalGroup(
            jPanelTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTT, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(jScrollPaneTT)
                    .addComponent(jComboBoxTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelTTLayout.setVerticalGroup(
            jPanelTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTT, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
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
                        .addComponent(jPanelTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanelTimeLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(1, 1, 1))
        );
        jPanelBackgroundLayout.setVerticalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelTimeLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(102, 153, 255));

        jMenuGenerarInformes.setText("Informes");
        jMenuGenerarInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGenerarInformesActionPerformed(evt);
            }
        });

        jMenuItemGenerarInformes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemGenerarInformes.setText("Generar Informes");
        jMenuItemGenerarInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGenerarInformesActionPerformed(evt);
            }
        });
        jMenuGenerarInformes.add(jMenuItemGenerarInformes);

        jMenuItemAyuda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItemAyuda.setText("Ayuda");
        jMenuGenerarInformes.add(jMenuItemAyuda);

        jMenuItemSalirAplicacion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItemSalirAplicacion.setText("Salir Aplicacion");
        jMenuItemSalirAplicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalirAplicacionActionPerformed(evt);
            }
        });
        jMenuGenerarInformes.add(jMenuItemSalirAplicacion);

        jMenuBar1.add(jMenuGenerarInformes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPublicarTwitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPublicarTwitActionPerformed
        GestionClienteTwitter.publicarTwit(twitter, jTextFieldPublicarTwit.getText());
        jTextFieldPublicarTwit.setText("");

        //Confirmación tweet
        JDialogConfirmacionTweet confirmacionTweet = new JDialogConfirmacionTweet(this, true);
        confirmacionTweet.setVisible(true);


    }//GEN-LAST:event_jButtonPublicarTwitActionPerformed

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        pintarTimeLine(twitter);
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        VistasBusqueda busqueda = new VistasBusqueda(this, true, twitter, jTextFieldPublicarTwit.getText());
        busqueda.setVisible(true);
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jMenuItemGenerarInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGenerarInformesActionPerformed

        JDialogoInformes dialogoInformes = new JDialogoInformes(this, true);
        dialogoInformes.setLocationRelativeTo(null);
        dialogoInformes.setVisible(true);
    }//GEN-LAST:event_jMenuItemGenerarInformesActionPerformed

    private void jMenuGenerarInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGenerarInformesActionPerformed

    }//GEN-LAST:event_jMenuGenerarInformesActionPerformed

    private void jMenuItemSalirAplicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalirAplicacionActionPerformed
        //setVisible(false);
        this.dispose();
        System.exit(0);//Este usarlo para salir porque el dispose me deja una pantalla.
    }//GEN-LAST:event_jMenuItemSalirAplicacionActionPerformed

    /**
     * Método que incorpora la ayuda en nuestro proyecto.
     */
    private void ponLaAyuda() {
        try {

            /*
             File fichero = new File("help"+File.separator+"help_set.hs");
            URL hsURL = fichero.toURI().toURL();
            HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
            HelpBroker hb = helpset.createHelpBroker();
            hb.enableHelpKey(componente, ayuda, helpset);
             */
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
            hb.enableHelpOnButton(this.jMenuItemAyuda, "aplicacion", helpset);

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
            /*homeTimeLine += status.getUser().getName() + "\n";
            homeTimeLine += "@" + status.getUser().getScreenName() + "\n";
            homeTimeLine += Fecha.timeFormat(status.getCreatedAt()) + "\n";
            homeTimeLine += status.getText() + "\n";
            homeTimeLine += "--------------------------------------" + "\n";*/

            statuses.addElement(status);

        }

        //jTextAreaHomeTL.setText(homeTimeLine);
        //jTextAreaUserTimeLine.setText(userTimeLine);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonPublicarTwit;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JComboBox<String> jComboBoxTT;
    private javax.swing.JLabel jLabelHomeTL;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelScName;
    private javax.swing.JLabel jLabelTT;
    private jlabelcircular.CLabel jLabelUserImg;
    private javax.swing.JList<String> jListTL;
    private javax.swing.JList<String> jListTT;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuGenerarInformes;
    private javax.swing.JMenuItem jMenuItemAyuda;
    private javax.swing.JMenuItem jMenuItemGenerarInformes;
    private javax.swing.JMenuItem jMenuItemSalirAplicacion;
    private javax.swing.JPanel jPanelAcciones;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelTT;
    private javax.swing.JPanel jPanelTimeLine;
    private javax.swing.JPanel jPanelUser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneTL;
    private javax.swing.JScrollPane jScrollPaneTT;
    private jTextFieldLimitado.LTextField jTextFieldPublicarTwit;
    // End of variables declaration//GEN-END:variables
}
