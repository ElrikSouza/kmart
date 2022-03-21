package com.elrik.tap.authorization;

import java.sql.Connection;

import com.elrik.tap.db.DbConn;

public class AuthorizationService {
	private Connection conn = DbConn.getConnection();

	private String getPasswordUsingTheUsername(String username) {
		try {
			var statement = conn.prepareStatement("SELECT * FROM user_account WHERE name = ?");
			statement.setString(1, username);
			var result = statement.executeQuery();
			result.next();
			var password = result.getString(3);
			return password;
		} catch (Exception e) {
			System.out.println("Um error ocorreu ao tentar pegar as credenciais");
			System.out.println(e);
			return "error";
		}
	}

	public boolean isUserAuthorized(Credentials credentials) {
		String password = getPasswordUsingTheUsername(credentials.username());
		return password.equals(credentials.password());
	}
}
