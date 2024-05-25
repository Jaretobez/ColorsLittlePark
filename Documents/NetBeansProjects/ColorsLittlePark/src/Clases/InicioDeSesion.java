/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import colorslittlepark.Home;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class InicioDeSesion {
    
       ConexionBD con = new ConexionBD();
    public InicioDeSesion() {

    }
    
    public void verificarCredenciales( JTextField usuario, JPasswordField contrasena) {
    try{ 
    ResultSet rs = null;
    PreparedStatement ps = null;
    
    Clases.ConexionBD objetoConexion = new Clases.ConexionBD();
    String consulta="SELECT * FROM turno_usuario WHERE turno_usuario.usuario = (?) AND turno_usuario.contrasena = (?);";
    ps=objetoConexion.conector().prepareStatement(consulta);
    
    String contra = String.valueOf(contrasena.getPassword());
    
    ps.setString(1, usuario.getText());
    ps.setString(2, contra);
    
    rs = ps.executeQuery();
    
    if (rs.next ()) {
     JOptionPane.showMessageDialog(null, "El usuario es correcto");
     Home objetoMenu = new Home();
     objetoMenu.setVisible(true);
    ((JFrame) usuario.getTopLevelAncestor()).dispose();
    }
    else{
    JOptionPane.showMessageDialog(null, "El usuario o contraseña incorrecta, vuelva a intentarlo");
    }
    
    }catch (SQLException e){
    JOptionPane.showMessageDialog(null, "Error"+e.toString());
    }

}
}
