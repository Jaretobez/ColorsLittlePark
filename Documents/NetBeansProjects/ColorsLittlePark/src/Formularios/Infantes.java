/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.Tutor;
import Clases.ConexionBD;
import Clases.Infante;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
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
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo (64x32).png")));
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

                            }
                            txtTelefono.setText(dtm.getValueAt(selectedRow, 4).toString());
                        }
                    }
                }
            }
        });
    }

    //limpia los campos del formulario
    void limpiarCampos() {
        txtID.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        SpinnerNumberModel model = (SpinnerNumberModel) SpinnerEdad.getModel();
        model.setValue(0); // Cambia 0 por el valor predeterminado que desees para el spinner
        txtTelefono.setText("");
    }

    //obtiene los datos para registrar infantes los manda lo muestra en la tabla
void Agregar() {
    String nombre = txtNombre.getText();
    String apellido = txtApellido.getText();
    int edad = (int) SpinnerEdad.getValue(); // Parsear el valor del Spinner como un entero
    String telefono = txtTelefono.getText();
    
    // Preguntar al usuario si desea agregar al infante
    int option = JOptionPane.showConfirmDialog(null, "¿Desea agregar al infante?", "Confirmación", JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION) {
        // Llamar al método RegistrarInfante() en la instancia de Infante
        infante.RegistrarInfante(nombre, apellido, edad, telefono);
        
        // Agregar a la tabla    
        dtm.addRow(new Object[]{nombre, apellido, edad, telefono});
        MostrarTabla();
    }
}


void Eliminar() {
    DefaultTableModel dtm = (DefaultTableModel) tblInfantes.getModel();
    int fila = tblInfantes.getSelectedRow();
    if (fila >= 0) {
        // Obtener el ID del infante seleccionado
        String idInfante = txtID.getText();
        int id = Integer.parseInt(idInfante);
        
        // Preguntar al usuario si desea eliminar al infante
        int option = JOptionPane.showConfirmDialog(null, "¿Desea eliminar al infante?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Eliminar de la base de datos
            infante.EliminarInfante(id);
            
            // Eliminar de la tabla
            dtm.removeRow(fila);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
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
        // Preguntar al usuario si desea actualizar los datos del infante
        int option = JOptionPane.showConfirmDialog(null, "¿Desea actualizar los datos del infante?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Actualizar en la base de datos
            infante.ModificarInfante(idInfante, nombre, apellido, edad);

            // Actualizar los valores en la tabla
            dtm.setValueAt(idInfante, selectedRow, 0);
            dtm.setValueAt(nombre, selectedRow, 1);
            dtm.setValueAt(apellido, selectedRow, 2);
            dtm.setValueAt(edad, selectedRow, 3);
        }
    } else {
        // Si no hay ninguna fila seleccionada, buscar el teléfono en la tabla
        boolean found = false;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            if (dtm.getValueAt(i, 0).toString().equals(telefono)) {
                // Preguntar al usuario si desea actualizar los datos del infante
                int option = JOptionPane.showConfirmDialog(null, "¿Desea actualizar los datos del infante?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // Actualizar en la base de datos
                    infante.ModificarInfante(idInfante, nombre, apellido, edad);

                    // Actualizar los valores en la tabla
                    dtm.setValueAt(idInfante, selectedRow, 0);
                    dtm.setValueAt(nombre, selectedRow, 1);
                    dtm.setValueAt(apellido, selectedRow, 2);
                    dtm.setValueAt(edad, selectedRow, 3);
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró un infante con el ID especificado.");
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
        JOptionPane.showMessageDialog(null, "No se encontró ningún infante con el dato especificado.");
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

        jLabel3.setText("Edad:");

        jLabel5.setText("Telefono del tutor:");

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
        tblTutores.getTableHeader().setReorderingAllowed(false);
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
        tblInfantes.getTableHeader().setReorderingAllowed(false);
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
        fSButtonMD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        fSButtonMD1.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setColorTextHover(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setContentAreaFilled(true);
        fSButtonMD1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD1MousePressed(evt);
            }
        });

        fSButtonMD2.setBackground(new java.awt.Color(255, 0, 0));
        fSButtonMD2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono de agregar.jpg"))); // NOI18N
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
        fSButtonMD4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/WhatsApp Image 2024-05-28 at 9.48.19 PM (3).jpeg"))); // NOI18N
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
        fSButtonMD5.setText("Activar infante");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(fSButtonMD2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addComponent(fSButtonMD1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addComponent(fSButtonMD4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(54, 54, 54))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(SpinnerEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(fSButtonMD3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(19, 19, 19)
                .addComponent(fSButtonMD5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(55, 55, 55))
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(130, 130, 130)
                .addComponent(fSButtonMD6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(144, 144, 144))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(txtBuscarTutor)
                .addGap(140, 140, 140)
                .addComponent(jButton2)
                .addGap(8, 8, 8)
                .addComponent(txtBuscarInfante)
                .addGap(100, 100, 100))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fSButtonMD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fSButtonMD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fSButtonMD4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(SpinnerEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(fSButtonMD3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(fSButtonMD5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fSButtonMD6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(txtBuscarTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(txtBuscarInfante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        setBounds(0, 0, 904, 621);
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
