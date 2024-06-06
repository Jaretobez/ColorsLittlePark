/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.beans.Statement;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Activos {

    ConexionBD con = new ConexionBD();

    public Activos() {
        a = new Atributos();
    }

    //Funcion para activar al infante
    public void Activar(int id_infante, String num_tutor, String hora_entrada, String nombre_infante) {
        if (VerificaInfanteEnActivos(id_infante)) {
            JOptionPane.showMessageDialog(null, "El infante con id " + id_infante + " ya está activo.");
            return; // Salir del método para evitar duplicados
        }
        int id = 0;
        boolean error = false;
        try {
            boolean idverifica;
            do {
                id = generarIdActivo();
                idverifica = VerificaIdActivo(id);
            } while (idverifica);
            // Una vez que se encuentra un ID único, asignarlo al infante
            a.setIdActivo(id);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            error = true;
        }
        if (!error) {
            try {
                String query = "INSERT INTO activos(idactivo, infante, fk_num_telefono_act, hora_entrada, nomb_infante) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement pps = con.prepareStatement(query);
                pps.setInt(1, id);
                pps.setInt(2, id_infante);
                pps.setString(3, num_tutor);
                pps.setString(4, hora_entrada);
                pps.setString(5, nombre_infante);
                pps.executeUpdate();
            //     JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar los datos en la base de datos:  " + e.getMessage());
            }
        }
    }
    
    public List<String[]> ConsultarActivos(String valorBusqueda) {
    List<String[]> resultados = new ArrayList<>();
    String[] queries = {
        "SELECT * FROM activos WHERE idactivo = ?",
        "SELECT * FROM activos WHERE infante = ?",
        "SELECT * FROM activos WHERE fk_num_telefono_act = ?",
        "SELECT * FROM activos WHERE nomb_infante = ?"
    };
    for (String query : queries) {
        try (PreparedStatement pps = con.prepareStatement(query)) {
             pps.setString(1, valorBusqueda);
             try (ResultSet rs = pps.executeQuery()) {
                while (rs.next()) {
                    String[] infanteInfo = new String[5];
                    infanteInfo[0] = rs.getString("idactivo");
                    infanteInfo[1] = rs.getString("infante");
                    infanteInfo[2] = rs.getString("nomb_infante");
                    infanteInfo[3] = rs.getString("fk_num_telefono_act");
                    infanteInfo[4] = rs.getString("hora_entrada");   
                    resultados.add(infanteInfo);
                }
                // Si encontramos resultados, dejamos de buscar
                if (!resultados.isEmpty()) {
                    break;
                }
            }
        } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, "Error al guardar los datos en la base de datos:  " + e.getMessage());
        }
    }
    return resultados;
}


    //Funcion que generea un ID aleatorio al activar infante
    public static int generarIdActivo() {
        Random random = new Random();
        int id = 100 + random.nextInt(900);
        return id;
    }

    //funcion para verificar si el ID generado existe en la tabla activos
    public boolean VerificaIdActivo(int id) {
        try {
            String query = "SELECT * FROM activos WHERE idactivo = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, id);
            ResultSet rs = pps.executeQuery();
            return rs.next();  // Devuelve true si hay un resultado, lo que significa que el ID de infante ya existe
        } catch (SQLException e) {
            return false;  // Manejar la excepción de alguna manera adecuada para tu aplicación
        }
    }
    
    //funcion para verificar la existencia del infante en la tabla activos
    public boolean VerificaInfanteEnActivos(int id_infante) {
        boolean existe = false;
        String query = "SELECT COUNT(*) FROM activos WHERE infante = ?";

        try (PreparedStatement pps = con.prepareStatement(query)) {
            pps.setInt(1, id_infante);
            try (ResultSet rs = pps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    existe = (count > 0);
                }
            }
        } catch (SQLException e) {
   JOptionPane.showMessageDialog(null, "Error al verificar si el infante ya está en activos: " + e.getMessage());
        }
        return existe;
    }
    
    
        public void Desactivar(int idactivo) throws IOException {
        // Implementar la lógica para desactivar al infante de la tabla de activos
        try {
     //       Connection con = (Connection) new ConexionBD().conector();
            String query = "DELETE FROM activos WHERE idactivo = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, idactivo);
            pps.executeUpdate();
            pps.close();
            con.close();
        } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Error al desactivar el infante: " + e.getMessage());
        }
    }

    private final Atributos a;

}
