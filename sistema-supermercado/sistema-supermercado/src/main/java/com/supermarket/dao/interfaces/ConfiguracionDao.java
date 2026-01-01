package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Configuracion;

public interface ConfiguracionDao extends CrudDao<Configuracion> {
    Configuracion findByClave(String clave);
}
