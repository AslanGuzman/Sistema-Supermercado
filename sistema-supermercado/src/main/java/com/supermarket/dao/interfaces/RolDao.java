package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Rol;

public interface RolDao extends CrudDao<Rol> {
    Rol findByNombre(String nombre);
}
