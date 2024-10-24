/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD.CONTROLADOR;

/**
 *
 * @author Nathalia y Andres
 */
import CRUD.MODELO.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ControladorCrud {

    private DatabaseConnection dbConnection;
    private Connection connection;

    public ControladorCrud() {
        dbConnection = new DatabaseConnection();
    }

    public void conectar() {
        connection = dbConnection.connect(); // Conectar a la base de datos
    }

    // Método para crear un cliente
    public boolean crearCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (cedula, nombre_completo, sisben, direccion, telefono, correo_electronico) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cliente.getCedula());
            statement.setString(2, cliente.getNombreCompleto());
            statement.setString(3, cliente.getSisben());
            statement.setString(4, cliente.getDireccion());
            statement.setString(5, cliente.getTelefono());
            statement.setString(6, cliente.getCorreoElectronico());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para leer un cliente por cédula
    public Cliente obtenerCliente(String cedula) {
        String sql = "SELECT * FROM cliente WHERE cedula = ?";
        Cliente cliente = null;
        try (Connection connection = dbConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cedula);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cliente = new Cliente(
                        resultSet.getString("cedula"),
                        resultSet.getString("nombre_completo"),
                        resultSet.getString("sisben"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"),
                        resultSet.getString("correo_electronico")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cliente: " + e.getMessage());
        }
        return cliente;
    }

    // Método para actualizar un cliente
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre_completo = ?, sisben = ?, direccion = ?, telefono = ?, correo_electronico = ? WHERE cedula = ?";
        try (Connection connection = dbConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cliente.getNombreCompleto());
            statement.setString(2, cliente.getSisben());
            statement.setString(3, cliente.getDireccion());
            statement.setString(4, cliente.getTelefono());
            statement.setString(5, cliente.getCorreoElectronico());
            statement.setString(6, cliente.getCedula());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un cliente
    public boolean eliminarCliente(String cedula) {
        String sql = "DELETE FROM cliente WHERE cedula = ?";
        try (Connection connection = dbConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cedula);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> listaClientes = new ArrayList<>();
        try (Connection connection = dbConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Cliente cliente = new Cliente(
                        resultSet.getString("cedula"),
                        resultSet.getString("nombre_completo"),
                        resultSet.getString("sisben"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"),
                        resultSet.getString("correo_electronico")
                );
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clientes: " + e.getMessage());
        }
        return listaClientes;
    }

    public void llenarTablaClientes(JTable tablaClientes) {
        DefaultTableModel modelo = new DefaultTableModel();

        // Definir las columnas
        modelo.addColumn("Cédula");
        modelo.addColumn("Nombre Completo");
        modelo.addColumn("Sisben");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo Electrónico");

        // Obtener todos los clientes
        List<Cliente> listaClientes = obtenerTodosLosClientes();

        // Agregar los datos al modelo
        for (Cliente cliente : listaClientes) {
            Object[] fila = {
                cliente.getCedula(),
                cliente.getNombreCompleto(),
                cliente.getSisben(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getCorreoElectronico()
            };
            modelo.addRow(fila);
        }

        // Asignar el modelo al JTable
        tablaClientes.setModel(modelo);
    }
}
