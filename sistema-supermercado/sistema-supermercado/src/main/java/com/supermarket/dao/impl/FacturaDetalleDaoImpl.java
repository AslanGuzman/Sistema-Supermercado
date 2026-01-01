package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.FacturaDetalleDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.FacturaDetalle;
import com.supermarket.model.Producto;
import com.supermarket.model.Factura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDetalleDaoImpl extends DatabaseDao implements FacturaDetalleDao {

    @Override
    public List<FacturaDetalle> findByFacturaId(int facturaId) {
        List<FacturaDetalle> list = new ArrayList<>();
        String sql = "SELECT * FROM factura_detalle WHERE factura_id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, facturaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(FacturaDetalle d) {
        String sql = "INSERT INTO factura_detalle (factura_id, producto_id, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, d.getFactura().getId());
            stmt.setInt(2, d.getProducto().getId());
            stmt.setInt(3, d.getCantidad());
            stmt.setDouble(4, d.getPrecioUnitario());
            stmt.setDouble(5, d.getSubtotal());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    private FacturaDetalle map(ResultSet rs) throws SQLException {
        return FacturaDetalle.builder()
                .id(rs.getInt("id"))
                .cantidad(rs.getInt("cantidad"))
                .precioUnitario(rs.getDouble("precio_unitario"))
                .subtotal(rs.getDouble("subtotal"))
                .producto(Producto.builder().id(rs.getInt("producto_id")).build())
                .factura(Factura.builder().id(rs.getInt("factura_id")).build())
                .build();
    }

	@Override
	public FacturaDetalle findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FacturaDetalle> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(FacturaDetalle entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}
