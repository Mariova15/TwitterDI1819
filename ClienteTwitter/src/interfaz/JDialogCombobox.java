package interfaz;

import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import logica.GestionClienteTwitter;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 *
 * @author Shaila
 */
public class JDialogCombobox extends javax.swing.JDialog {

    /**
     * Creates new form JDialogConfirmacionTweet
     */
    private long id;
    private String[] usuariosSiguen, usuariosNoSiguen;
    private String user;

    public JDialogCombobox(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //parent.dispose();//cerramos al padre una vez entrado
        setLocationRelativeTo(null);
        //Establecer el título de la aplicación
        setTitle("TTCSASM");
    }

    public JDialogCombobox(Dialog parent, boolean b, String[] objeto) {
        this(parent, b);
        this.rellenarCombo(objeto);
    }
    
    public JDialogCombobox (Dialog parent, boolean b, String opcion,String user){
        this(parent,b);
        this.user=user;
        this.rellenarCombo(new String[]{opcion});
    }

    public JDialogCombobox(Dialog aThis, boolean b, String[] opciones, long id) {
        this(aThis, b, opciones);
        this.id = id;
    }

    public JDialogCombobox(Dialog aThis, boolean b, String[] opciones, long id, String[] usuariosSiguen, String[] usuariosNoSiguen) {
        this(aThis, b, opciones, id);
        this.usuariosSiguen = usuariosSiguen;
        this.usuariosNoSiguen = usuariosNoSiguen;
    }

    private void rellenarCombo(String[] objeto) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();

        for (String string : objeto) {
            modelo.addElement(string);
        }

        this.jComboBoxStrings.setModel(modelo);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelHeader = new javax.swing.JPanel();
        jButtonAceptar = new javax.swing.JButton();
        jComboBoxStrings = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelHeader.setBackground(new java.awt.Color(255, 255, 255));
        jPanelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonAceptar.setBackground(new java.awt.Color(56, 161, 243));
        jButtonAceptar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonAceptar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAceptar.setText("ACEPTAR");
        jButtonAceptar.setToolTipText("PIEDARA");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });
        jPanelHeader.add(jButtonAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, -1, 30));

        jPanelHeader.add(jComboBoxStrings, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 230, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        String seleccion = (String) this.jComboBoxStrings.getSelectedItem();
        Twitter twitter = TwitterFactory.getSingleton();
        if (seleccion != null) {
            switch (seleccion) {
                case "Añadir cuenta":
                    new Login(false).setVisible(true);
                    dispose();
                    break;
                case "Cambiar cuenta":
                    new Login(true).setVisible(true);
                    dispose();
                    break;
                case "Ir al perfil":
                    new UserProfile(this, true, TwitterFactory.getSingleton()).setVisible(true);
                    break;
                case "Responder":
                    String respuesta = JOptionPane.showInputDialog("Escribe tu respuesta");
                    GestionClienteTwitter.responderTwit(twitter, respuesta,
                            this.id);
                    break;
                case "Retweet":
                    GestionClienteTwitter.retwitear(twitter, this.id);
                    break;
                case "Favorito":
                    GestionClienteTwitter.hacerFavorito(twitter, this.id);
                    break;
                case "Seguir":
                    this.seguirUsuario(twitter);
                    break;
                case "Dejar de seguir":
                    this.dejarSeguirUsuario(twitter);
                    break;
            }
        }
        this.dispose();
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    /**
     * selecciona de una lista al usuario que quieres dejar de seguir o seguir
     *
     * @param seguir
     * @return
     */
    private String seleccionarUsuario(boolean seguir) {
        String[] usuariosLista = (seguir) ? this.usuariosNoSiguen : this.usuariosSiguen;
        String eleccion = seguir ? "seguir" : "dejar de seguir";
        String usuarioSeleccionado = (String) JOptionPane.showInputDialog(this, "a quien quieres " + eleccion,
                "seleccionar usuario", JOptionPane.QUESTION_MESSAGE,
                null, usuariosLista, usuariosLista[0]);

        return usuarioSeleccionado;
    }

    /**
     * sigue al usuario que seleccionas de la lista
     *
     * @param twitter
     */
    private void seguirUsuario(Twitter twitter) {
        String usuarioSeguir=null;
        if(user!=null)
            usuarioSeguir=this.user;
        else
            usuarioSeguir = this.seleccionarUsuario(true);
        if (usuarioSeguir != null) {
            long idUsuarioDejar = GestionClienteTwitter.buscarPrimerUsuario(twitter, usuarioSeguir).getId();
            GestionClienteTwitter.seguirUsuario(twitter, idUsuarioDejar);
        }
    }

    /**
     * deja de seguir al usuario que seleccionas de la lista
     *
     * @param twitter
     */
    private void dejarSeguirUsuario(Twitter twitter) {
        String usuarioDejarSeguir=null;
        if(user!=null)
            usuarioDejarSeguir=this.user;
        else
            usuarioDejarSeguir = this.seleccionarUsuario(true);
        if (usuarioDejarSeguir != null) {
            long idUsuarioSeguir = GestionClienteTwitter.buscarPrimerUsuario(twitter, usuarioDejarSeguir).getId();
            GestionClienteTwitter.dejarDeSeguirUsuario(twitter, idUsuarioSeguir);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JComboBox<String> jComboBoxStrings;
    private javax.swing.JPanel jPanelHeader;
    // End of variables declaration//GEN-END:variables

}
