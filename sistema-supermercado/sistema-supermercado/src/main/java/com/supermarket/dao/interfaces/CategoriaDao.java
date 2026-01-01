package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Categoria;

public interface CategoriaDao extends CrudDao<Categoria> {
    Categoria findByNombre(String nombre);
}
