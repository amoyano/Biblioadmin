/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PrestamoSuspendidosView.java
 *
 * Created on 16-may-2011, 19:20:25
 */

package biblioadmin2;

import biblioadmin2.PrestamoSuspendidosVerSocioView;
import com.db4o.ObjectSet;
import java.util.Vector;
import javax.swing.JFrame;
import locators.ModelLocator;
import managers.SocioManager;
import models.SancionSocio;
import models.Socio;
import org.jdesktop.application.Action;

/**
 *
 * @author agustinmoyano
 */
public class PrestamoSuspendidosView extends javax.swing.JFrame {

    /** Creates new form PrestamoSuspendidosView */
    public PrestamoSuspendidosView() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listSuspendidos = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(biblioadmin2.BiblioAdmin2App.class).getContext().getResourceMap(PrestamoSuspendidosView.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        listSuspendidos.setName("listSuspendidos"); // NOI18N
        jScrollPane1.setViewportView(listSuspendidos);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(biblioadmin2.BiblioAdmin2App.class).getContext().getActionMap(PrestamoSuspendidosView.class, this);
        jButton1.setAction(actionMap.get("cancelar")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("verSocio")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setAction(actionMap.get("buscarSocios")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 350, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .add(jButton2)
                .add(18, 18, 18)
                .add(jButton1)
                .add(95, 95, 95))
            .add(layout.createSequentialGroup()
                .add(108, 108, 108)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(jButton3))
                    .add(jLabel1))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton3)
                .add(31, 31, 31)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton2)
                    .add(jButton1))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void cancelar() {
        this.dispose();
    }

    @Action
    public void buscarSocios() {
         ObjectSet<SancionSocio> sancionesSocios;
         Vector<Socio> sociosSuspendidos = new Vector();

          sancionesSocios  = SocioManager.getInstance().buscarSancionados();
          for (int i = 0; i < sancionesSocios.size(); i++) {
            sociosSuspendidos.add(sancionesSocios.get(i).socio);

        }
//PREGUNTAR TULIO PARA QUE SE VEA EL CAMPO TITULO Y QUE ACEPTE OBJECTSET LA LISTA
      this.listSuspendidos.setListData(sociosSuspendidos);

    }

    @Action
    public void verSocio() {
      //SI NO SELECCIONO MOSTRAR DIALOGO
        ModelLocator.getInstance().socioSuspendido = (Socio) this.listSuspendidos.getSelectedValue();
      if (prestamoSuspendidosVerSocioView == null) {
            JFrame mainFrame = BiblioAdmin2App.getApplication().getMainFrame();
            prestamoSuspendidosVerSocioView = new PrestamoSuspendidosVerSocioView();
            prestamoSuspendidosVerSocioView.setLocationRelativeTo(mainFrame);
        }
        prestamoSuspendidosVerSocioView.show();
    }

    public JFrame prestamoSuspendidosVerSocioView;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listSuspendidos;
    // End of variables declaration//GEN-END:variables

}
