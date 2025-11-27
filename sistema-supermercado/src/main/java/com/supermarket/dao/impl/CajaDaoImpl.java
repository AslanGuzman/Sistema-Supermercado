package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.CajaDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Caja;
import com.supermarket.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CajaDaoImpl extends DatabaseDao implements CajaDao {

    @Override
    public Caja findById(int id) {
        String sql = "SELECT * FROM caja WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    @Override
    public Caja findOpenCajaByUsuarioId(int usuarioId) {
        String sql = "SELECT * FROM caja WHERE usuario_id=? AND estado='ABIERTA'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    @Override
    public List<Caja> findAll() {
        List<Caja> list = new ArrayList<>();
        String sql = "SELECT * FROM caja";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(Caja c) {
        String sql = "INSERT INTO caja (usuario_id, fecha_apertura, monto_inicial, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getUsuario().getId());
            stmt.setTimestamp(2, Timestamp.valueOf(c.getFechaApertura()));
            stmt.setDouble(3, c.getMontoInicial());
            stmt.setString(4, c.getEstado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public boolean update(Caja c) {
        String sql = "UPDATE caja SET fecha_cierre=?, monto_final=?, estado=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, c.getFechaCierre() != null ? Timestamp.valueOf(c.getFechaCierre()) : null);
            stmt.setDouble(2, c.getMontoFinal());
            stmt.setString(3, c.getEstado());
            stmt.setInt(4, c.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM caja WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    private Caja map(ResultSet rs) throws SQLException {

        return Caja.builder()
                .id(rs.getInt("id"))
                .usuario(Usuario.builder().id(rs.getInt("usuario_id")).build())
                .fechaApertura(rs.getTimestamp("fecha_apertura").toLocalDateTime())
                .fechaCierre(rs.getTimestamp("fecha_cierre") != null ?
                        rs.getTimestamp("fecha_cierre").toLocalDateTime() : null)
                .montoInicial(rs.getDouble("monto_inicial"))
                .montoFinal(rs.getDouble("monto_final"))
                .estado(rs.getString("estado"))
                .build();
    }
}
