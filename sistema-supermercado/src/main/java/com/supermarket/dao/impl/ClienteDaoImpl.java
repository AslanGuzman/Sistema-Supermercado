package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.ClienteDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl extends DatabaseDao implements ClienteDao {

    @Override
    public Cliente findById(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public Cliente findByCedula(String cedula) {
        String sql = "SELECT * FROM clientes WHERE cedula = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> list = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(Cliente c) {
        String sql = "INSERT INTO clientes (nombre, apellido, cedula, telefono, email, membresia) VALUES (?,?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getApellido());
            stmt.setString(3, c.getCedula());
            stmt.setString(4, c.getTelefono());
            stmt.setString(5, c.getEmail());
            stmt.setBoolean(6, c.isMembresia());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean update(Cliente c) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, telefono=?, email=?, membresia=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getApellido());
            stmt.setString(3, c.getTelefono());
            stmt.setString(4, c.getEmail());
            stmt.setBoolean(5, c.isMembresia());
            stmt.setInt(6, c.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Cliente map(ResultSet rs) throws SQLException {
        return Cliente.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .apellido(rs.getString("apellido"))
                .cedula(rs.getString("cedula"))
                .telefono(rs.getString("telefono"))
                .email(rs.getString("email"))
                .membresia(rs.getBoolean("membresia"))
                .fechaRegistro(rs.getDate("fecha_registro").toLocalDate())
                .build();
    }
}
