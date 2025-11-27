package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.FacturaDetalle;
import java.util.List;

public interface FacturaDetalleDao extends CrudDao<FacturaDetalle> {
    List<FacturaDetalle> findByFacturaId(int facturaId);
}
