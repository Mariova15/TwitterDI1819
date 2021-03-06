/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.io.File;
import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;

/**
 *
 * @author sergio
 */
public class JavaHelp extends javax.swing.JDialog {

    /**
     * Creates new form MainWindow
     */
    public JavaHelp(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);

        initComponents();
        //parent.dispose();//cerramos al padre una vez entrado
        setLocationRelativeTo(null);
        //Establecer el título de la aplicación
        setTitle("TTCSASM");
        ponLaAyuda();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jButtonPrincipal = new javax.swing.JButton();
        jButtonLogin = new javax.swing.JButton();
        jButtonInformes = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        ayudaMenuItem = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonPrincipal.setText("Principal");
        jButtonPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrincipalActionPerformed(evt);
            }
        });

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jButtonInformes.setText("Informes");
        jButtonInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInformesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jButtonLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jButtonPrincipal)
                .addGap(42, 42, 42)
                .addComponent(jButtonInformes)
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPrincipal)
                    .addComponent(jButtonLogin)
                    .addComponent(jButtonInformes))
                .addContainerGap(186, Short.MAX_VALUE))
        );

        jMenu2.setText("Archivo");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edición");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Ayuda");

        ayudaMenuItem.setText("Mostrar ayuda");
        ayudaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ayudaMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(ayudaMenuItem);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrincipalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonPrincipalActionPerformed

    private void jButtonInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInformesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonInformesActionPerformed

    private void ayudaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ayudaMenuItemActionPerformed

    }//GEN-LAST:event_ayudaMenuItemActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void ponLaAyuda() {
        try {
            // Carga el fichero de ayuda
            File fichero = new File("help" + File.separator + "help_set.hs");
            URL hsURL = fichero.toURI().toURL();

            //ClassLoader cl = JavaHelp.class.getClassLoader();
            //URL hsURL = HelpSet.findHelpSet(cl,"help/help_set.hs");
            //HelpSet hs = new HelpSet(null, hsURL);
            // Crea el HelpSet y el HelpBroker
            HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
            HelpBroker hb = helpset.createHelpBroker();

            // Pone ayuda a item de menu al pulsarlo y a F1 en ventana
            // principal y secundaria.
            hb.enableHelpOnButton(ayudaMenuItem, "ventana_principal", helpset);
            hb.enableHelpKey(getRootPane(), "ventana_principal", helpset);
            hb.enableHelpKey(jButtonLogin, "ventana_login", helpset);
            hb.enableHelpKey(jButtonPrincipal, "ventana_principal", helpset);
            hb.enableHelpKey(jButtonInformes, "ventana_informes", helpset);
            
            hb.enableHelpOnButton(jButtonLogin, "ventana_login", helpset);
            hb.enableHelpOnButton(jButtonPrincipal, "ventana_principal", helpset);
            hb.enableHelpOnButton(jButtonInformes, "ventana_informes", helpset);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ayudaMenuItem;
    private javax.swing.JButton jButtonInformes;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonPrincipal;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
