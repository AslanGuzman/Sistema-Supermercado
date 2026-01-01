package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.UsuarioDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Usuario;
import com.supermarket.model.Rol;
import com.supermarket.model.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImpl extends DatabaseDao implements UsuarioDao {

    @Override
    public Usuario findById(int id) {
        String sql = "SELECT u.*, r.nombre AS rol_nombre "
        		+ "FROM usuarios u"
        		+ "JOIN roles r ON u.rol_id = r.id"
        		+ "WHERE u.id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Usuario findByUsername(String username) {
        String sql = "SELECT u.*, r.nombre AS rol_nombre FROM usuarios u JOIN roles r ON u.rol_id = r.id WHERE u.username = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre AS rol_nombre FROM usuarios u JOIN roles r ON u.rol_id = r.id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean save(Usuario u) {
        String sql = "INSERT INTO usuarios (empleado_id, rol_id, username, password_hash, activo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, u.getEmpleado().getId());
            stmt.setInt(2, u.getRol().getId());
            stmt.setString(3, u.getUsername());
            stmt.setString(4, u.getPasswordHash());
            stmt.setBoolean(5, u.isActivo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public boolean update(Usuario u) {
        String sql = "UPDATE usuarios SET rol_id=?, password_hash=?, activo=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, u.getRol().getId());
            stmt.setString(2, u.getPasswordHash());
            stmt.setBoolean(3, u.isActivo());
            stmt.setInt(4, u.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    private Usuario map(ResultSet rs) throws SQLException {

        return Usuario.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .passwordHash(rs.getString("password_hash"))
                .activo(rs.getBoolean("activo"))
                .empleado(Empleado.builder().id(rs.getInt("empleado_id")).build())
                .rol(Rol.builder().id(rs.getInt("rol_id")).nombre(rs.getString("rol_nombre")).build())
                .build();
    }
}
