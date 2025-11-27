package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.EmpleadoDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDaoImpl extends DatabaseDao implements EmpleadoDao {

    @Override
    public Empleado findById(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public Empleado findByCedula(String cedula) {
        String sql = "SELECT * FROM empleados WHERE cedula = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Empleado> findAll() {
        List<Empleado> list = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(Empleado e) {
        String sql = "INSERT INTO empleados (nombre, apellido, cedula, telefono, direccion) VALUES (?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getApellido());
            stmt.setString(3, e.getCedula());
            stmt.setString(4, e.getTelefono());
            stmt.setString(5, e.getDireccion());

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    @Override
    public boolean update(Empleado e) {
        String sql = "UPDATE empleados SET nombre=?, apellido=?, telefono=?, direccion=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getApellido());
            stmt.setString(3, e.getTelefono());
            stmt.setString(4, e.getDireccion());
            stmt.setInt(5, e.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Empleado map(ResultSet rs) throws SQLException {
        return Empleado.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .apellido(rs.getString("apellido"))
                .cedula(rs.getString("cedula"))
                .telefono(rs.getString("telefono"))
                .direccion(rs.getString("direccion"))
                .fechaIngreso(rs.getDate("fecha_ingreso").toLocalDate())
                .build();
    }
}
