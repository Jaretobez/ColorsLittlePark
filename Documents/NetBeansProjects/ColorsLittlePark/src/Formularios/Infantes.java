/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.Tutor;
import Clases.ConexionBD;
import Clases.Infante;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.List;
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
    Tutor tutor = new Tutor();

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
    
    
public void ConsultarInfantee() {
    String buscar = txtBuscarInfante.getText();
    String buscar2 = String.valueOf(buscar);
    DefaultTableModel dtm = (DefaultTableModel) tblInfantes.getModel();
    // Consultar tutor según el dato proporcionado
    List<String[]> infanteInfo = infante.ConsultarInfante(buscar2);  
    // Limpiar la tabla antes de mostrar los resultados
    dtm.setRowCount(0);
   
    if (!infanteInfo.isEmpty()) {
        // Agregar los datos obtenidos a la tabla
        for (String[] info : infanteInfo) {
            Object[] rowData = new Object[]{info[0], info[1], info[2], info[3], info[4]}; 
            dtm.addRow(rowData);
        } 
           
    } else {
        JOptionPane.showMessageDialog(null, "No se encontró ningún tutor con el dato especificado.");
    }
    tblInfantes.setModel(dtm);
}

public void ConsultarTutorr() {
    String buscar = txtBuscarTutor.getText();
   DefaultTableModel dtm = (DefaultTableModel) tblTutores.getModel();
    // Consultar tutor según el dato proporcionado
    List<String[]> tutorInfo = tutor.ConsultarTutor(buscar);  
        // Limpiar la tabla antes de mostrar los resultados
    dtm.setRowCount(0);

    if (!tutorInfo.isEmpty()) {
        // Agregar los datos obtenidos a la tabla
        for (String[] info : tutorInfo) {
            Object[] rowData = new Object[]{info[0], info[1], info[2]}; 
            dtm.addRow(rowData);
        }    
       
    } else {
        JOptionPane.showMessageDialog(null, "No se encontró ningún tutor con el dato especificado.");
    }
     tblTutores.setModel(dtm);
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
        txtBuscarTutor = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInfantes = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        txtBuscarInfante = new javax.swing.JTextField();
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
        fSButtonMD6 = new LIB.FSButtonMD();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Nombre");

        jLabel2.setText("Apellido");

        jLabel3.setText("Edad");

        jLabel5.setText("Telefono del tutor");

        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtApellido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        txtTelefono.setEditable(false);
        txtTelefono.setBackground(new java.awt.Color(255, 255, 204));
        txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

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

        jButton1.setText("Buscar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        txtBuscarTutor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        tblInfantes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        tblInfantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblInfantes);

        jButton2.setText("Buscar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        txtBuscarInfante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        SpinnerEdad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Infantes");

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Tutores");

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

        jLabel8.setText("ID:");

        txtID.setEditable(false);
        txtID.setBackground(new java.awt.Color(255, 255, 204));
        txtID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        fSButtonMD5.setBackground(new java.awt.Color(255, 0, 0));
        fSButtonMD5.setText("Activar");
        fSButtonMD5.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD5.setColorTextHover(new java.awt.Color(255, 0, 0));
        fSButtonMD5.setContentAreaFilled(true);
        fSButtonMD5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD5MousePressed(evt);
            }
        });

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
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel4))
        );

        fSButtonMD6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actualizarinfo.png"))); // NOI18N
        fSButtonMD6.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD6.setContentAreaFilled(true);
        fSButtonMD6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD6MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(69, 69, 69)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SpinnerEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fSButtonMD1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fSButtonMD2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fSButtonMD4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fSButtonMD5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fSButtonMD3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscarTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(45, 45, 45)
                        .addComponent(fSButtonMD6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscarInfante, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fSButtonMD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(fSButtonMD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(fSButtonMD3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SpinnerEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(fSButtonMD4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(fSButtonMD5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBuscarTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2)
                                .addComponent(txtBuscarInfante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(fSButtonMD6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
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

        setBounds(0, 0, 884, 621);
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

    private void fSButtonMD6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD6MousePressed
        MostrarTabla();
    }//GEN-LAST:event_fSButtonMD6MousePressed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        ConsultarInfantee();
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
       ConsultarTutorr();
    }//GEN-LAST:event_jButton1MousePressed

    private void fSButtonMD5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD5MousePressed
     Activar activar = new Activar();
     this.dispose();
     activar.setVisible(true);
    }//GEN-LAST:event_fSButtonMD5MousePressed

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
    private LIB.FSButtonMD fSButtonMD6;
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
    private javax.swing.JTable tblInfantes;
    private javax.swing.JTable tblTutores;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscarInfante;
    private javax.swing.JTextField txtBuscarTutor;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
