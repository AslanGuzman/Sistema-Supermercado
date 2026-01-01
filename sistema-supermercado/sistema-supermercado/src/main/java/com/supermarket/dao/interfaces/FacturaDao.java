package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Factura;
import java.util.List;

public interface FacturaDao extends CrudDao<Factura> {
    List<Factura> findByClienteId(int clienteId);
    List<Factura> findByUsuarioId(int usuarioId);
}
