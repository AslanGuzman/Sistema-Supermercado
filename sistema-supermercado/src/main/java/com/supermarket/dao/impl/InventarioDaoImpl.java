package com.supermarket.dao.impl;

import com.supermarket.dao.interfaces.InventarioDao;
import com.supermarket.dao.base.DatabaseDao;
import com.supermarket.model.Inventario;
import com.supermarket.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDaoImpl extends DatabaseDao implements InventarioDao {

    @Override
    public Inventario findByProductoId(int productoId) {
        String sql = "SELECT * FROM inventario WHERE producto_id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    @Override
    public List<Inventario> findAll() {
        List<Inventario> list = new ArrayList<>();
        String sql = "SELECT * FROM inventario";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    @Override
    public boolean save(Inventario i) {
        String sql = "INSERT INTO inventario (producto_id, cantidad, minimo, maximo) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, i.getProducto().getId());
            stmt.setInt(2, i.getCantidad());
            stmt.setInt(3, i.getMinimo());
            stmt.setInt(4, i.getMaximo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public boolean update(Inventario i) {
        String sql = "UPDATE inventario SET cantidad=?, minimo=?, maximo=? WHERE producto_id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, i.getCantidad());
            stmt.setInt(2, i.getMinimo());
            stmt.setInt(3, i.getMaximo());
            stmt.setInt(4, i.getProducto().getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }

    private Inventario map(ResultSet rs) throws SQLException {
        return Inventario.builder()
                .id(rs.getInt("id"))
                .cantidad(rs.getInt("cantidad"))
                .minimo(rs.getInt("minimo"))
                .maximo(rs.getInt("maximo"))
                .producto(Producto.builder().id(rs.getInt("producto_id")).build())
                .build();
    }

	@Override
	public Inventario findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}
