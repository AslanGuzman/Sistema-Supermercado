package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Inventario;

public interface InventarioDao extends CrudDao<Inventario> {
    Inventario findByProductoId(int productoId);
}
