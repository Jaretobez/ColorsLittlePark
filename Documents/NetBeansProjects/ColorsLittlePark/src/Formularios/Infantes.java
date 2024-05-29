/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.ConexionBD;
import Clases.Infante;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Infantes extends javax.swing.JFrame {

    ConexionBD con = new ConexionBD();
    DefaultTableModel dtm = new DefaultTableModel();
    Infante infante = new Infante();

    /**
     * Creates new form Infantes
     */
    // Inicialización de las tablas en el constructor
    public Infantes() {
        initComponents();
        MostrarTabla();
        this.setLocationRelativeTo(null);
        initializeTables();
    }

// Mostrar los datos en las tablas
    public void MostrarTabla() {
        MostrarTabla mostrartabla = new MostrarTabla();
        DefaultTableModel modeloTutores = mostrartabla.mostrarTutores();
        DefaultTableModel modeloInfantes = mostrartabla.mostrarInfantes();
        tblTutores.setModel(modeloTutores);
        tblInfantes.setModel(modeloInfantes);
    }

// Configurar los listeners para las tablas
    private void initializeTables() {
        addTableSelectionListener(tblTutores);
        addTableSelectionListener(tblInfantes);
    }

// Función general para añadir el listener a una tabla
    private void addTableSelectionListener(JTable table) {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                    if (selectedRow >= 0) {
                        if (table == tblTutores) {
                            txtTelefono.setText(dtm.getValueAt(selectedRow, 0).toString());
                            // No actualizamos los campos de nombre y apellido
                        } else if (table == tblInfantes) {
                            txtID.setText(dtm.getValueAt(selectedRow, 0).toString());
                            txtNombre.setText(dtm.getValueAt(selectedRow, 1).toString());
                            txtApellido.setText(dtm.getValueAt(selectedRow, 2).toString());
                            try {
                                int edad = Integer.parseInt(dtm.getValueAt(selectedRow, 3).toString());
                                SpinnerEdad.setValue(edad);
                            } catch (NumberFormatException ex) {
                                System.out.println("Error al convertir la edad: " + ex.getMessage());
                            }
                            txtTelefono.setText(dtm.getValueAt(selectedRow, 4).toString());
                        }
                    }
                }
            }
        });
    }

    void limpiarCampos() {
        txtID.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        SpinnerNumberModel model = (SpinnerNumberModel) SpinnerEdad.getModel();
        model.setValue(0); // Cambia 0 por el valor predeterminado que desees para el spinner
        txtTelefono.setText("");
    }

    void Agregar() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        int edad = (int) SpinnerEdad.getValue(); // Parsear el valor del Spinner como un entero
        String telefono = txtTelefono.getText();

        // Llamar al método RegistrarInfante() en la instancia de Infante
        infante.RegistrarInfante(nombre, apellido, edad, telefono);
        // Agregar a la tabla    
        dtm.addRow(new Object[]{nombre, apellido, edad, telefono});
        MostrarTabla();
    }

    void Eliminar() {
        DefaultTableModel dtm = (DefaultTableModel) tblInfantes.getModel();
        int fila = tblInfantes.getSelectedRow();
        if (fila >= 0) {
            String idInfante = txtID.getText();
            int id = Integer.parseInt(idInfante);
            // Eliminar de la base de datos
            infante.EliminarInfante(id);
            // Eliminar de la tabla
            dtm.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
            System.out.println("No se seleccionó ninguna fila.");
        }
    }

    void Actualizar() {
        // Obtener el modelo de la tabla
        DefaultTableModel dtm = (DefaultTableModel) tblInfantes.getModel();
        // Obtener la fila seleccionada
        int selectedRow = tblInfantes.getSelectedRow();

        // Obtener los valores de los campos de texto
        String id = txtID.getText();
        int idInfante = Integer.parseInt(id);
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        int edad = (int) SpinnerEdad.getValue();
        String telefono = txtTelefono.getText();

        // Verificar si una fila está realmente seleccionada
        if (selectedRow >= 0) {
            // Actualizar en la base de datos
            infante.ModificarInfante(idInfante, nombre, apellido, edad);

            // Actualizar los valores en la tabla
            dtm.setValueAt(idInfante, selectedRow, 0);
            dtm.setValueAt(nombre, selectedRow, 1);
            dtm.setValueAt(apellido, selectedRow, 2);
            dtm.setValueAt(edad, selectedRow, 3);
     //       dtm.setValueAt(telefono, selectedRow, 4);

            System.out.println("Datos del tutor actualizados correctamente.");
        } else {
            // Si no hay ninguna fila seleccionada, buscar el teléfono en la tabla
            boolean found = false;
            for (int i = 0; i < dtm.getRowCount(); i++) {
                if (dtm.getValueAt(i, 0).toString().equals(telefono)) {
                    // Actualizar en la base de datos
                    infante.ModificarInfante(idInfante, nombre, apellido, edad);

                    // Actualizar los valores en la tabla
                    dtm.setValueAt(idInfante, selectedRow, 0);
                    dtm.setValueAt(nombre, selectedRow, 1);
                    dtm.setValueAt(apellido, selectedRow, 2);
                    dtm.setValueAt(edad, selectedRow, 3);
                 //   dtm.setValueAt(telefono, selectedRow, 4);

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTutores = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInfantes = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        SpinnerEdad = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fSButtonMD1 = new LIB.FSButtonMD();
        fSButtonMD2 = new LIB.FSButtonMD();
        fSButtonMD3 = new LIB.FSButtonMD();
        fSButtonMD4 = new LIB.FSButtonMD();
        jLabel8 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        fSButtonMD5 = new LIB.FSButtonMD();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Nombre");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 63, 26));

        jLabel2.setText("Apellido");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 63, 25));

        jLabel3.setText("Edad");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 49, 24));

        jLabel5.setText("Telefono del tutor");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 120, 20));

        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 200, 20));

        txtApellido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jPanel1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 200, 20));

        txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 200, 20));

        tblTutores.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        tblTutores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblTutores);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 400, 230));

        jButton1.setText("jButton1");
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, -1));

        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 200, -1));

        tblInfantes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        tblInfantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblInfantes);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 340, 400, 230));

        jButton2.setText("jButton2");
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, -1, -1));

        jTextField6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 310, 220, -1));

        SpinnerEdad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jPanel1.add(SpinnerEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 80, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Infantes");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 270, 130, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Tutores");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 130, 30));

        fSButtonMD1.setBackground(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setText("Eliminar");
        fSButtonMD1.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setColorTextHover(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setContentAreaFilled(true);
        fSButtonMD1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD1MousePressed(evt);
            }
        });
        jPanel1.add(fSButtonMD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, 129, -1));

        fSButtonMD2.setBackground(new java.awt.Color(255, 0, 0));
        fSButtonMD2.setText("Agregar");
        fSButtonMD2.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD2.setColorTextHover(new java.awt.Color(255, 0, 0));
        fSButtonMD2.setContentAreaFilled(true);
        fSButtonMD2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD2MousePressed(evt);
            }
        });
        jPanel1.add(fSButtonMD2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 129, -1));

        fSButtonMD3.setBackground(new java.awt.Color(255, 0, 0));
        fSButtonMD3.setText("Limpiar Campos");
        fSButtonMD3.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD3.setColorTextHover(new java.awt.Color(255, 0, 0));
        fSButtonMD3.setContentAreaFilled(true);
        fSButtonMD3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD3MousePressed(evt);
            }
        });
        jPanel1.add(fSButtonMD3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 129, -1));

        fSButtonMD4.setBackground(new java.awt.Color(255, 0, 0));
        fSButtonMD4.setText("Modificar");
        fSButtonMD4.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD4.setColorTextHover(new java.awt.Color(255, 0, 0));
        fSButtonMD4.setContentAreaFilled(true);
        fSButtonMD4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD4MousePressed(evt);
            }
        });
        jPanel1.add(fSButtonMD4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, 129, -1));

        jLabel8.setText("ID:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 60, 20));

        txtID.setEditable(false);
        txtID.setBackground(new java.awt.Color(204, 204, 204));
        txtID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 200, 20));

        fSButtonMD5.setBackground(new java.awt.Color(255, 0, 0));
        fSButtonMD5.setText("Activar");
        fSButtonMD5.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD5.setColorTextHover(new java.awt.Color(255, 0, 0));
        fSButtonMD5.setContentAreaFilled(true);
        jPanel1.add(fSButtonMD5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 129, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 860, 10));

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));

        jLabel4.setFont(new java.awt.Font("Segoe UI Historic", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CONTROL DE INFANTES");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(367, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
        );

        setBounds(0, 0, 875, 621);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void fSButtonMD3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD3MousePressed
        limpiarCampos();
    }//GEN-LAST:event_fSButtonMD3MousePressed

    private void fSButtonMD2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD2MousePressed
        Agregar();
    }//GEN-LAST:event_fSButtonMD2MousePressed

    private void fSButtonMD1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD1MousePressed
        Eliminar();
    }//GEN-LAST:event_fSButtonMD1MousePressed

    private void fSButtonMD4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD4MousePressed
        Actualizar();
    }//GEN-LAST:event_fSButtonMD4MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Infantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner SpinnerEdad;
    private LIB.FSButtonMD fSButtonMD1;
    private LIB.FSButtonMD fSButtonMD2;
    private LIB.FSButtonMD fSButtonMD3;
    private LIB.FSButtonMD fSButtonMD4;
    private LIB.FSButtonMD fSButtonMD5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTable tblInfantes;
    private javax.swing.JTable tblTutores;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
