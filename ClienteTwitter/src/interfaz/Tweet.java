package interfaz;

import java.awt.Component;
import java.awt.Image;
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
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import twitter4j.Status;
import twitter4j.Twitter;
import utils.Fecha;

/**
 *
 * @author Mario
 */
public class Tweet extends javax.swing.JPanel implements ListCellRenderer<Object> {

    private Twitter twitter;

    /**
     * Creates new form Tweet
     */
    public Tweet(Twitter twitter) {
        initComponents();
        ponLaAyuda();
        this.twitter = twitter;
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
        cLabelAvatar = new jlabelcircular.CLabel();
        jLabelTTScreenName = new jlabeltt.JLabelTT();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaTexto = new javax.swing.JTextArea();
        jLabelDate = new javax.swing.JLabel();

        jPanelBackground.setBackground(new java.awt.Color(255, 255, 255));

        cLabelAvatar.setBackground(new java.awt.Color(255, 255, 255));
        cLabelAvatar.setLineBorder(4);
        cLabelAvatar.setLineColor(new java.awt.Color(56, 161, 243));

        jLabelTTScreenName.setText("ScreenName");
        jLabelTTScreenName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jTextAreaTexto.setColumns(20);
        jTextAreaTexto.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jTextAreaTexto.setLineWrap(true);
        jTextAreaTexto.setRows(5);
        jScrollPane1.setViewportView(jTextAreaTexto);

        jLabelDate.setText("Date");

        javax.swing.GroupLayout jPanelBackgroundLayout = new javax.swing.GroupLayout(jPanelBackground);
        jPanelBackground.setLayout(jPanelBackgroundLayout);
        jPanelBackgroundLayout.setHorizontalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cLabelAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                        .addComponent(jLabelTTScreenName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDate)
                        .addGap(0, 170, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelBackgroundLayout.setVerticalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBackgroundLayout.createSequentialGroup()
                        .addGroup(jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTTScreenName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cLabelAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private jlabelcircular.CLabel cLabelAvatar;
    private javax.swing.JLabel jLabelDate;
    private jlabeltt.JLabelTT jLabelTTScreenName;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaTexto;
    // End of variables declaration//GEN-END:variables

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        try {
            Status status = (Status) value;
            jLabelTTScreenName.setText("@" + status.getUser().getScreenName());

            Image image;

            image = ImageIO.read(new URL(status.getUser().getBiggerProfileImageURL())).getScaledInstance(cLabelAvatar.getWidth(),
                    cLabelAvatar.getHeight(), Image.SCALE_SMOOTH);
            cLabelAvatar.setIcon(new ImageIcon(image));

            jTextAreaTexto.setText(status.getText());

            jLabelDate.setText(Fecha.timeFormat(status.getCreatedAt()));

            return this;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Tweet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Tweet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
}
