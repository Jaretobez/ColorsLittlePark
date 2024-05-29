/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package com.mysql.conexion;
package Clases;

//import com.sun.jdi.connect.spi.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ConexionBD {
    private static Connection con;
    
   /* private static final*/ String bd="colorslittlepark";
    /* private static final*/ String driver="com.mysql.cj.jdbc.Driver";
    /* private static final*/ String user="root";
    /* private static final*/ String pass="kiroxd23";
    /* private static final*/ String url="jdbc:mysql://localhost:3306/";
    
    public ConexionBD(){
    
    }
    
    public Connection conector() {
        // Reseteamos a null la conexion a la bd
        con=null;
        try{
            Class.forName(driver);
            // Nos conectamos a la bd
            con= (Connection) DriverManager.getConnection(url+bd, user, pass);
             System.out.println("Conexion establecida a" + bd);
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println("Error de conexion" + bd);
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return con;
    }
    
    public void desconectar() throws SQLException{
        con.close();
    }

    public static void main(String[] args) {
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conector();
    }

        public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (con == null) {
            throw new SQLException("La conexión no está establecida");
        }
        return con.prepareStatement(sql);
    }

        public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }
        }
}

