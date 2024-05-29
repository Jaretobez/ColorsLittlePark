/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Formularios;

import Clases.ConexionBD;
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
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public DefaultTableModel mostrarTutores() {
        String[] nombresColumnas = {"num_telefono", "nombre", "apellido"};
        String[] registros = new String[3];

        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        String sql = "SELECT * FROM tutores";

        try {
            connection = con.conector();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("num_telefono");

                registros[1] = rs.getString("nombre");

                registros[2] = rs.getString("apellido");

                modelo.addRow(registros);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar");
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

    public DefaultTableModel mostrarInfantes() {
        String[] nomColumnas = {"ID_infante", "nombre", "apellido", "edad", "tutor"};
        String[] registrosinf = new String[5];
        DefaultTableModel modeloinf = new DefaultTableModel(null, nomColumnas);
        String sqlinf = "SELECT * FROM infantes";

        try {
            connection = con.conector();
            pst = con.prepareStatement(sqlinf);
            rs = pst.executeQuery();

            while (rs.next()) {
                registrosinf[0] = rs.getString("ID_infante");
                registrosinf[1] = rs.getString("nombre");
                registrosinf[2] = rs.getString("apellido");
                registrosinf[3] = rs.getString("edad");
                registrosinf[4] = rs.getString("tutor");
                modeloinf.addRow(registrosinf);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar");
        }
        return modeloinf;
    }

    public DefaultTableModel MostrarActivos() {
        String[] nomColumnas = {"idactivo", "infante", "fk_num_telefono_act"};
        String[] registros = new String[3];
        DefaultTableModel modelo = new DefaultTableModel(null, nomColumnas);
        String sql = "SELECT * FROM activos";
        try {
            connection = con.conector();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                registros[0] = rs.getString("idactivo");
                registros[1] = rs.getString("infante");
                registros[2] = rs.getString("fk_num_telefono_act");
                modelo.addRow(registros);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar");
        }
        return modelo;
    }
}
