package com.supermarket.dao.interfaces;

import com.supermarket.dao.base.CrudDao;
import com.supermarket.model.Usuario;

public interface UsuarioDao extends CrudDao<Usuario> {
    Usuario findByUsername(String username);
}
