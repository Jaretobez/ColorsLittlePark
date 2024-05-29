/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.sql.*;
import java.util.concurrent.TimeUnit;

public class Venta {

    ConexionBD con = new ConexionBD();

    public Venta() {
        a = new Atributos();

    }

    public void AgregarVenta(String tipo_pago, int fk_activo, Time  hora_entrada, Time  hora_salida) {
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
            String query = "INSERT INTO venta(folio, tiempo, tipo_pago, monto_total, fecha, fk_activo, hora_entrada, hora_salida) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pps = con.prepareStatement(query);
            pps.setInt(1, folio);
            pps.setInt(2, tiempo);
            pps.setString(3, tipo_pago);
            pps.setDouble(4, monto_total);
            pps.setTimestamp(5, fecha);
            pps.setInt(6, fk_activo);
            pps.setTime(7, hora_entrada);
            pps.setTime(8, hora_salida);
            pps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos en la base de datos: " + e.getMessage());
        }
        if (!error) {
            System.out.println("Datos guardados correctamente.");
        }
        /*Folio
tiempo
tipo_pago
monto_total
fecha
id_activo
hora_entrada
hora_salida*/

    }

    public void MostrarVenta() {

    }

    public static int generarFolio() {
        Random random = new Random();
        // Generar un número aleatorio entre 1000 y 9999
        int id = 1000000 + random.nextInt(9000000);
        return id;
    }

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
    
    
        public static double calcularMontoTotal(int tiempoTotal) {
        // Convertir el tiempo total de Timestamp a minutos
     //   long totalMinutes = TimeUnit.MILLISECONDS.toMinutes(tiempoTotal.getTime());        
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
    
        public static void main(String[] args) {
        Venta venta = new Venta();
        //CONECTAR A BASE DE DATOS
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conector();

//CONSULTAR 
   //     infante.ConsultarInfante(9056);

//INSERTAR
  /*   // Aquí defines los valores que quieres ingresar
    String tipo_pago = "tarjeta";
    int fk_activo = 15298;
    Time hora_entrada = Time.valueOf("08:00:00");
    Time hora_salida = Time.valueOf("09:06:00"); 
    venta.AgregarVenta(tipo_pago, fk_activo, hora_entrada, hora_salida);
    
    int minutos_totales = calcularTiempoTotal(hora_entrada, hora_salida);
    System.out.println("tiempo total en minutos:" + minutos_totales );
    double monto = calcularMontoTotal(minutos_totales);
    System.out.println("monto total calculado:" + monto );*/
      
 /*
//ELIMINAR 
 //   infante.EliminarInfante(4);
/* //MODIFICAR
        int id = 8787;
        String newnom = "chavo";
        String newape = "delocho";
        int newedad = 56;
        infante.ModificarInfante(id, newnom, newape, newedad);
    
    String tipo_pago, int fk_activo, Timestamp hora_entrada, Timestamp hora_salida
*/
   }
    }


