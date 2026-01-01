package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.RolDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDaoImpl extends DatabaseDao implements RolDao {

    @Override
    public Rol findById(int id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public Rol findByNombre(String nombre) {
        String sql = "SELECT * FROM roles WHERE nombre = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Rol> findAll() {
        List<Rol> list = new ArrayList<>();
        String sql = "SELECT * FROM roles";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(Rol r) {
        String sql = "INSERT INTO roles (nombre) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getNombre());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean update(Rol r) {
        String sql = "UPDATE roles SET nombre = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getNombre());
            stmt.setInt(2, r.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM roles WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Rol map(ResultSet rs) throws SQLException {
        return Rol.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .build();
    }
}
