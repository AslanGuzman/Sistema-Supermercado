package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.BackupRegistro;
import java.util.List;

public interface BackupRegistroDao extends CrudDao<BackupRegistro> {
    List<BackupRegistro> findByUsuarioId(int usuarioId);
}
