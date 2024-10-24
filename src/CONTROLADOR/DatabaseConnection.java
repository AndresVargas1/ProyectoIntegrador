/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD.CONTROLADOR;

/**
 *
 * @author Nathalia y Andres
 */
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    public PreparedStatement sql;
    public Connection con = null;
    
    public void conectar(){
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "EcoForge";
        String password = "EcoForge";
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(url, user,password);
            JOptionPane.showMessageDialog(null, "Connection succesfully");
        }catch(HeadlessException | ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, "Connection error" +e);
        }
    }

    // Método para cerrar la conexión
    public void desconectar() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
