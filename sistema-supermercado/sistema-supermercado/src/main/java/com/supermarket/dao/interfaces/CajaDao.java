package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Caja;

public interface CajaDao extends CrudDao<Caja> {
    Caja findOpenCajaByUsuarioId(int usuarioId);
}
