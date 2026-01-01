package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Log;
import java.util.List;

public interface LogDao extends CrudDao<Log> {
    List<Log> findByUsuarioId(int usuarioId);
    List<Log> findAllRecent(int limit);
}
