package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Producto;

public interface ProductoDao extends CrudDao<Producto> {

    Producto findBySku(String sku);
}
