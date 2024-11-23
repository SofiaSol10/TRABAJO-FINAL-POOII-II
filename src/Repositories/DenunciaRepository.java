/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import Models.Denuncia;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Sofía
 */
public class DenunciaRepository {
    private Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sistemadenuncias?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", 
                "root", 
                "143143"
            );
        } catch (Exception e) {
            throw new RuntimeException("Error de conexión a base de datos", e);
        }
    }

    // Crear una denuncia
    public Denuncia save(Denuncia denuncia) {
        String sql = "INSERT INTO denuncias " +
            "(fecha, hora, distrito, lugarDesc, esAnonimo, nombre, tipoDenu, denuDesc, foto) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setDate(1, Date.valueOf(denuncia.getFecha()));
            stmt.setTime(2, Time.valueOf(denuncia.getHora()));
            stmt.setString(3, denuncia.getDistrito());
            stmt.setString(4, denuncia.getLugarDesc());
            stmt.setBoolean(5, denuncia.isEsAnonimo());
            stmt.setString(6, denuncia.getNombre());
            stmt.setString(7, denuncia.getTipoDenu());
            stmt.setString(8, denuncia.getDenuDesc());
            stmt.setString(9, denuncia.getFoto());
            
            stmt.executeUpdate();
           
            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    denuncia.setId(generatedKeys.getInt(1));
                }
            }
            
            return denuncia;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar denuncia", e);
        }
    }

    // Buscar todas las denuncias
    public List<Denuncia> findAll() {
        List<Denuncia> denuncias = new ArrayList<>();
        String sql = "SELECT * FROM denuncias";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                denuncias.add(new Denuncia.DenunciaBuilder()
                    .setId(rs.getInt("id"))
                    .setFecha(rs.getDate("fecha").toLocalDate())
                    .setHora(rs.getTime("Hora").toLocalTime())
                    .setDistrito(rs.getString("distrito"))
                    .setLugarDesc(rs.getString("lugarDesc"))
                    .setEsAnonimo(rs.getBoolean("esAnonimo"))
                    .setNombre(rs.getString("nombre"))
                    .setTipoDenu(rs.getString("tipoDenu"))
                    .setDenuDesc(rs.getString("denuDesc"))
                    .setFoto(rs.getString("foto"))
                     .build());
                    
                
               
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar denuncias", e);
        }
        
        return denuncias;
    }

    // Buscar por ID
    public Denuncia findById(int id) {
        String sql = "SELECT * FROM denuncias WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                   return new Denuncia.DenunciaBuilder()
                      .setId(rs.getInt("id"))
                    .setFecha(rs.getDate("fecha").toLocalDate())
                    .setHora(rs.getTime("Hora").toLocalTime())
                    .setDistrito(rs.getString("distrito"))
                    .setLugarDesc(rs.getString("lugarDesc"))
                    .setEsAnonimo(rs.getBoolean("esAnonimo"))
                    .setNombre(rs.getString("nombre"))
                    .setTipoDenu(rs.getString("tipoDenu"))
                    .setDenuDesc(rs.getString("denuDesc"))
                    .setFoto(rs.getString("foto"))
                     .build();     
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar denuncia", e);
        }
        
        return null;
    }

    // Eliminar por ID
    public void deleteById(int id) {
        String sql = "DELETE FROM denuncias WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar denuncia", e);
        }
    }
}
