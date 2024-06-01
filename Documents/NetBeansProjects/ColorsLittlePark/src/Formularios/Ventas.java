/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.Activos;
import Clases.ConexionBD;
import Clases.Venta;
import com.formdev.flatlaf.FlatLightLaf;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Time;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Jared
 */
public class Ventas extends javax.swing.JFrame {
    ConexionBD con = new ConexionBD();
    DefaultTableModel dtm = new DefaultTableModel();
    Venta venta = new Venta();
    Activos activos = new Activos();
    /**
     * Creates new form Ventas
     */
    public Ventas() {
        initComponents();
        MostrarTabla();
        this.setLocationRelativeTo(null);
        initializeTables();
    }
    
        public void MostrarTabla() {
        MostrarTabla mostrartabla = new MostrarTabla();
        DefaultTableModel modeloActivos = mostrartabla.MostrarActivos();
        tblActivos.setModel(modeloActivos);
        DefaultTableModel modeloVentas = mostrartabla.MostrarVentas();
        tblVentas.setModel(modeloVentas);
    }
        
        // Configurar los listeners para las tablas



    private void initializeTables() {
        addTableSelectionListener(tblActivos);
    }

 private void addTableSelectionListener(JTable table) {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                    if (selectedRow >= 0) {
                        if (table == tblActivos) {
                            try {
                   txtIdInfante.setText(dtm.getValueAt(selectedRow, 1).toString());
                   txtHREntrada.setText(dtm.getValueAt(selectedRow, 3).toString());                               
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


private void RegistrarVenta() {
   String tipo_pago = jComboBox1.getSelectedItem().toString();

    DefaultTableModel dtm2 = (DefaultTableModel) tblActivos.getModel();
    int selectedRow = tblActivos.getSelectedRow();
    
    if (selectedRow >= 0) {
        try {
            String idInfante = txtIdInfante.getText();

            String hrEntrada = txtHREntrada.getText();
            
            // Convertir idInfante a int
            int idinf = Integer.parseInt(idInfante);
            
            // Convertir hora_entrada a Time
            Time horaEntrada = Time.valueOf(hrEntrada);

            // Obtener la hora actual y convertirla a Time
            LocalTime horaActual = LocalTime.now();
            Time horaSalida = Time.valueOf(horaActual);
            
            // Llamar al método para agregar la venta
            venta.AgregarVenta(tipo_pago, idinf, horaEntrada, horaSalida);
           
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error al convertir los datos de la tabla: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la tabla: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione una fila para activar.");
    }
}


    public void ConsultarActivosve() {
    String buscar = txtBuscarAct.getText();
    DefaultTableModel dtm = (DefaultTableModel) tblActivos.getModel();
    // Consultar tutor según el dato proporcionado
    List<String[]> activoInfo = activos.ConsultarActivos(buscar);  
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



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscarActivos = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtIdInfante = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblActivos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtBuscarAct = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        fSButtonMD1 = new LIB.FSButtonMD();
        jLabel6 = new javax.swing.JLabel();
        txtHREntrada = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtBuscarActivos.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ventas");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(396, 396, 396)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addGap(377, 377, 377))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblVentas);

        jLabel2.setText("Tipo de pago");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta debito", "Item 4" }));

        txtIdInfante.setEditable(false);
        txtIdInfante.setBackground(new java.awt.Color(255, 255, 204));
        txtIdInfante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdInfanteActionPerformed(evt);
            }
        });

        jLabel3.setText("ID infante");

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

        jLabel4.setText("Activos");

        jButton1.setText("Buscar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Ventas");

        fSButtonMD1.setBackground(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setText("Generar Venta");
        fSButtonMD1.setColorNormal(new java.awt.Color(255, 0, 0));
        fSButtonMD1.setColorTextHover(new java.awt.Color(255, 255, 255));
        fSButtonMD1.setContentAreaFilled(true);
        fSButtonMD1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fSButtonMD1MousePressed(evt);
            }
        });

        jLabel6.setText("Hora de entrada");

        txtHREntrada.setEditable(false);
        txtHREntrada.setBackground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout txtBuscarActivosLayout = new javax.swing.GroupLayout(txtBuscarActivos);
        txtBuscarActivos.setLayout(txtBuscarActivosLayout);
        txtBuscarActivosLayout.setHorizontalGroup(
            txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdInfante, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fSButtonMD1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHREntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton1)
                        .addGap(28, 28, 28)
                        .addComponent(txtBuscarAct, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18))
            .addComponent(jSeparator1)
            .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                .addGap(440, 440, 440)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jScrollPane1)
                .addGap(88, 88, 88))
        );
        txtBuscarActivosLayout.setVerticalGroup(
            txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addGroup(txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                        .addGroup(txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(txtBuscarAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                    .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(txtBuscarActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(txtIdInfante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(fSButtonMD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(txtBuscarActivosLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(txtHREntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtBuscarActivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtBuscarActivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 914, 659);
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdInfanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdInfanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdInfanteActionPerformed

    private void fSButtonMD1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fSButtonMD1MousePressed
        RegistrarVenta();
        MostrarTabla();
    }//GEN-LAST:event_fSButtonMD1MousePressed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        ConsultarActivosve();
    }//GEN-LAST:event_jButton1MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
       FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private LIB.FSButtonMD fSButtonMD1;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblActivos;
    private javax.swing.JTable tblVentas;
    private javax.swing.JTextField txtBuscarAct;
    private javax.swing.JPanel txtBuscarActivos;
    private javax.swing.JTextField txtHREntrada;
    private javax.swing.JTextField txtIdInfante;
    // End of variables declaration//GEN-END:variables
}
