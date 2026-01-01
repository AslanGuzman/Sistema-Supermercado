package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Empleado;

public interface EmpleadoDao extends CrudDao<Empleado> {
    Empleado findByCedula(String cedula);
}
