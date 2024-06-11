/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;


public class HistorialEliminacion {
    ConexionBD con = new ConexionBD();
    Activos activos = new Activos();
    
    // Agrega al infante desactivado a la tabla historial_eliminacion  
    public void AgregarHistoriaEliminados(int idinfante) {
        int id = 0;
        boolean error = false;
        try {
            boolean idExiste;
            do {
                id = generarIdEliminado();
                idExiste = VerificaIdEliminado(id);
            } while (idExiste);
            // Una vez que se encuentra un ID único, asignarlo al infante
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            error = true;
        }
        try {
            // Obtener la fecha y hora actuales
            Date now = new Date();
            java.sql.Date fecha_eliminacion = new java.sql.Date(now.getTime());
            java.sql.Time hora_eliminacion = new java.sql.Time(now.getTime());
            
            String query = "INSERT INTO historial_eliminacion(id_eliminacion, idinfante, fecha_eliminacion, hora_eliminacion) VALUES(?, ?, ?, ?)";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, id);
            pps.setInt(2, idinfante);
            pps.setDate(3, fecha_eliminacion);
            pps.setTime(4, hora_eliminacion);
            pps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos en la base de datos: " + e.getMessage());
        }
    }
    
        //Funcion que generea un ID aleatorio al activar infante
    public static int generarIdEliminado() {
        Random random = new Random();
        int id = 1000 + random.nextInt(9000);
        return id;
    }
    
          //funcion para verificar si el ID generado existe en la tabla historial_eliminacion     
        public boolean VerificaIdEliminado(int id_elim) {
        try {
            String query = "SELECT * FROM historial_eliminacion WHERE id_eliminacion = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, id_elim);
            ResultSet rs = pps.executeQuery();
            return rs.next();  
        } catch (SQLException e) {
            return false; 
        }
    }
}
