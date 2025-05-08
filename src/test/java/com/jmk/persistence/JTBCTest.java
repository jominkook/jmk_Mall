package com.jmk.persistence;


import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class JTBCTest {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConnection() {

		try(Connection con =
				DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/risecake_db",
						"minkook",
						"1234")){
			System.out.println("MySQL 연결 성공");
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

}
