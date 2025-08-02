/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorproyectos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 *
 * @author tatia
 */
public class ConexionBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/gestor_proyectos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    private static Connection conexion = null;
    
    private ConexionBD() {} // Constructor privado para evitar instancias
    
    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Configuración adicional para mejor manejo de conexión
                Properties props = new Properties();
                props.setProperty("user", USER);
                props.setProperty("password", PASSWORD);
                props.setProperty("ssl", "false");
                props.setProperty("connectTimeout", "5"); // 5 segundos de timeout
                
                conexion = DriverManager.getConnection(URL, props);
                conexion.setAutoCommit(false); // Mejor control de transacciones
            } catch (SQLException e) {
                throw new SQLException("Error al conectar a la base de datos: " + e.getMessage(), e);
            }
        }
        return conexion;
    }
    
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.getAutoCommit()) {
                    conexion.commit(); // Asegurar commit pendiente
                }
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    public static void rollback() {
        if (conexion != null) {
            try {
                conexion.rollback();
            } catch (SQLException e) {
                System.err.println("Error al hacer rollback: " + e.getMessage());
            }
        }
    }
}
