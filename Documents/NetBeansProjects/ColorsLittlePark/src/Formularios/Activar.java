/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.Activos;
import Clases.ConexionBD;
import Clases.HistorialEliminacion;
import Clases.Infante;
import com.formdev.flatlaf.FlatLightLaf;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.sql.SQLException;

/**
 *
 * @author Jared
 */
public class Activar extends javax.swing.JFrame {
     DefaultTableModel dtm=new DefaultTableModel();
        ConexionBD con = new ConexionBD();
        Activos activos = new Activos();
        Infante infante = new Infante();
        HistorialEliminacion historialEliminacion = new HistorialEliminacion();
    /**
     * Creates new form Activar
     */
    public Activar() {
        initComponents();
        this.setLocationRelativeTo(null);
         MostrarTabla();
         initializeTables();
    }
    
    
    public void MostrarTabla() {     
    MostrarTabla mostrartabla = new MostrarTabla();      
    DefaultTableModel modeloActivos = mostrartabla.MostrarActivos(); 
    tblActivos2.setModel(modeloActivos);  
    DefaultTableModel modeloInfantes = mostrartabla.mostrarInfantes(); 
    tblInfantes.setModel(modeloInfantes);  
}

    private void initializeTables() {
        addTableSelectionListener(tblInfantes);
    }

 private void addTableSelectionListener(JTable table) {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                    if (selectedRow >= 0) {
                        if (table == tblInfantes) {
                            try {
                                Object idInfanteObj = dtm.getValueAt(selectedRow, 0); 
                                Object nombre_infanteObj = dtm.getValueAt(selectedRow, 1);
                                Object numTelefonoObj = dtm.getValueAt(selectedRow, 4); // Assuming phone number is at column 4
                                
                                int idInfante = Integer.parseInt(idInfanteObj.toString());
                                String numTelefono = nombre_infanteObj.toString();
                                String nominfante = numTelefonoObj.toString();

                            } catch (NumberFormatException ex) {
                                System.out.println("Error al convertir los datos de la tabla: " + ex.getMessage());
                            } catch (Exception ex) {
                                System.out.println("Error al obtener los datos de la tabla: " + ex.getMessage());
                            }
                        }
                    }
                }
            }
        });
    }

