package com.elrik.tap.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe utilizada para conseguir uma conexão com o banco.
 * 
 * Essa conexão é única para toda a aplicação.
 */
public class DbConn {
	private static Connection CONN;

	private static void loadDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			System.out.println("Não foi possivel carregar o driver");
			System.exit(0);
		}
	}

	public static Connection getConnection() {
		if (CONN == null) {
			try {
				loadDriver();
				CONN = DriverManager.getConnection("jdbc:mysql://localhost:3306/kmart", "root", "tap");
			} catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}
		}

		return CONN;
	}
}
