/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.Activos;
import Clases.ConexionBD;
import Clases.Infante;
import com.formdev.flatlaf.FlatLightLaf;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jared
 */
public class Activar extends javax.swing.JFrame {
     DefaultTableModel dtm=new DefaultTableModel();
        ConexionBD con = new ConexionBD();
        Activos activos = new Activos();
        Infante infante = new Infante();
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
    tblActivos.setModel(modeloActivos);  
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
                                Object idInfanteObj = dtm.getValueAt(selectedRow, 0); // Assuming the ID is at column 0
                                Object numTelefonoObj = dtm.getValueAt(selectedRow, 4); // Assuming phone number is at column 4
                                
                                int idInfante = Integer.parseInt(idInfanteObj.toString());
                                String numTelefono = numTelefonoObj.toString();

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
            Object numTelefonoObj = dtm.getValueAt(selectedRow, 4); // Assuming phone number is at column 4
            
            int idInfante = Integer.parseInt(idInfanteObj.toString());
            String numTelefono = numTelefonoObj.toString();

            // Obtener la hora actual
            LocalTime horaEntrada = LocalTime.now();
            // Convertir la hora actual a formato de cadena (opcional)
            String horaEntradaStr = horaEntrada.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            
            if (activos.VerificaInfanteEnActivos(idInfante)) {
                JOptionPane.showMessageDialog(null, "El infante con ID " + idInfante + " ya está activo.");
            } else {
                activos.Activar(idInfante, numTelefono, horaEntradaStr);
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
    DefaultTableModel dtm = (DefaultTableModel) tblActivos.getModel();
    // Consultar tutor según el dato proporcionado
    List<String[]> activoInfo = activos.ConsultarActivos(buscar2);  
    // Limpiar la tabla antes de mostrar los resultados
    dtm.setRowCount(0);
   
    if (!activoInfo.isEmpty()) {
        // Agregar los datos obtenidos a la tabla
        for (String[] info : activoInfo) {
            Object[] rowData = new Object[]{info[0], info[1], info[2]}; 
            dtm.addRow(rowData);
        } 
           
    } else {
        JOptionPane.showMessageDialog(null, "No se encontró ningún tutor con el dato especificado.");
    }
    tblActivos.setModel(dtm);
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
        tblActivos = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        txtBuscarActivo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fSButtonMD2 = new LIB.FSButtonMD();

        jScrollPane3.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane1.setViewportView(tblInfantes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 375, 303));

        txtBuscarInfante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jPanel1.add(txtBuscarInfante, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 178, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busquda (2).png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, -1, -1));

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
        jPanel1.add(fSButtonMD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 54, -1));

        tblActivos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        tblActivos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblActivos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, 260, 303));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/busquda (2).png"))); // NOI18N
        jButton2.setText("Buscar");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 67, -1));

        txtBuscarActivo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jPanel1.add(txtBuscarActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 178, 20));

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ACTIVAR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(334, 334, 334)
                .addComponent(jLabel1)
                .addContainerGap(342, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, -1, -1));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Infantes");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 60, -1));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Activos");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 100, 60, -1));

        fSButtonMD2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actualizarinfo.png"))); // NOI18N
        fSButtonMD2.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD2.setContentAreaFilled(true);
        fSButtonMD2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD2MousePressed(evt);
            }
        });
        jPanel1.add(fSButtonMD2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 50, 32));

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

        setBounds(0, 0, 837, 597);
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
    private javax.swing.JTable tblActivos;
    private javax.swing.JTable tblInfantes;
    private javax.swing.JTextField txtBuscarActivo;
    private javax.swing.JTextField txtBuscarInfante;
    // End of variables declaration//GEN-END:variables
}
