/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Activos {

    ConexionBD con = new ConexionBD();

    public Activos() {
        a = new Atributos();
    }

    public void Activar(int id_infante, String num_tutor) {
        if (VerificaInfanteEnActivos(id_infante)) {
            System.out.println("El infante con id " + id_infante + " ya está activo.");
            return; // Salir del método para evitar duplicados
        }

        int id = 0;
        boolean error = false;

        try {
            boolean idverifica;
            do {
                id = generarIdActivo();
                idverifica = VerificaIdActivo(id);
                if (idverifica) {
                    System.out.println("El id del activo ya existe en la base de datos. Generando un nuevo id...");
                }
            } while (idverifica);

            // Una vez que se encuentra un ID único, asignarlo al infante
            a.setIdActivo(id);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            error = true;
        }

        if (!error) {
            try {
                String query = "INSERT INTO activos(idactivo, infante, fk_num_telefono_act) VALUES(?, ?, ?)";
                PreparedStatement pps = con.prepareStatement(query);
                pps.setInt(1, id);
                pps.setInt(2, id_infante);
                pps.setString(3, num_tutor);
                pps.executeUpdate();
                System.out.println("Datos guardados correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al guardar los datos en la base de datos: " + e.getMessage());
            }
        }
    }

    public static int generarIdActivo() {
        Random random = new Random();
        int id = 100 + random.nextInt(900);
        return id;
    }

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
            System.out.println("Error al verificar si el infante ya está en activos: " + e.getMessage());
        }

        return existe;
    }

    private final Atributos a;

    public static void main(String[] args) {
        Activos activos = new Activos();
        //CONECTAR A BASE DE DATOS
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conector();
    }

}
