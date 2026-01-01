package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Cliente;

public interface ClienteDao extends CrudDao<Cliente> {
    Cliente findByCedula(String cedula);
}
