/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.sql.*;
import java.util.concurrent.TimeUnit;

public class Venta {
    ConexionBD con = new ConexionBD();
    Activos activos = new Activos();
    public Venta() {
        a = new Atributos();
    }
    
    //Funcion para agregar registro de venta en la tabla ventas
    public void AgregarVenta(String tipo_pago, Time hora_entrada, Time hora_salida, String nom_infante, int idactivo) throws IOException {
        int folio = 0;
        boolean error = false;
        try {
            boolean folioexist;
            do {
                folio = generarFolio();
                folioexist = VerificaFolio(folio);
                if (folioexist) {
                    System.out.println("El folio de la venta ya existe en la base de datos. Generando un nuevo folio...");
                }
            } while (folioexist);
            // Una vez que se encuentra un ID único, asignarlo al infante
            a.setFolio(folio);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            error = true;
        }
        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        int tiempo = calcularTiempoTotal(hora_entrada, hora_salida);
        double monto_total = calcularMontoTotal(tiempo);
        try {
            String query = "INSERT INTO venta(folio, tiempo, tipo_pago, monto_total, fecha, hora_entrada, hora_salida, nom_infante) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, folio);
            pps.setInt(2, tiempo);
            pps.setString(3, tipo_pago);
            pps.setDouble(4, monto_total);
            pps.setTimestamp(5, fecha);
            pps.setTime(6, hora_entrada);
            pps.setTime(7, hora_salida);
            pps.setString(8,  nom_infante);
            pps.executeUpdate();
            

        } catch (SQLException e) {
            System.out.println("Error al guardar los datos en la base de datos: " + e.getMessage());
        }
        if (!error) {
            System.out.println("Datos guardados correctamente.");
                        activos.Desactivar(idactivo);
        }
    }

    //Funcion para generar un folio de venta aleatorio
    public static int generarFolio() {
        Random random = new Random();
        // Generar un número aleatorio entre 1000 y 9999
        int id = 1000000 + random.nextInt(9000000);
        return id;
    }

    //Funcion para verificar si ya existe el folio generado
    public boolean VerificaFolio(int idinfante) {
        try {
            String query = "SELECT * FROM ventas WHERE folio = ?";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, idinfante);
            ResultSet rs = pps.executeQuery();
            return rs.next();  // Devuelve true si hay un resultado, lo que significa que el ID de infante ya existe
        } catch (SQLException e) {
            return false;  // Manejar la excepción de alguna manera adecuada para tu aplicación
        }
    }

    //Funcion para calcular el Tiempo Total
public static int calcularTiempoTotal(Time hora_entrada, Time hora_salida) {
     /*   if (hora_entrada == null || hora_salida == null) {
            throw new IllegalArgumentException("Las horas de entrada y salida no pueden ser nulas");
        }*/        
        long diftime = hora_salida.getTime() - hora_entrada.getTime();     
 /*       if (diftime < 0) {
            throw new IllegalArgumentException("La hora de salida no puede ser anterior a la hora de entrada");
        }   */    
        // Convertir la diferencia de milisegundos a minutos
        int difMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(diftime);       
        return difMinutes;
    }
    
      //Funcion para calcular el monto total en base al tiempo total
        public static double calcularMontoTotal(int tiempoTotal) {      
        // Reglas de cobro
        if (tiempoTotal <= 5) {
            return 0;
        } else if (tiempoTotal <= 30) {
            return 65;
        } else {
            double monto = 65; // Costo inicial para los primeros 30 minutos
            tiempoTotal -= 30; // Resta los primeros 30 minutos
            
            // Calcular las medias horas adicionales
            while (tiempoTotal > 0) {
                if (tiempoTotal <= 5) {
                    // Tolerancia de 5 minutos
                    break;
                }
                monto += 55;
                tiempoTotal -= 30;
            }
            return monto;
        }
    }
        
    private final Atributos a;
     
    }


