/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Jared
 */
public class Infante {

    ConexionBD con = new ConexionBD();

    public Infante() {
        a = new Atributos();

    }

    public void RegistrarInfante() {
        int id = 0;
        boolean error = false;
        String nombre;
        String apellido;

        try {
            id = generarID();
            // Verificar si el ID de infante ya existe
            if (VerificaId(id)) {
                JOptionPane.showMessageDialog(null, "Error: El ID de Equipo ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            a.setIdInfante(id);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            error = true;
        }

        nombre = a.getNombreInfante();
        apellido = a.getApellidosInfante();

        try {
            String query = "INSERT INTO infantes(ID_infante, nombre, apellido) VALUES(?, ?, ?)";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, id);
            pps.setString(2, nombre);
            pps.setString(3, apellido);
            pps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos en la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (!error) {
            JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
        }
    }

    public void ModificarInfante() {

    }

    public void EliminarInfante(int id) {
        
            try {
        // Eliminar el infante 
        String deleteEquipoQuery = "DELETE FROM infantes WHERE ID_infante = ?";
        try (PreparedStatement deleteinfanteStatement = con.prepareStatement(deleteEquipoQuery)) {
            deleteinfanteStatement.setInt(1, id);
            int rowsAffected = deleteinfanteStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Equipo y registros relacionados eliminados con éxito.");

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un equipo con el ID especificado.");
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el equipo y registros relacionados en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }

    public void ConsultarInfante() {

    }

    public boolean VerificaId(int idinfante) {
        try {
            String query = "SELECT * FROM equipos WHERE ID_Equipo = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, idinfante);

            ResultSet rs = pps.executeQuery();

            return rs.next();  // Devuelve true si hay un resultado, lo que significa que el ID de usuario ya existe
        } catch (SQLException e) {
            return false;  // Manejar la excepción de alguna manera adecuada para tu aplicación
        }
    }

    public static int generarID() {
        Random random = new Random();
        // Generar un número aleatorio entre 1000 y 9999
        int id = 1000 + random.nextInt(9000);
        return id;
    }
    
   

    private final Atributos a;
}
