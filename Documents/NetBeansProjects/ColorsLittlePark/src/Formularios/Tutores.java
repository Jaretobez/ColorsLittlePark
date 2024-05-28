/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.ConexionBD;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Jared
 */
public class Tutores extends javax.swing.JFrame {
    ConexionBD con = new ConexionBD();
   
 DefaultTableModel dtm=new DefaultTableModel();
 
 Tutor tutor = new Tutor();
    /**
     * Creates new form Tutores
     */
    public Tutores() {
        initComponents();
        MostrarTabla();
  //      a = new Atributos();
        this.setLocationRelativeTo(null); 
         initialize();
    }

    
    
            public void MostrarTabla()
    {
      
        MostrarTabla mostrartabla = new MostrarTabla();
        
        DefaultTableModel modelo = mostrartabla.mostrarTutores();
        
        tblTutor.setModel(modelo);
        
    }
    
    
     void Agregar() {
         
        String  tel = txtTelefono.getText();
       String nombre = txtNomTutor.getText();
        String apellido = txtApeTutor.getText();
        String num = String.valueOf(tel);
        // Agregar a la base de datos
        tutor.RegistrarTutor(tel, nombre, apellido);
        // Agregar a la tabla
               MostrarTabla();
        dtm.addRow(new Object[]{tel, nombre, apellido});
    }
    
void Eliminar() {
      DefaultTableModel dtm = (DefaultTableModel) tblTutor.getModel();
    int fila = tblTutor.getSelectedRow();
    if (fila >= 0) { 
        String num_telefono = (String) dtm.getValueAt(fila, 0);
        // Eliminar de la base de datos
        tutor.EliminarTutor(num_telefono);
        // Eliminar de la tabla
        dtm.removeRow(fila);
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
        System.out.println("No se seleccionó ninguna fila.");
    }
}

    
void Actualizar() {
    // Obtener el modelo de la tabla
    DefaultTableModel dtm = (DefaultTableModel) tblTutor.getModel();
    // Obtener la fila seleccionada
    int selectedRow = tblTutor.getSelectedRow();
    
    // Obtener los valores de los campos de texto
    String telefono = txtTelefono.getText();
    String nombre = txtNomTutor.getText();
    String apellido = txtApeTutor.getText();

    // Verificar si una fila está realmente seleccionada
    if (selectedRow >= 0) {
        // Actualizar en la base de datos
        tutor.ModificarTutor(telefono, nombre, apellido);
        
        // Actualizar los valores en la tabla
        dtm.setValueAt(telefono, selectedRow, 0);
        dtm.setValueAt(nombre, selectedRow, 1);
        dtm.setValueAt(apellido, selectedRow, 2);
        
        System.out.println("Datos del tutor actualizados correctamente.");
    } else {
        // Si no hay ninguna fila seleccionada, buscar el teléfono en la tabla
        boolean found = false;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            if (dtm.getValueAt(i, 0).toString().equals(telefono)) {
                // Actualizar en la base de datos
                tutor.ModificarTutor(telefono, nombre, apellido);
                
                // Actualizar los valores en la tabla
                dtm.setValueAt(telefono, i, 0);
                dtm.setValueAt(nombre, i, 1);
                dtm.setValueAt(apellido, i, 2);
                
                System.out.println("Datos del tutor actualizados correctamente.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Error: No se encontró un tutor con el número de teléfono especificado.");
        }
    }
}

void Consultar() {
    String telefono = jTextField1.getText();  // Obtener el número de teléfono desde el campo de texto
    // Limpiar la tabla antes de mostrar los resultados
    dtm.setRowCount(0);

    // Consultar tutor según el número de teléfono proporcionado
    String[] tutorInfo = tutor.ConsultarTutor(telefono);

    if (tutorInfo[0] != null) {
        // Agregar los datos obtenidos a la tabla
        dtm.addRow(tutorInfo);
        System.out.println("consulta:" + tutorInfo);
        MostrarTabla();
    } else {
        System.out.println("No se encontró ningún tutor con el número de teléfono especificado.");
    }
}



    
    
private void initialize() {

        // Inicializar el modelo de la tabla y la tabla
        dtm = new DefaultTableModel(new Object[]{"num_telefono", "nombre", "apellido"}, 0);
        // Agregar el ListSelectionListener a la tabla
        tblTutor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                       
                    int selectedRow = tblTutor.getSelectedRow();
                    DefaultTableModel dtm = (DefaultTableModel) tblTutor.getModel();
                    if (selectedRow >= 0) {
                        txtTelefono.setText(dtm.getValueAt(selectedRow, 0).toString());
                        txtNomTutor.setText(dtm.getValueAt(selectedRow, 1).toString());
                        txtApeTutor.setText(dtm.getValueAt(selectedRow, 2).toString());
                      
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

        jPanel1 = new javax.swing.JPanel();
        fSButtonMD1 = new LIB.FSButtonMD();
        BotonAgregar = new javax.swing.JButton();
        BotonModificar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtTelefono = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNomTutor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtApeTutor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTutor = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fSButtonMD1.setText("Agregar Infante");
        fSButtonMD1.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setColorTextHover(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setContentAreaFilled(true);
        fSButtonMD1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD1MousePressed(evt);
            }
        });
        fSButtonMD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fSButtonMD1ActionPerformed(evt);
            }
        });
        jPanel1.add(fSButtonMD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));

        BotonAgregar.setText("Agregar");
        BotonAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BotonAgregarMousePressed(evt);
            }
        });
        jPanel1.add(BotonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 305, -1, -1));

        BotonModificar.setText("Modificar");
        BotonModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BotonModificarMousePressed(evt);
            }
        });
        BotonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonModificarActionPerformed(evt);
            }
        });
        jPanel1.add(BotonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 305, -1, -1));

        jButton1.setText("Eliminar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 305, -1, -1));
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 212, -1));

        jLabel1.setText("Telefono");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 73, -1));

        jLabel2.setText("Nombre");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 110, -1));
        jPanel1.add(txtNomTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 216, -1));

        jLabel3.setText("Apellido");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 76, 20));
        jPanel1.add(txtApeTutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 216, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Control Tutores");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 22, 212, -1));

        jButton2.setText("Buscar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 32, 88, -1));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 32, 277, -1));

        tblTutor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblTutor);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 383, 323));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );

        setBounds(0, 0, 729, 491);
    }// </editor-fold>//GEN-END:initComponents

    private void BotonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonModificarActionPerformed
       
    }//GEN-LAST:event_BotonModificarActionPerformed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        Consultar();
        MostrarTabla();
    }//GEN-LAST:event_jButton2MousePressed

    private void BotonAgregarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotonAgregarMousePressed
       Agregar();
       MostrarTabla();
    }//GEN-LAST:event_BotonAgregarMousePressed

    private void BotonModificarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotonModificarMousePressed
        Actualizar();
        MostrarTabla();
    }//GEN-LAST:event_BotonModificarMousePressed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
       Eliminar();
       MostrarTabla();
    }//GEN-LAST:event_jButton1MousePressed

    private void fSButtonMD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fSButtonMD1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fSButtonMD1ActionPerformed

    private void fSButtonMD1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD1MousePressed
    Infantes infantes = new Infantes();
     this.dispose();
     infantes.setVisible(true);
    }//GEN-LAST:event_fSButtonMD1MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
  FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tutores().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonAgregar;
    private javax.swing.JButton BotonModificar;
    private LIB.FSButtonMD fSButtonMD1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblTutor;
    private javax.swing.JTextField txtApeTutor;
    private javax.swing.JTextField txtNomTutor;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
//private final Atributos a;
}
