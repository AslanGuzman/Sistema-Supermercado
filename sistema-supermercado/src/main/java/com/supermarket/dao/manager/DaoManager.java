package com.supermarket.dao.manager;

import java.sql.SQLException;
import com.supermarket.dao.impl.*;
import com.supermarket.dao.interfaces.*;


public class DaoManager {
    private static ProductoDao productoDAO;
    private static ClienteDao clienteDAO;
    private static FacturaDao facturaDAO;
    private static EmpleadoDao empleadoDAO;
    private static CajaDao cajaDAO;
    private static BackupRegistroDao backupRegistroDAO;
    private static CategoriaDao categoriaDAO;
    private static ConfiguracionDao configuracionDAO;
    private static FacturaDetalleDao facturaDetalleDAO;
    private static InventarioDao inventarioDAO;
    private static LogDao logDAO;
    private static RolDao rolDAO;
    private static UsuarioDao usuarioDAO;

    public static ProductoDao getProductoDAO() throws SQLException {
        if (productoDAO == null) productoDAO = new ProductoDaoImpl();
        return productoDAO;
    }

    public static ClienteDao getClienteDAO() throws SQLException {
        if (clienteDAO == null) clienteDAO = new ClienteDaoImpl();
        return clienteDAO;
    }

    public static FacturaDao getFacturaDAO() throws SQLException {
        if (facturaDAO == null) facturaDAO = new FacturaDaoImpl();
        return facturaDAO;
    }

    public static EmpleadoDao getEmpleadoDAO() throws SQLException {
        if (empleadoDAO == null) empleadoDAO = new EmpleadoDaoImpl();
        return empleadoDAO;
    }

    public static CajaDao getCajaDAO() throws SQLException {
        if (cajaDAO == null) cajaDAO = new CajaDaoImpl();
        return cajaDAO;
    }

    public static BackupRegistroDao getBackupRegistroDAO() throws SQLException {
        if (backupRegistroDAO == null) backupRegistroDAO = new BackupRegistroDaoImpl();
        return backupRegistroDAO;
    }

    public static CategoriaDao getCategoriaDAO() throws SQLException {
        if (categoriaDAO == null) categoriaDAO = new CategoriaDaoImpl();
        return categoriaDAO;
    }

    public static ConfiguracionDao getConfiguracionDAO() throws SQLException {
        if (configuracionDAO == null) configuracionDAO = new ConfiguracionDaoImpl();
        return configuracionDAO;
    }

    public static FacturaDetalleDao getFacturaDetalleDAO() throws SQLException {
        if (facturaDetalleDAO == null) facturaDetalleDAO = new FacturaDetalleDaoImpl();
        return facturaDetalleDAO;
    }

    public static InventarioDao getInventarioDAO() throws SQLException {
        if (inventarioDAO == null) inventarioDAO = new InventarioDaoImpl();
        return inventarioDAO;
    }

    public static LogDao getLogDAO() throws SQLException {
        if (logDAO == null) logDAO = new LogDaoImpl();
        return logDAO;
    }

    public static RolDao getRolDAO() throws SQLException {
        if (rolDAO == null) rolDAO = new RolDaoImpl();
        return rolDAO;
    }

    public static UsuarioDao getUsuarioDAO() throws SQLException {
        if (usuarioDAO == null) usuarioDAO = new UsuarioDaoImpl();
        return usuarioDAO;
    }

}
