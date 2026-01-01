package com.supermarket.dao.base;

import com.supermarket.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseDao {

    protected Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }
}