private void activarInfante() {
    DefaultTableModel dtm = (DefaultTableModel) tblInfantes.getModel();
    int selectedRow = tblInfantes.getSelectedRow();
    if (selectedRow >= 0) {
        try {
            Object idInfanteObj = dtm.getValueAt(selectedRow, 0); // Assuming the ID is at column 0
            Object nombre_infanteObj = dtm.getValueAt(selectedRow, 1);
            Object numTelefonoObj = dtm.getValueAt(selectedRow, 4); // Assuming phone number is at column 4
            
            int idInfante = Integer.parseInt(idInfanteObj.toString());
            String numTelefono = numTelefonoObj.toString();
            String nombre_infante = nombre_infanteObj.toString();

            // Obtener la hora actual
            LocalTime horaEntrada = LocalTime.now();
            // Convertir la hora actual a formato de cadena (opcional)
            String horaEntradaStr = horaEntrada.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            
            if (activos.VerificaInfanteEnActivos(idInfante)) {
                JOptionPane.showMessageDialog(null, "El infante con ID " + idInfante + " ya está activo.");
            } else {
                activos.Activar(idInfante, numTelefono, horaEntradaStr, nombre_infante);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error al convertir los datos de la tabla: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la tabla: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione una fila para activar.");
    }
}

    public void ConsultarActivoss() {
    String buscar = txtBuscarActivo.getText();
    String buscar2 = String.valueOf(buscar);
    DefaultTableModel dtm = (DefaultTableModel) tblActivos2.getModel();
    // Consultar tutor según el dato proporcionado
    List<String[]> activoInfo = activos.ConsultarActivos(buscar2);  
    // Limpiar la tabla antes de mostrar los resultados
    dtm.setRowCount(0);
   
    if (!activoInfo.isEmpty()) {
        // Agregar los datos obtenidos a la tabla
        for (String[] info : activoInfo) {
            Object[] rowData = new Object[]{info[0], info[1], info[2], info[3], info[4]}; 
            dtm.addRow(rowData);
        } 
           
    } else {
        JOptionPane.showMessageDialog(null, "No se encontró ningún tutor con el dato especificado.");
    }
    tblActivos2.setModel(dtm);
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
    
    
public String getHoraEntrada(Connection conector, int idActivo) throws SQLException {
    String query = "SELECT hora_entrada FROM activos WHERE id_activo = ?";
    try (PreparedStatement stmt = conector.prepareStatement(query)) {
        stmt.setInt(1, idActivo);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("hora_entrada");
            } else {
                throw new SQLException("No se encontró el activo con ID: " + idActivo);
            }
        }
    }
}


    
private void desactivar() {
    DefaultTableModel dtm = (DefaultTableModel) tblActivos2.getModel();
    int selectedRow = tblActivos2.getSelectedRow();
    if (selectedRow >= 0) {
        try {
            Object idInfanteObj = dtm.getValueAt(selectedRow, 0); // Suponiendo que el ID está en la columna 0
            int idActivo = Integer.parseInt(idInfanteObj.toString());
            Object hora_entradaObj = dtm.getValueAt(selectedRow, 3);
            String hora_entrada = hora_entradaObj.toString();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date horaEntrada = dateFormat.parse(hora_entrada);

            // Verificar el tiempo transcurrido desde la activación
            long elapsedTime = new Date().getTime() - horaEntrada.getTime();
            long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);

            if (elapsedMinutes < 5) {
                JOptionPane.showMessageDialog(null, "No se puede desactivar el infante despues de 5 minutos de su activación.");
                return;
            }
            historialEliminacion.AgregarHistoriaEliminados(idActivo);
            activos.Desactivar(idActivo);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error al convertir los datos de la tabla: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al desactivar el infante: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione una fila para desactivar.");
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

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInfantes = new javax.swing.JTable();
        txtBuscarInfante = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        fSButtonMD1 = new LIB.FSButtonMD();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblActivos2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        txtBuscarActivo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fSButtonMD2 = new LIB.FSButtonMD();
        fSButtonMD3 = new LIB.FSButtonMD();

        jScrollPane3.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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
        ));
        tblInfantes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblInfantes.setFillsViewportHeight(true);
        tblInfantes.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblInfantes);

        txtBuscarInfante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busquda (2).png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        fSButtonMD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/flecha roja 32x32.jpg"))); // NOI18N
        fSButtonMD1.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setColorTextNormal(new java.awt.Color(204, 204, 204));
        fSButtonMD1.setColorTextPressed(new java.awt.Color(204, 204, 204));
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

        tblActivos2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        tblActivos2.setModel(new javax.swing.table.DefaultTableModel(
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
        tblActivos2.setFillsViewportHeight(true);
        tblActivos2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblActivos2);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busquda (2).png"))); // NOI18N
        jButton2.setText("Buscar");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        txtBuscarActivo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ACTIVAR INFANTE");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(350, 350, 350)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addGap(364, 364, 364))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Infantes");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Activos");

        fSButtonMD2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actualizarinfo.png"))); // NOI18N
        fSButtonMD2.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD2.setContentAreaFilled(true);
        fSButtonMD2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD2MousePressed(evt);
            }
        });

        fSButtonMD3.setBackground(new java.awt.Color(255, 0, 0));
        fSButtonMD3.setText("Desactivar");
        fSButtonMD3.setToolTipText("");
        fSButtonMD3.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD3.setColorTextHover(new java.awt.Color(255, 0, 0));
        fSButtonMD3.setContentAreaFilled(true);
        fSButtonMD3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(537, 537, 537)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscarInfante, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fSButtonMD3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fSButtonMD2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fSButtonMD1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscarActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscarInfante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscarActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)))
                    .addComponent(fSButtonMD2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(fSButtonMD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(fSButtonMD3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(126, 126, 126))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 1069, 597);
    }// </editor-fold>//GEN-END:initComponents

    private void fSButtonMD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fSButtonMD1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fSButtonMD1ActionPerformed

    private void fSButtonMD1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD1MousePressed
        activarInfante();
        MostrarTabla();
    }//GEN-LAST:event_fSButtonMD1MousePressed

    private void fSButtonMD2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD2MousePressed
        MostrarTabla();
    }//GEN-LAST:event_fSButtonMD2MousePressed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        ConsultarInfantee();
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        ConsultarActivoss();
    }//GEN-LAST:event_jButton2MousePressed

    private void fSButtonMD3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD3MousePressed
     ConexionBD con = new ConexionBD();
    desactivar(); // Llamar a la función desactivarInfante pasando la conexión
    MostrarTabla(); // Actualizar la tabla
    }//GEN-LAST:event_fSButtonMD3MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatLightLaf.setup();   

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Activar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private LIB.FSButtonMD fSButtonMD1;
    private LIB.FSButtonMD fSButtonMD2;
    private LIB.FSButtonMD fSButtonMD3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTable tblActivos2;
    private javax.swing.JTable tblInfantes;
    private javax.swing.JTextField txtBuscarActivo;
    private javax.swing.JTextField txtBuscarInfante;
    // End of variables declaration//GEN-END:variables
}
