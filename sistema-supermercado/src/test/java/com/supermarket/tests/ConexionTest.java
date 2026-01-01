package com.supermarket.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.supermarket.config.DatabaseConfig;
import java.sql.Connection;

public class ConexionTest {

	@Test
	void testConexionNoNula() {
	    try {
	        Connection con = DatabaseConfig.getConnection();
	        assertNotNull(con, "La conexión a la base de datos no debe ser nula");
	    } catch (Exception e) {
	        fail("Error al conectar a la base de datos: " + e.getMessage());
	    }
	}


}
