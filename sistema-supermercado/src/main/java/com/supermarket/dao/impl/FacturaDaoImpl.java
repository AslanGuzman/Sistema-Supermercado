package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.FacturaDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Factura;
import com.supermarket.model.Cliente;
import com.supermarket.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDaoImpl extends DatabaseDao implements FacturaDao {

    @Override
    public Factura findById(int id) {
        String sql = "SELECT * FROM facturas WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }
    
    @Override
    public List<Factura> findByUsuarioId(int id) {
        List<Factura> list = new ArrayList<>();
        String sql = "SELECT f.*"
				+ "FROM facturas f"
				+ "JOIN usuarios u ON f.cliente_id = u.id"
				+ "WHERE u.id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

               while (rs.next()) list.add(map(rs));

           } catch (SQLException e) { e.printStackTrace(); }

           return list;
    }

    @Override
    public List<Factura> findAll() {
        List<Factura> list = new ArrayList<>();
        String sql = "SELECT * FROM facturas ORDER BY fecha DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(Factura f) {
        String sql = "INSERT INTO facturas (cliente_id, usuario_id, total, metodo_pago, membresia_aplicada) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (f.getCliente() != null)
                stmt.setInt(1, f.getCliente().getId());
            else
                stmt.setNull(1, Types.INTEGER);

            stmt.setInt(2, f.getUsuario().getId());
            stmt.setDouble(3, f.getTotal());
            stmt.setString(4, f.getMetodoPago());
            stmt.setBoolean(5, f.isMembresiaAplicada());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    private Factura map(ResultSet rs) throws SQLException {
        return Factura.builder()
                .id(rs.getInt("id"))
                .cliente(rs.getObject("cliente_id") != null ?
                        Cliente.builder().id(rs.getInt("cliente_id")).build() : null)
                .usuario(Usuario.builder().id(rs.getInt("usuario_id")).build())
                .fecha(rs.getTimestamp("fecha").toLocalDateTime())
                .total(rs.getDouble("total"))
                .metodoPago(rs.getString("metodo_pago"))
                .membresiaAplicada(rs.getBoolean("membresia_aplicada"))
                .build();
    }

	@Override
	public boolean update(Factura entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Factura> findByClienteId(int clienteId) {
		List<Factura> list = new ArrayList<>();
		String sql = "SELECT f.*"
				+ "FROM facturas f"
				+ "JOIN clientes c ON f.cliente_id = c.id"
				+ "WHERE c.id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

               while (rs.next()) list.add(map(rs));

           } catch (SQLException e) { e.printStackTrace(); }

           return list;
	}
}
