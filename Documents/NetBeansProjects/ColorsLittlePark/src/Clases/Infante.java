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

    public void RegistrarInfante(String nombre, String apellido, int edad, String tutor) {
int id = 0;
boolean error = false;

try {
    boolean idExiste;
    do {
        id = generarID();
        idExiste = VerificaId(id);
        if (idExiste) {
            System.out.println("El ID de Infante ya existe en la base de datos. Generando un nuevo ID...");
        }
    } while (idExiste);
    
    // Una vez que se encuentra un ID único, asignarlo al infante
    a.setIdInfante(id);
} catch (RuntimeException e) {
    System.out.println("Error: " + e.getMessage());
    error = true;
}

        //       nombre = a.getNombreInfante();
        //      apellido = a.getApellidosInfante();
        try {
            String query = "INSERT INTO infantes(ID_infante, nombre, apellido, edad, tutor) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, id);
            pps.setString(2, nombre);
            pps.setString(3, apellido);
            pps.setInt(4, edad);
            pps.setString(5, String.valueOf(tutor));
            pps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos en la base de datos: " + e.getMessage());
        }
        if (!error) {
            System.out.println("Datos guardados correctamente.");
        }
    }

    public void ModificarInfante(int id, String newnom, String newape, int newedad, String newnum) {
        try {
            // Verificar si el infante con el ID proporcionado existe
            if (!VerificaId(id)) {
                System.out.println("Error: El ID de Infante no existe en la base de datos.");
                return;
            }
            // Actualizar los datos del infante
            String query = "UPDATE infantes SET nombre = ?, apellido = ?, edad = ?, tutor = ? WHERE ID_infante = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setString(1, newnom);
            pps.setString(2, newape);
            pps.setInt(3, newedad);
            pps.setString(4, newnum);
            pps.setInt(5, id);

            int rowsAffected = pps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Datos del infante actualizados correctamente.");
            } else {
                System.out.println("Error: No se pudo actualizar los datos del infante.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar los datos en la base de datos: " + e.getMessage());
        }
    }

    public void EliminarInfante(int id) {
        
        try {
            // Eliminar el infante
            String deleteEquipoQuery = "DELETE FROM infantes WHERE ID_infante = ?";
            try (PreparedStatement deleteinfanteStatement = con.prepareStatement(deleteEquipoQuery)) {
                deleteinfanteStatement.setInt(1, id);
                int rowsAffected = deleteinfanteStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Infante y registros relacionados eliminados con éxito.");
                } else {
                    System.out.println("No se encontró un infante con el ID especificado.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el infante y registros relacionados en la base de datos.");
        }
    }

    public void ConsultarInfante(int id) {
        try {
            // Consultar los datos del infante
            String query;
            query = "SELECT * FROM infantes WHERE ID_infante = ? ";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, id);

            ResultSet rs = pps.executeQuery();

            if (rs.next()) {
                // Recuperar los datos del infante
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int edad = rs.getInt("edad");
                String tutor = rs.getString("tutor");

                // Mostrar los datos del infante en la consola
                System.out.println("ID: " + id);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellido);
                System.out.println("Edad: " + edad);
                System.out.println("Tutor: " + tutor);
            } else {
                System.out.println("No se encontró un infante con el ID especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos en la base de datos: " + e.getMessage());
        }
    }

    public boolean VerificaId(int idinfante) {
        try {
            String query = "SELECT * FROM infantes WHERE ID_infante = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, idinfante);

            ResultSet rs = pps.executeQuery();

            return rs.next();  // Devuelve true si hay un resultado, lo que significa que el ID de infante ya existe
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

    // Método main para probar LAS FUNCIONES
    public static void main(String[] args) {
        Infante infante = new Infante();
        //CONECTAR A BASE DE DATOS
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conector();

//CONSULTAR 
   //     infante.ConsultarInfante(9056);
/*
//INSERTAR
     // Aquí defines los valores que quieres ingresar
    String nombre = "pedro";
    String apellido = "colunga";
    int edad = 8; // Por ejemplo, la edad es 5
    String tutor = "9381111111"; // Por ejemplo, el tutor es 1234567890    
    infante.RegistrarInfante(nombre, apellido, edad, tutor);*/ 
 
//ELIMINAR 
 //   infante.EliminarInfante(4);
/* //MODIFICAR
        int id = 8787;
        String newnom = "chavo";
        String newape = "delocho";
        int newedad = 56;
        infante.ModificarInfante(id, newnom, newape, newedad);
*/
    }

    public void RegistrarInfante(String nombre, String apellido, String edad, String telefono) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
