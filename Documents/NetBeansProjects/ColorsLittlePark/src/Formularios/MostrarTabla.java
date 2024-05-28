/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Formularios;

import Clases.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static java.awt.PageAttributes.MediaType.D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jared
 */
public class MostrarTabla {
    
    ConexionBD con = new ConexionBD();
    
 public DefaultTableModel mostrarTutores()
    {
        String []  nombresColumnas = {"num_telefono","nombre","apellido"};
        String [] registros = new String[3];
        
        DefaultTableModel modelo = new DefaultTableModel(null,nombresColumnas);
        
        String sql = "SELECT * FROM tutores";
            
         Connection connection = null;
        PreparedStatement pst = null;       
        ResultSet rs = null;
        
        try
        {            
            connection = con.conector();
            pst = con.prepareStatement(sql);                                  
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                registros[0] = rs.getString("num_telefono");
                
                registros[1] = rs.getString("nombre");
                
                registros[2] = rs.getString("apellido");
                
                modelo.addRow(registros);           
            }      
          
        }
        catch(SQLException e)
        {         
            JOptionPane.showMessageDialog(null,"Error al conectar");        
        }
  /*      finally
        {
            try
            {
                if (rs != null) rs.close();
                
                if (pst != null) pst.close();
                
                if (con != null) con.close();
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null,e);
            }
        }*/
         return modelo;
    }
    
}
