package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.BackupRegistroDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.BackupRegistro;
import com.supermarket.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BackupRegistroDaoImpl extends DatabaseDao implements BackupRegistroDao {

    @Override
    public List<BackupRegistro> findAll() {
        List<BackupRegistro> list = new ArrayList<>();
        String sql = "SELECT * FROM backups ORDER BY fecha DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(BackupRegistro b) {
        String sql = "INSERT INTO backups (usuario_id, ruta) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, b.getUsuario().getId());
            stmt.setString(2, b.getRuta());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    private BackupRegistro map(ResultSet rs) throws SQLException {
        return BackupRegistro.builder()
                .id(rs.getInt("id"))
                .ruta(rs.getString("ruta"))
                .fecha(rs.getTimestamp("fecha").toLocalDateTime())
                .usuario(Usuario.builder().id(rs.getInt("usuario_id")).build())
                .build();
    }

	@Override
	public BackupRegistro findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(BackupRegistro entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BackupRegistro> findByUsuarioId(int usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}
}
