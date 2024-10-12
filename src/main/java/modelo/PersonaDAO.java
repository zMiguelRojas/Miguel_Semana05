/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author miguel
 */
public class PersonaDAO {
    private Conexion conexion = new Conexion();

    // Método para agregar una nueva persona a la base de datos
 public int agregar(Persona persona) {
    String sql = "INSERT INTO persona (nombres, correo, telefono) VALUES (?, ?, ?)";
    try (Connection conn = conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, persona.getNombres());
        ps.setString(2, persona.getCorreo());
        ps.setString(3, persona.getTelefono());
        return ps.executeUpdate(); // Retorna 1 si la inserción fue exitosa
    } catch (Exception e) {
        System.out.println("Error al agregar persona: " + e.getMessage());
        return 0; // Retorna 0 si ocurre un error
    }
}

    // Método para actualizar una persona existente
    public int actualizar(Persona persona) {
        String sql = "UPDATE persona SET nombres=?, correo=?, telefono=? WHERE id=?";
        try (Connection conn = conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, persona.getNombres());
            ps.setString(2, persona.getCorreo());
            ps.setString(3, persona.getTelefono());
            ps.setInt(4, persona.getId());
            return ps.executeUpdate(); // Retorna 1 si la actualización fue exitosa
        } catch (Exception e) {
            System.out.println("Error al actualizar persona: " + e.getMessage());
            return 0; // Retorna 0 si ocurre un error
        }
    }

// Método para eliminar una persona de la base de datos
public int delete(int id) {
    String sql = "DELETE FROM persona WHERE id = ?";
    try (Connection conn = conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        return ps.executeUpdate(); // Retorna el número de filas afectadas
    } catch (Exception e) {
        System.out.println("Error al eliminar persona: " + e.getMessage());
        return 0; // Retorna 0 si ocurre un error
    }
}


// Método para ajustar los IDs después de una eliminación
private void ajustarIds() {
    String sql = "UPDATE persona SET id = (SELECT ROW_NUMBER() OVER (ORDER BY id) FROM persona AS p2 WHERE p2.id >= p.id)";
    try (Connection conn = conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.executeUpdate(); // Ejecuta la actualización de IDs
    } catch (Exception e) {
        System.out.println("Error al ajustar IDs: " + e.getMessage());
    }
}
    // Método para listar todas las personas de la base de datos
    public List<Persona> listar() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM persona";
        try (Connection conn = conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
               Persona p = new Persona();
                p.setId(rs.getInt("id"));
                p.setNombres(rs.getString("nombres"));
                p.setCorreo(rs.getString("correo"));
                p.setTelefono(rs.getString("telefono"));
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error al listar personas: " + e.getMessage());
        }
        return lista;
    }
}