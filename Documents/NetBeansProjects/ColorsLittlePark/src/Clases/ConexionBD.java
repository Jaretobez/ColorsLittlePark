/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import com.mysql.jdbc.Connection;

/**
 *
 * @author Jared
 */
public class ConexionBD {
    private static Connection con;
    
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String user="eugenio";
    private static final String pass="123456";
    private static final String url="jdbc:mysql://localhost:3306/prueba";
    
}
