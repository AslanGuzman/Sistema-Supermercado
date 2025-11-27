package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Caja;
import java.util.List;

public interface CajaDao extends CrudDao<Caja> {
    Caja findOpenCajaByUsuarioId(int usuarioId);
}
