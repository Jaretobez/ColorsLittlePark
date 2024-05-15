/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Clases.ConexionBD;
import colorslittlepark.Home;

public class InicioDeSesion extends javax.swing.JFrame {

    ConexionBD con = new ConexionBD();
    public InicioDeSesion() {
        initComponents();
        this.setLocationRelativeTo(null);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jEImagePanel1 = new LIB.JEImagePanel();
        jPanelTransparente1 = new LIB.JPanelTransparente();
        jLabel1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanelRound1 = new LIB.JPanelRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        jEImagePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Canada_Mountains_Scenery_488701.jpg"))); // NOI18N
        jEImagePanel1.setLayout(null);

        jPanelTransparente1.setBackground(new java.awt.Color(98, 89, 87));
        jPanelTransparente1.setColorPrimario(new java.awt.Color(26, 54, 101));
        jPanelTransparente1.setColorSecundario(new java.awt.Color(197, 208, 223));
        jPanelTransparente1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Usuario:");
        jPanelTransparente1.add(jLabel1);
        jLabel1.setBounds(0, 140, 300, 30);

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setBorder(null);
        jPanelTransparente1.add(txtPassword);
        txtPassword.setBounds(50, 270, 200, 30);

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(51, 51, 51));
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setToolTipText("");
        txtUsuario.setBorder(null);
        txtUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsuario.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtUsuario.setName(""); // NOI18N
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        jPanelTransparente1.add(txtUsuario);
        txtUsuario.setBounds(50, 180, 200, 30);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user_2499292 (1).png"))); // NOI18N
        jPanelTransparente1.add(jLabel3);
        jLabel3.setBounds(100, 20, 110, 110);

        jPanelRound1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Iniciar");
        jPanelRound1.add(jLabel2);
        jLabel2.setBounds(0, 0, 200, 25);

        jPanelTransparente1.add(jPanelRound1);
        jPanelRound1.setBounds(50, 330, 200, 30);

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Contraseña:");
        jPanelTransparente1.add(jLabel4);
        jLabel4.setBounds(0, 230, 300, 25);

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("X");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
        });
        jPanelTransparente1.add(jLabel5);
        jLabel5.setBounds(260, 0, 40, 50);

        jEImagePanel1.add(jPanelTransparente1);
        jPanelTransparente1.setBounds(130, 30, 300, 410);

        getContentPane().add(jEImagePanel1);
        jEImagePanel1.setBounds(0, 0, 560, 500);

        setSize(new java.awt.Dimension(557, 485));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed
       System.exit(0);
    }//GEN-LAST:event_jLabel5MousePressed

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
            java.util.logging.Logger.getLogger(InicioDeSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InicioDeSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InicioDeSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InicioDeSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InicioDeSesion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private LIB.JEImagePanel jEImagePanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private LIB.JPanelRound jPanelRound1;
    private LIB.JPanelTransparente jPanelTransparente1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
