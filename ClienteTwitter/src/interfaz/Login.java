package interfaz;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import logica.Autentificacion;
import logica.CifradoRsa;
import logica.Excepciones;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Login extends javax.swing.JFrame {

    Autentificacion auto = Autentificacion.getInstance();
    private static final String RUTA_LOGO = ".." + File.separator + "imgs"
            + File.separator + "logo.png";
    private static final String RUTA_ICON = ".." + File.separator + "imgs"
            + File.separator + "favicon-96x96.png";

    /**
     * Creates new form Logg
     */
    public Login() {
        initComponents();
        setLocationRelativeTo(null);

        //Establecer el título de la aplicación
        setTitle("TTCSASM");
        //Establecer una imagen en una label
        jLabelLogo.setIcon(new ImageIcon(getClass().getResource(RUTA_LOGO)));
        //Establecer el logo del a aplicación
        setIconImage(new ImageIcon(getClass().getResource(RUTA_ICON)).getImage());
        /*
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        File[] ficheros = new File("sesiones").listFiles();
        CifradoRsa cifrado = new CifradoRsa();
        for (File fichero : ficheros) {
        
        try {
        FileInputStream reader = new FileInputStream(fichero);
        byte[] bytes = new byte[reader.available()];
        reader.read(bytes);
        String desencriptar = cifrado.desencriptar(bytes);
        modelo.addElement(new CifradoRsa().sha256(desencriptar));
        } catch (FileNotFoundException ex) {
        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Excepciones.CifradoExcepcion ex) {
        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        //}

        //this.jComboBox1.setModel(modelo);
        
        
        jswitchRecordar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jswitchRecordar.isOnOff()) {
                    jComboBox1.setVisible(true);
                } else {
                    jComboBox1.setVisible(false);
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
        jPanelLogin = new javax.swing.JPanel();
        jLabelLogo = new javax.swing.JLabel();
        botonLogin = new javax.swing.JButton();
        jCheckBoxRemember = new javax.swing.JCheckBox();
        botonDelete = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jswitchRecordar = new jswitch.Jswitch();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelBackground.setBackground(new java.awt.Color(244, 244, 244));
        jPanelBackground.setMaximumSize(new java.awt.Dimension(0, 0));
        jPanelBackground.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelBackground.setPreferredSize(new java.awt.Dimension(407, 663));
        jPanelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelLogin.setBackground(new java.awt.Color(255, 255, 255));

        jLabelLogo.setPreferredSize(new java.awt.Dimension(309, 309));

        botonLogin.setBackground(new java.awt.Color(56, 161, 243));
        botonLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonLogin.setForeground(new java.awt.Color(255, 255, 255));
        botonLogin.setText("Login");
        botonLogin.setToolTipText("PIEDARA");
        botonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLoginActionPerformed(evt);
            }
        });

        jCheckBoxRemember.setText("Remember");

        botonDelete.setBackground(new java.awt.Color(56, 161, 243));
        botonDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonDelete.setForeground(new java.awt.Color(255, 255, 255));
        botonDelete.setText("Borrar conexión");
        botonDelete.setToolTipText("PIEDARA");
        botonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDeleteActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jswitchRecordar.setBackgroundColor(new java.awt.Color(56, 161, 243));
        jswitchRecordar.setOnOff(false);
        jswitchRecordar.setPreferredSize(new java.awt.Dimension(50, 30));

        javax.swing.GroupLayout jPanelLoginLayout = new javax.swing.GroupLayout(jPanelLogin);
        jPanelLogin.setLayout(jPanelLoginLayout);
        jPanelLoginLayout.setHorizontalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxRemember)
                            .addComponent(jswitchRecordar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLoginLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        jPanelLoginLayout.setVerticalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jswitchRecordar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxRemember, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonDelete)
                .addGap(210, 210, 210))
        );

        jPanelBackground.add(jPanelLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 330, 640));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDeleteActionPerformed

        //Tiene que borrar la conexion seleccionada en el combox de las recordadas
        try {
            this.auto.borrarUltimaSesion();
        } catch (Excepciones.CifradoExcepcion ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonDeleteActionPerformed

    private void botonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLoginActionPerformed

        // try {
        /*try {
        //trata de cargar la ultima sesion
        Twitter cargarUltimaSesion = auto.cargarUltimaSesion();
        new Principal(this, true, cargarUltimaSesion).setVisible(true);
        } catch (IOException | Excepciones.CifradoExcepcion ex) {
        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Excepciones.SesionExcepcion ex) {
        try {
        //si no puede inicia una nueva conexion
        Twitter nuevaConexion = auto.nuevaConexion(jCheckBoxRemember.isSelected());
        if (nuevaConexion != null) {
        new Principal(this, true, nuevaConexion).setVisible(true);
        }
        } catch (TwitterException | Excepciones.CifradoExcepcion | IOException | URISyntaxException ex1) {
        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
        }
        }*/
        //Prueba Login
        

        if (jswitchRecordar.isOnOff()) {
            //Parte donde carga la sesion del combox
            
            //Twitter cargarUltimaSesion = auto.cargarSesion(accessToken, twitter, RUTA_LOGO);
            //new Principal(this, true, cargarUltimaSesion).setVisible(true);
        } else {
            Twitter nuevaConexion = null;
            try {
                nuevaConexion = auto.nuevaConexion(jCheckBoxRemember.isSelected());
            } catch (TwitterException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Excepciones.CifradoExcepcion ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (nuevaConexion != null) {
            new Principal(this, true, nuevaConexion).setVisible(true);
        }
        }

        /*  String itemAt = this.jComboBox1.getItemAt(jComboBox1.getSelectedIndex());
            Twitter twitter = TwitterFactory.getSingleton();
            auto.cargarSesion(new File("sesiones"+File.separator+itemAt),itemAt, twitter);
            new Principal(this, true, twitter).setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Excepciones.CifradoExcepcion ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Excepciones.SesionExcepcion ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_botonLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonDelete;
    private javax.swing.JButton botonLogin;
    private javax.swing.JCheckBox jCheckBoxRemember;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelLogin;
    private jswitch.Jswitch jswitchRecordar;
    // End of variables declaration//GEN-END:variables
}
