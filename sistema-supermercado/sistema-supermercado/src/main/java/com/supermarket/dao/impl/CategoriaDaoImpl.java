package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.CategoriaDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoImpl extends DatabaseDao implements CategoriaDao {

    @Override
    public Categoria findById(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public Categoria findByNombre(String nombre) {
        String sql = "SELECT * FROM categorias WHERE nombre = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Categoria> findAll() {
        List<Categoria> list = new ArrayList<>();
        String sql = "SELECT * FROM categorias";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(Categoria c) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean update(Categoria c) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());
            stmt.setInt(2, c.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Categoria map(ResultSet rs) throws SQLException {
        return Categoria.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .build();
    }
}
