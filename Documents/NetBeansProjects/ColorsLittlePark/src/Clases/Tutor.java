/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Tutor {
    
    ConexionBD con = new ConexionBD();
    
        public Tutor() {
        a = new Atributos();

    }
    
    public void RegistrarTutor(String num, String nombre, String apellido){
        boolean error = false;
        try {
            if (VerificaNum(num)) {
                System.out.println("Error: El Numero ya existe en la base de datos.");
                return;
            }
         //   a.setNumTutor(num);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            error = true;
        }

        //       nombre = a.getNombreInfante();
        //      apellido = a.getApellidosInfante();
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

    public void ConsultarTutor(String num){
            try {
            // Consultar los datos del infante
            String query;
            query = "SELECT * FROM tutores WHERE num_telefono = ? ";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setString(1, num);

            ResultSet rs = pps.executeQuery();

            if (rs.next()) {
//                String num = rs.getString("num_telefono");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                // Mostrar los datos del infante en la consola
                System.out.println("Numero: " + num);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellido);

            } else {
                System.out.println("No se encontró tutor con el numero especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos en la base de datos: " + e.getMessage());
        }
    }  
    
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

    // Método main para probar LAS FUNCIONES
    public static void main(String[] args) {
        Tutor tutor = new Tutor();
        //CONECTAR A BASE DE DATOS
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conector();
        
 /*       //INSERTAR
        // Aquí defines los valores que quieres ingresar
    String num = "9383333333";
    String nombre = "jose";
    String apellido = "contreras";
  
    tutor.RegistrarTutor(num, nombre, apellido)
    
    
    //MODIFICAR
         String num = "9383333333";
        String newnom = "chavo";
        String newape = "delocho";
        tutor.ModificarTutor(num, newnom, newape);;*/
      

//   tutor.EliminarTutor(num);

 //    String num = "9381111111";
     tutor.ConsultarTutor("9381111111");

    }
    
}
