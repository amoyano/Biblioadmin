/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LibrosView.java
 *
 * Created on 04-may-2011, 17:17:29
 */

package biblioadmin2;

import javax.swing.JFrame;
import org.jdesktop.application.Action;

/**
 *
 * @author agustinmoyano
 */
public class LibrosView extends javax.swing.JFrame {

    /** Creates new form LibrosView */
    public LibrosView() {
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

        btnCerrar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuLibros = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        menuInformes = new javax.swing.JMenu();
        librosNoDevueltosMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(biblioadmin2.BiblioAdmin2App.class).getContext().getActionMap(LibrosView.class, this);
        btnCerrar.setAction(actionMap.get("cerrarVentana")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(biblioadmin2.BiblioAdmin2App.class).getContext().getResourceMap(LibrosView.class);
        btnCerrar.setText(resourceMap.getString("btnCerrar.text")); // NOI18N
        btnCerrar.setName("btnCerrar"); // NOI18N

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        menuLibros.setText(resourceMap.getString("menuLibros.text")); // NOI18N
        menuLibros.setName("menuLibros"); // NOI18N

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem5.setAction(actionMap.get("showBuscarLibrosPorISBN")); // NOI18N
        jMenuItem5.setText(resourceMap.getString("jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        jMenu1.add(jMenuItem5);

        jMenuItem6.setAction(actionMap.get("showBuscarLibrosPorTitulo")); // NOI18N
        jMenuItem6.setText(resourceMap.getString("jMenuItem6.text")); // NOI18N
        jMenuItem6.setName("jMenuItem6"); // NOI18N
        jMenu1.add(jMenuItem6);

        jMenuItem7.setAction(actionMap.get("showBuscarLibrosPorDiscplina")); // NOI18N
        jMenuItem7.setText(resourceMap.getString("jMenuItem7.text")); // NOI18N
        jMenuItem7.setName("jMenuItem7"); // NOI18N
        jMenu1.add(jMenuItem7);

        menuLibros.add(jMenu1);

        jMenuItem3.setAction(actionMap.get("showNuevoLibroView")); // NOI18N
        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        menuLibros.add(jMenuItem3);

        jMenuBar1.add(menuLibros);

        menuInformes.setText(resourceMap.getString("menuInformes.text")); // NOI18N
        menuInformes.setName("menuInformes"); // NOI18N

        librosNoDevueltosMenu.setAction(actionMap.get("showLibrosNoDevueltos")); // NOI18N
        librosNoDevueltosMenu.setText(resourceMap.getString("librosNoDevueltosMenu.text")); // NOI18N
        librosNoDevueltosMenu.setName("librosNoDevueltosMenu"); // NOI18N
        menuInformes.add(librosNoDevueltosMenu);

        jMenuBar1.add(menuInformes);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(151, 151, 151)
                .add(btnCerrar)
                .addContainerGap(167, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(249, Short.MAX_VALUE)
                .add(btnCerrar))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   

    @Action
    public void cerrarVentana() {
        this.dispose();
    }
    
    @Action
    public void showLibrosNoDevueltos() {

        if (librosNoDevueltos == null) {
            JFrame mainFrame = BiblioAdmin2App.getApplication().getMainFrame();
            librosNoDevueltos = new LibrosNoDevueltosView();
            librosNoDevueltos.setLocationRelativeTo(mainFrame);
        }
        librosNoDevueltos.show();

    }

    @Action
    public void showLibrosPorDisciplina() {
        if (librosPorDisciplina == null) {
            JFrame mainFrame = BiblioAdmin2App.getApplication().getMainFrame();
            librosPorDisciplina = new LibrosPorDisciplinaView();
            librosPorDisciplina.setLocationRelativeTo(mainFrame);
        }
        librosPorDisciplina.show();
    }

        @Action
    public void showBuscarLibrosPorISBN() {
        if (librosBuscarPorISBN == null) {
            JFrame mainFrame = BiblioAdmin2App.getApplication().getMainFrame();
            librosBuscarPorISBN = new LibrosBuscarPorISBNView();
            librosBuscarPorISBN.setLocationRelativeTo(mainFrame);
        }
        librosBuscarPorISBN.show();
    }
        @Action
    public void showBuscarLibrosPorTitulo() {
        if (librosBuscarPorTitulo == null) {
            JFrame mainFrame = BiblioAdmin2App.getApplication().getMainFrame();
            librosBuscarPorTitulo = new LibrosBuscarPorTituloView();
            librosBuscarPorTitulo.setLocationRelativeTo(mainFrame);
        }
        librosBuscarPorTitulo.show();
    }
@Action
    public void showBuscarLibrosPorDiscplina() {
        if (librosBuscarPorDisciplina == null) {
            JFrame mainFrame = BiblioAdmin2App.getApplication().getMainFrame();
            librosBuscarPorDisciplina = new LibrosBuscarPorDisciplinaView();
            librosBuscarPorDisciplina.setLocationRelativeTo(mainFrame);
        }
        librosBuscarPorDisciplina.show();
    }
@Action
    public void showNuevoLibroView() {
        if (librosNuevoView == null) {
            JFrame mainFrame = BiblioAdmin2App.getApplication().getMainFrame();
            librosNuevoView = new LibrosNuevoView();
            librosNuevoView.setLocationRelativeTo(mainFrame);
        }
        librosNuevoView.show();
    }



    private JFrame librosNoDevueltos;
    private JFrame librosPorDisciplina;
    private JFrame librosBuscarPorISBN;
    private JFrame librosBuscarPorTitulo;
    private JFrame librosBuscarPorDisciplina;
    private JFrame librosNuevoView;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem librosNoDevueltosMenu;
    private javax.swing.JMenu menuInformes;
    private javax.swing.JMenu menuLibros;
    // End of variables declaration//GEN-END:variables

}
