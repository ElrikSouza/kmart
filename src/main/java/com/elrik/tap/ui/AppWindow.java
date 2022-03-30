package com.elrik.tap.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.elrik.tap.authorization.LoginScene;

/** Singleton do JFrame da aplicação. */
public class AppWindow {
	private static AppWindow INSTANCE;
	private JFrame appFrame;
	private boolean userAuthenticated;

	private void setUpAppFrame() {
		this.appFrame.setSize(AppDimensions.WindowSize.getWidth(),
				AppDimensions.WindowSize.getHeight());
		this.appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.appFrame.setResizable(false);
	}

	private AppWindow() {
		this.appFrame = new JFrame();
		this.userAuthenticated = false;
		this.setUpAppFrame();
	}

	/** Método para obter uma referência deste singleton */
	public static AppWindow getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AppWindow();
		}

		return INSTANCE;
	}

	/** Exibe a tela da aplicação */
	public void showAppWindow() {
		appFrame.setVisible(true);
	}

	public void updateTitle(String newTitle) {
		this.appFrame.setTitle(newTitle);
	}

	/**
	 * Função que troca a cena atual.
	 * 
	 * Caso a scene atual requeira autorização e o usuário não esteja autenticado,
	 * ela redireceiona para a scene de login.
	 * 
	 * @param panel um JPanel ou derivado (Scene, e AuthenticatedScene)
	 */
	public void setCurrentPanel(JPanel panel) {
		if (!isUserAuthenticated() && panel instanceof AuthenticatedScene) {
			this.setCurrentPanel(new LoginScene());
			return;
		}

		appFrame.setContentPane(panel);
	}

	/**
	 * Método para verificar o estado de autorização do usuário.
	 * 
	 * @return um boolean informando se o usuário está logado ou não.
	 */
	public boolean isUserAuthenticated() {
		return userAuthenticated;
	}

	/**
	 * Método para mudar o estado de autenticação do usuário.
	 * 
	 * @param userAuthenticated
	 */
	public void setUserIsAuthenticated(boolean userAuthenticated) {
		this.userAuthenticated = userAuthenticated;
	}
}
