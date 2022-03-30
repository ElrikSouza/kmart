package com.elrik.tap.authorization;

/** Record (Java 14 >=) com as credenciais do usuário. */
public record Credentials(String username, String password) {
}
