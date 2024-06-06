/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Infante {
    ConexionBD con = new ConexionBD();
    public Infante() {
    }

    //funcion Para registrar infante
    public void RegistrarInfante(String nombre, String apellido, int edad, String tutor) {
int id = 0;
boolean error = false;
try {
    boolean idExiste;
    do {
        id = generarID();
        idExiste = VerificaId(id);
 /*       if (idExiste) {
            System.out.println("El ID de Infante ya existe en la base de datos. Generando un nuevo ID...");
        }*/
    } while (idExiste); 
    // Una vez que se encuentra un ID único, asignarlo al infante
} catch (RuntimeException e) {
    System.out.println("Error: " + e.getMessage());
    error = true;
}
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
      /*  if (!error) {
            System.out.println("Datos guardados correctamente.");
        }*/
    }
  
    //funcion Para modificar infante
    public void ModificarInfante(int id, String newnom, String newape, int newedad) {
        try {
            // Verificar si el infante con el ID proporcionado existe
            if (!VerificaId(id)) {
                System.out.println("Error: El ID de Infante no existe en la base de datos.");
                return;
            }
            // Actualizar los datos del infante
            String query = "UPDATE infantes SET nombre = ?, apellido = ?, edad = ? WHERE ID_infante = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setString(1, newnom);
            pps.setString(2, newape);
            pps.setInt(3, newedad);
            pps.setInt(4, id);
    /*        int rowsAffected = pps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Datos del infante actualizados correctamente.");
            } else {
                System.out.println("Error: No se pudo actualizar los datos del infante.");
            }*/
        } catch (SQLException e) {
            System.out.println("Error al actualizar los datos en la base de datos: " + e.getMessage());
        }
    }

    //funcion Para Eliminar infante
    public void EliminarInfante(int id) {  
        try {
            // Eliminar el infante
            String deleteEquipoQuery = "DELETE FROM infantes WHERE ID_infante = ?";
            try (PreparedStatement deleteinfanteStatement = con.prepareStatement(deleteEquipoQuery)) {
                deleteinfanteStatement.setInt(1, id);
                int rowsAffected = deleteinfanteStatement.executeUpdate();
                if (rowsAffected > 0) {
             //       System.out.println("Infante y registros relacionados eliminados con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el infante con el ID especificado.");
                   // System.out.println("No se encontró el infante con el ID especificado.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el infante.");
        }
    }
 //funcion Para consultar infante   
public List<String[]> ConsultarInfante(String valorBusqueda) {
    List<String[]> resultados = new ArrayList<>();
    String[] queries = {
        "SELECT * FROM infantes WHERE ID_infante = ?",
        "SELECT * FROM infantes WHERE nombre = ?",
        "SELECT * FROM infantes WHERE apellido = ?",
        "SELECT * FROM infantes WHERE edad = ?",
        "SELECT * FROM infantes WHERE tutor = ?"
    };

    for (String query : queries) {
        try (PreparedStatement pps = con.prepareStatement(query)) {
             pps.setString(1, valorBusqueda);
             try (ResultSet rs = pps.executeQuery()) {
                while (rs.next()) {
                    String[] infanteInfo = new String[5];
                    infanteInfo[0] = rs.getString("ID_infante");
                    infanteInfo[1] = rs.getString("nombre");
                    infanteInfo[2] = rs.getString("apellido");
                    infanteInfo[3] = rs.getString("edad");
                    infanteInfo[4] = rs.getString("tutor");
   
                    resultados.add(infanteInfo);
                    // Imprimir cada registro encontrado
        //            System.out.println("Registro encontrado: " + Arrays.toString(infanteInfo));
                }
                // Si encontramos resultados, dejamos de buscar
                if (!resultados.isEmpty()) {
                    break;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar los datos en la base de datos: " + e.getMessage());
        }
    }
    return resultados;
}


    
 //funcion Para verificar si ya existe un id en la tabla infantes
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
//funcion Para generar un id de infante
    public static int generarID() {
        Random random = new Random();
        // Generar un número aleatorio entre 1000 y 9999
        int id = 1000 + random.nextInt(9000);
        return id;
    }
}
