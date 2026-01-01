package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.LogDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Log;
import com.supermarket.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDaoImpl extends DatabaseDao implements LogDao {

    @Override
    public List<Log> findAll() {
        List<Log> list = new ArrayList<>();
        String sql = "SELECT * FROM logs ORDER BY fecha DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(Log l) {
        String sql = "INSERT INTO logs (usuario_id, accion) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, l.getUsuario().getId());
            stmt.setString(2, l.getAccion());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    private Log map(ResultSet rs) throws SQLException {
        return Log.builder()
                .id(rs.getInt("id"))
                .accion(rs.getString("accion"))
                .fecha(rs.getTimestamp("fecha").toLocalDateTime())
                .usuario(Usuario.builder().id(rs.getInt("usuario_id")).build())
                .build();
    }

	@Override
	public Log findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Log entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Log> findByUsuarioId(int usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Log> findAllRecent(int limit) {
		// TODO Auto-generated method stub
		return null;
	}
}
