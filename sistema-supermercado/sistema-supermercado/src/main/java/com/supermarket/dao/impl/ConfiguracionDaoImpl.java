package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.ConfiguracionDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Configuracion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionDaoImpl extends DatabaseDao implements ConfiguracionDao {

    @Override
    public Configuracion findByClave(String clave) {
        String sql = "SELECT * FROM configuracion WHERE clave=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clave);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    @Override
    public Configuracion findById(int id) {
        String sql = "SELECT * FROM configuracion WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    @Override
    public boolean save(Configuracion cfg) {
        String sql = "INSERT INTO configuracion (clave, valor) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cfg.getClave());
            stmt.setString(2, cfg.getValor());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public boolean update(Configuracion cfg) {
        String sql = "UPDATE configuracion SET valor=? WHERE clave=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cfg.getValor());
            stmt.setString(2, cfg.getClave());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM configuracion WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public List<Configuracion> findAll() {
        List<Configuracion> list = new ArrayList<>();
        String sql = "SELECT * FROM configuracion";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    private Configuracion map(ResultSet rs) throws SQLException {
        return Configuracion.builder()
                .id(rs.getInt("id"))
                .clave(rs.getString("clave"))
                .valor(rs.getString("valor"))
                .build();
    }
}