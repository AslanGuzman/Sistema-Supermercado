package com.supermarket.dao.base;

import java.util.List;

public interface CrudDao<T> {

    T findById(int id);

    List<T> findAll();

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(int id);
}
