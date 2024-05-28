/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.ConexionBD;
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

        BotonAgregar.setText("Agregar");
        BotonAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BotonAgregarMousePressed(evt);
            }
        });

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

        jButton1.setText("Eliminar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        jLabel1.setText("Telefono");

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Control Tutores");

        jButton2.setText("Buscar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(410, 410, 410))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BotonAgregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BotonModificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtNomTutor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                                .addComponent(txtApeTutor, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtApeTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(BotonModificar)
                            .addComponent(BotonAgregar))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90))))
        );

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

        setBounds(0, 0, 704, 479);
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
            java.util.logging.Logger.getLogger(Tutores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tutores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tutores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tutores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tutores().setVisible(true);
            }
        });
    }
    
    void cosultar(){
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonAgregar;
    private javax.swing.JButton BotonModificar;
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
