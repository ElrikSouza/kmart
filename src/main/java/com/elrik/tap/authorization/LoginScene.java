package com.elrik.tap.authorization;

import javax.swing.*;

import com.elrik.tap.home.HomeScene;
import com.elrik.tap.ui.AppLabel;
import com.elrik.tap.ui.Scene;

public class LoginScene extends Scene {
	private JPasswordField passwordField;
	private JTextField usernameField;
	private AppLabel usernameLabel;
	private AppLabel passwordLabel;
	private JButton submitButton;
	private AuthorizationService authorizationService;

	private String getPassword() {
		return String.valueOf(passwordField.getPassword());
	}

	private String getUsername() {
		return usernameField.getText();
	}

	private void submitLogin() {
		if (this.authorizationService.isUserAuthorized(new Credentials(getUsername(), getPassword()))) {
			appWindow.setUserIsAuthenticated(true);
			appWindow.setCurrentPanel(new HomeScene());
		} else {
			JOptionPane.showMessageDialog(null, "Senha e/ou usuario invalidos");
		}

	}

	private void organizeLayout() {
		this.usernameLabel.setBounds(100, 150, 600, 30);
		this.usernameField.setBounds(100, 190, 600, 30);

		this.passwordLabel.setBounds(100, 230, 600, 30);
		this.passwordField.setBounds(100, 270, 600, 30);

		this.submitButton.setBounds(100, 350, 600, 30);
	}

	private void setupScene() {

		this.setLayout(null);

		this.add(usernameLabel);
		this.add(usernameField);
		this.add(passwordLabel);
		this.add(passwordField);
		this.add(submitButton);

		this.submitButton.addActionListener(e -> {
			this.submitLogin();
		});

		this.organizeLayout();
	}

	public LoginScene() {
		this.usernameField = new JTextField();
		this.passwordField = new JPasswordField();
		this.passwordLabel = new AppLabel("Senha", 20f);
		this.usernameLabel = new AppLabel("Username", 20f);
		this.submitButton = new JButton("Logar");
		this.authorizationService = new AuthorizationService();

		this.setupScene();
	}
}
