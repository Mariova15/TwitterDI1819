package interfaz;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import logica.GestionClienteTwitter;
import modelo.Usuario;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import twitter4j.Twitter;

/**
 *
 * @author Shaila
 */
public class JDialogoInformes extends javax.swing.JDialog {

    static String seleccionUnidadGuardarInforme;
    private Connection connection;
    private File carpetaInformes;
    private Twitter twitter;
    /**
     * Creates new form JDialogConfirmacionTweet
     */
    private long id;

    public JDialogoInformes(java.awt.Dialog parent, boolean modal, Twitter twitter) {
        super(parent, modal);
        initComponents();
        //parent.dispose();//cerramos al padre una vez entrado
        setLocationRelativeTo(null);
        this.twitter = twitter;
        //Establecer el título de la aplicación
        setTitle("TTCSASM");
        ponLaAyuda();
        jButtonInforme1.setEnabled(false);
        jButtonInforme2.setEnabled(false);
        jButtonInforme3.setEnabled(false);

        jLabelSeleccionarInformes.setVisible(false);
        jLabelSeleccionarFechas.setVisible(false);
        jDateChooserFechaComienzo.setVisible(false);
        jLabelSeleccionarFecha.setVisible(false);
        jDateChooserFechaFin.setVisible(false);
        jLabelSeleccionarUsuario.setVisible(false);
        jTextFieldUsuario.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanelHeader = new javax.swing.JPanel();
        jPanelHeader1 = new javax.swing.JPanel();
        jButtonInforme3 = new javax.swing.JButton();
        jButtonSeleccionarCarpeta = new javax.swing.JButton();
        jButtonInforme2 = new javax.swing.JButton();
        jButtonInforme1 = new javax.swing.JButton();
        jLabelSeleccionarUsuario = new javax.swing.JLabel();
        jLabelSeleccionarCarpeta = new javax.swing.JLabel();
        jLabelSeleccionarInformes = new javax.swing.JLabel();
        jLabelSeleccionarFecha = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jDateChooserFechaComienzo = new com.toedter.calendar.JDateChooser();
        jDateChooserFechaFin = new com.toedter.calendar.JDateChooser();
        jLabelSeleccionarFechas = new javax.swing.JLabel();
        jButtonSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelHeader.setBackground(new java.awt.Color(255, 255, 255));
        jPanelHeader.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 16))); // NOI18N
        jPanelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelHeader1.setBackground(new java.awt.Color(56, 161, 243));
        jPanelHeader1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Twitter", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 16))); // NOI18N
        jPanelHeader1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonInforme3.setBackground(new java.awt.Color(255, 255, 255));
        jButtonInforme3.setForeground(new java.awt.Color(56, 161, 243));
        jButtonInforme3.setText("Informes tweets usuario");
        jButtonInforme3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInforme3ActionPerformed(evt);
            }
        });
        jPanelHeader1.add(jButtonInforme3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, 210, -1));

        jButtonSeleccionarCarpeta.setBackground(new java.awt.Color(255, 255, 255));
        jButtonSeleccionarCarpeta.setForeground(new java.awt.Color(56, 161, 243));
        jButtonSeleccionarCarpeta.setText("Seleccionar carpeta...");
        jButtonSeleccionarCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarCarpetaActionPerformed(evt);
            }
        });
        jPanelHeader1.add(jButtonSeleccionarCarpeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 180, -1));

        jButtonInforme2.setBackground(new java.awt.Color(255, 255, 255));
        jButtonInforme2.setForeground(new java.awt.Color(56, 161, 243));
        jButtonInforme2.setText("Informe tweets por fecha");
        jButtonInforme2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInforme2ActionPerformed(evt);
            }
        });
        jPanelHeader1.add(jButtonInforme2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, 210, -1));

        jButtonInforme1.setBackground(new java.awt.Color(255, 255, 255));
        jButtonInforme1.setForeground(new java.awt.Color(56, 161, 243));
        jButtonInforme1.setText("Informes Follows & Followers");
        jButtonInforme1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInforme1ActionPerformed(evt);
            }
        });
        jPanelHeader1.add(jButtonInforme1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 210, -1));

        jLabelSeleccionarUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelSeleccionarUsuario.setText("Introduzca un usuario:");
        jPanelHeader1.add(jLabelSeleccionarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 170, 20));

        jLabelSeleccionarCarpeta.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelSeleccionarCarpeta.setText("Seleccione una carpeta:");
        jPanelHeader1.add(jLabelSeleccionarCarpeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 36, 180, 20));

        jLabelSeleccionarInformes.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelSeleccionarInformes.setText("Seleccione un informe:");
        jPanelHeader1.add(jLabelSeleccionarInformes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 180, 20));

        jLabelSeleccionarFecha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelSeleccionarFecha.setText("y");
        jPanelHeader1.add(jLabelSeleccionarFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 20, 20));
        jPanelHeader1.add(jTextFieldUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 210, -1));
        jPanelHeader1.add(jDateChooserFechaComienzo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));
        jPanelHeader1.add(jDateChooserFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, -1, -1));

        jLabelSeleccionarFechas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelSeleccionarFechas.setText("Seleccione dos fechas entre:");
        jPanelHeader1.add(jLabelSeleccionarFechas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 230, 20));

        jPanelHeader.add(jPanelHeader1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 650, 300));

        jButtonSalir.setBackground(new java.awt.Color(255, 255, 255));
        jButtonSalir.setForeground(new java.awt.Color(56, 161, 243));
        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        jPanelHeader.add(jButtonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 340, 180, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 714, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

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
    private void jButtonInforme3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInforme3ActionPerformed

        try {
            // Creación de informe a partir de listas
            //String screenName = jTextFieldUsuario.getText();     // Necesario si no se pasa explícitamente en el método
            List<modelo.Tweet> listaTweets = GestionClienteTwitter.listarTodoTimeLineUsuario(twitter, jTextFieldUsuario.getText());
            Usuario usuario = new Usuario(jTextFieldUsuario.getText(), listaTweets);
            
            for (modelo.Tweet listaTweet : listaTweets) {
                System.out.println(listaTweet.getTexto());
            }

            List<Usuario> listaUsuarios = new ArrayList<Usuario>();
            listaUsuarios.add(usuario);

            //La encapsulamos en el objeto adecuado
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaUsuarios);
            //Creamos el map para los parámetros
            Map parametros = new HashMap();
            // Si no metemos la línea siguiente el map va sin parametros (vacío)
            //parametros.put(Object key, Object value);
            JasperPrint print = JasperFillManager.fillReport("informes/informe_twitter_listaTUE.jasper", parametros, dataSource);
            //JasperExportManager.exportReportToPdfFile(print, "informes/informe_test_twitter_listaTUE.pdf");
            JasperExportManager.exportReportToPdfFile(print, carpetaInformes.getAbsolutePath() + File.separator + "Informe3.pdf");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //this.dispose();
        /*JOptionPane.showMessageDialog(this, "Se ha generado el informe satisfactoriamente.",
        "Informe 3", JOptionPane.INFORMATION_MESSAGE);*/
        //Confirmación informe
        JDialogConfirmacionInforme confirmacionInforme = new JDialogConfirmacionInforme(this, true);
        confirmacionInforme.setVisible(true);

    }//GEN-LAST:event_jButtonInforme3ActionPerformed

    private void jButtonSeleccionarCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarCarpetaActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        int returnSelection = chooser.showOpenDialog(null);
        if (returnSelection == JFileChooser.APPROVE_OPTION) {
            seleccionUnidadGuardarInforme = chooser.getSelectedFile().getAbsolutePath();
            carpetaInformes = new File(seleccionUnidadGuardarInforme + File.separator + "informes_Ireport");
            if (!carpetaInformes.exists()) {
                carpetaInformes.mkdir();
                seleccionUnidadGuardarInforme = carpetaInformes.getAbsolutePath();
            } else {
                //Poner que seleccione la carpeta
                seleccionUnidadGuardarInforme = carpetaInformes.getAbsolutePath();
            }
        }
        jButtonInforme1.setEnabled(true);
        jButtonInforme2.setEnabled(true);
        jButtonInforme3.setEnabled(true);
        jLabelSeleccionarInformes.setVisible(true);
        jLabelSeleccionarFechas.setVisible(true);
        jDateChooserFechaComienzo.setVisible(true);
        jLabelSeleccionarFecha.setVisible(true);
        jDateChooserFechaFin.setVisible(true);
        jLabelSeleccionarUsuario.setVisible(true);
        jTextFieldUsuario.setVisible(true);
        jLabelSeleccionarCarpeta.setEnabled(false);
        jButtonSeleccionarCarpeta.setEnabled(false);

    }//GEN-LAST:event_jButtonSeleccionarCarpetaActionPerformed

    private void jButtonInforme2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInforme2ActionPerformed

        try {
            // Creación de informe a partir de listas
            List<modelo.Tweet> listaTweets = GestionClienteTwitter.listarTodoTimeLineUsuarioEntreFechas(twitter, twitter.getScreenName(), jDateChooserFechaComienzo.getDate(), jDateChooserFechaFin.getDate());
            Usuario usuario = new Usuario(twitter.getScreenName(), listaTweets);

            List<Usuario> listaUsuarios = new ArrayList<Usuario>();
            listaUsuarios.add(usuario);

            //La encapsulamos en el objeto adecuado
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaUsuarios);
            //Creamos el map para los parámetros
            Map parametros = new HashMap();

            JasperPrint print = JasperFillManager.fillReport("informes/informe_twitter_listaT2F.jasper", parametros, dataSource);
            //JasperExportManager.exportReportToPdfFile(print, "informes/informe_test_twitter_listaT2F.pdf");
            JasperExportManager.exportReportToPdfFile(print, carpetaInformes.getAbsolutePath() + File.separator + "Informe2.pdf");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //this.dispose();
        /*JOptionPane.showMessageDialog(this, "Se ha generado el informe satisfactoriamente.",
        "Informe 2", JOptionPane.INFORMATION_MESSAGE);*/

        JDialogConfirmacionInforme confirmacionInforme = new JDialogConfirmacionInforme(this, true);
        confirmacionInforme.setVisible(true);

    }//GEN-LAST:event_jButtonInforme2ActionPerformed

    private void jButtonInforme1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInforme1ActionPerformed

        try {
            // Creación de informe a partir de listas
            List<Usuario> listaFollowers = GestionClienteTwitter.listadoFollowersUsuariodeterminado(twitter, twitter.getScreenName());
            List<Usuario> listaFollows = GestionClienteTwitter.listadoFollowsUsuariodeterminado(twitter, twitter.getScreenName());
            Usuario usuario = new Usuario(twitter.getScreenName(), listaFollowers, listaFollows);
            // Comprobación: muestra listaFollows por consola
            for (Usuario listaFollow : usuario.getListaFollows()) {
                System.out.println(listaFollow.getName());
            }

            List<Usuario> listaUsuarios = new ArrayList<Usuario>();
            listaUsuarios.add(usuario);

            //La encapsulamos en el objeto adecuado
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaUsuarios);
            //Creamos el map para los parámetros
            Map parametros = new HashMap();
            // Si no metemos la línea siguiente el map va sin parametros (vacío)
            //parametros.put("NOM_PANTALLA", twitter.getScreenName());        // ¿¿NOM_PANTALLA existe en jasper??
            JasperPrint print = JasperFillManager.fillReport("informes/informe_twitter_listaFF.jasper", parametros, dataSource);
            //JasperExportManager.exportReportToPdfFile(print, "informes/informe_test_twitter_listaFF.pdf");
            JasperExportManager.exportReportToPdfFile(print, carpetaInformes.getAbsolutePath() + File.separator + "Informe1.pdf");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //this.dispose();
        /*JOptionPane.showMessageDialog(this, "Se ha generado el informe satisfactoriamente.",
        "Informe 1", JOptionPane.INFORMATION_MESSAGE);*/

        JDialogConfirmacionInforme confirmacionInforme = new JDialogConfirmacionInforme(this, true);
        confirmacionInforme.setVisible(true);

    }//GEN-LAST:event_jButtonInforme1ActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonInforme1;
    private javax.swing.JButton jButtonInforme2;
    private javax.swing.JButton jButtonInforme3;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JButton jButtonSeleccionarCarpeta;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JDateChooser jDateChooserFechaComienzo;
    private com.toedter.calendar.JDateChooser jDateChooserFechaFin;
    private javax.swing.JLabel jLabelSeleccionarCarpeta;
    private javax.swing.JLabel jLabelSeleccionarFecha;
    private javax.swing.JLabel jLabelSeleccionarFechas;
    private javax.swing.JLabel jLabelSeleccionarInformes;
    private javax.swing.JLabel jLabelSeleccionarUsuario;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelHeader1;
    private javax.swing.JTextField jTextFieldUsuario;
    // End of variables declaration//GEN-END:variables

}
