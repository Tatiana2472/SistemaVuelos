/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tatia
 */
public class Conexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_vuelos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admi"; 
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la BD.");
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return conn;
    }
}

