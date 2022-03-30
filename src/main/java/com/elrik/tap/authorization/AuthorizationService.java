package com.elrik.tap.authorization;

import java.sql.Connection;

import com.elrik.tap.db.DbConn;

/** Serviço que autentica o usuário na tela de login */
public class AuthorizationService {
	private Connection conn = DbConn.getConnection();

	private String getPasswordUsingTheUsername(String username) {
		try {
			var statement = conn.prepareStatement("SELECT * FROM user_account WHERE name = ?");
			statement.setString(1, username);

			var result = statement.executeQuery();
			result.next();

			var password = result.getString(3);

			statement.close();
			result.close();

			return password;
		} catch (Exception e) {
			System.out.println("Um erro ocorreu ao tentar pegar as credenciais");
			return "error";
		}
	}

	/**
	 * Método para autenticar as credenciais dos usuários.
	 * 
	 * @param credentials as credenciais fornecidas pelo usuário
	 * @return um booleano para indicar o sucesso ou não da operação.
	 */
	public boolean isUserAuthorized(Credentials credentials) {
		String password = getPasswordUsingTheUsername(credentials.username());
		return password.equals(credentials.password());
	}
}
