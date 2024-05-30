/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import Clases.Atributos;
import Clases.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Tutor {  
    ConexionBD con = new ConexionBD();
        public Tutor() {
        a = new Atributos();
    }
    
        //Funcion para registrar tutor
    public void RegistrarTutor(String num, String nombre, String apellido){
        boolean error = false;
        try {
            if (VerificaNum(num)) {
                System.out.println("Error: El Numero ya existe en la base de datos.");
                return;
            }            
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            error = true;
        }
        try {
            String query = "INSERT INTO tutores(num_telefono, nombre, apellido) VALUES(?, ?, ?)";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setString(1, num);
            pps.setString(2, nombre);
            pps.setString(3, apellido);
            pps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos en la base de datos: " + e.getMessage());
        }
        if (!error) {
            System.out.println("Datos guardados correctamente.");
        }
    }
    
    //Funcion para modificar tutor
    public void ModificarTutor(String num, String newnom, String newape){
            try {
            // Verificar si el infante con el ID proporcionado existe
            if (!VerificaNum(num)) {
                System.out.println("Error: numero de telefono no existe en la base de datos.");
                return;
            }
            // Actualizar los datos del infante
            String query = "UPDATE tutores SET nombre = ?, apellido = ? WHERE num_telefono = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setString(1, newnom);
            pps.setString(2, newape);
            pps.setString(3, num);
            int rowsAffected = pps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Datos del tutor actualizados correctamente.");
            } else {
                System.out.println("Error: No se pudo actualizar los datos del tutor.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar los datos en la base de datos: " + e.getMessage());
        }
    }

    //Funcion para eliminar tutor
    public void EliminarTutor(String num){
        try {
            // Eliminar el infante
            String deleteEquipoQuery = "DELETE FROM tutores WHERE num_telefono = ?";
            try (PreparedStatement deleteinfanteStatement = con.prepareStatement(deleteEquipoQuery)) {
                deleteinfanteStatement.setString(1, num);
                int rowsAffected = deleteinfanteStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("tutor y registros relacionados eliminados con éxito.");
                } else {
                    System.out.println("No se encontró el tutor con el numero especificado.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar tutor y registros relacionados en la base de datos.");
        }
    }

    //Funcion para consultar tutor
public List<String[]> ConsultarTutor(String valorBusqueda) {
    List<String[]> resultados = new ArrayList<>();
    String[] queries = {
        "SELECT * FROM tutores WHERE num_telefono = ?",
        "SELECT * FROM tutores WHERE nombre = ?",
        "SELECT * FROM tutores WHERE apellido = ?"
    };
        for (String query : queries) {
            try (PreparedStatement pps = con.prepareStatement(query)) {
                pps.setString(1, valorBusqueda);
                try (ResultSet rs = pps.executeQuery()) {
                    while (rs.next()) {
                        String[] tutorInfo = new String[3];
                        tutorInfo[0] = rs.getString("num_telefono");
                        tutorInfo[1] = rs.getString("nombre");
                        tutorInfo[2] = rs.getString("apellido");
                        resultados.add(tutorInfo);
                        // Imprimir cada registro encontrado
                        System.out.println("Registro encontrado: " + Arrays.toString(tutorInfo));
                    }
                    // Si encontramos resultados, dejamos de buscar
                    if (!resultados.isEmpty()) {
                        break;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al consultar los datos en la base de datos: " + e.getMessage());
            }
        }

    return resultados;
}



     //Funcion para verificar si existe el telefono en la tabla tutores  
        public boolean VerificaNum(String num) {
        try {
            String query = "SELECT * FROM tutores WHERE num_telefono = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setString(1, num);

            ResultSet rs = pps.executeQuery();

            return rs.next();  // Devuelve true si hay un resultado, lo que significa que el ID de infante ya existe
        } catch (SQLException e) {
            return false;  // Manejar la excepción de alguna manera adecuada para tu aplicación
        }
    }   
    private final Atributos a;  
}
