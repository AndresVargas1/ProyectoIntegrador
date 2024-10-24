/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CRUD.CONTROLADOR.ControladorCrud;
import CRUD.CONTROLADOR.DatabaseConnection;
import MODELO.Proyecto;
import com.sun.jdi.connect.spi.Connection;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author Nathalia
 */
public class crudProyecto {
    
    DatabaseConnection dbConnection = new DatabaseConnection();
    Connection connection = dbConnection.conectar();

    
     public boolean crearProyecto(Proyecto proyecto) {
        String sql = "INSERT INTO proyecto() VALUES (?, ?)";
        try (Connection connection = dbConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, proyecto.getCodigo());
            statement.setString(2, proyecto.getNombre());
          
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear Apartamento: " + e.getMessage());
            return false;
        }
    }
}
