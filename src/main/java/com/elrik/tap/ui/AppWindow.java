package com.elrik.tap.ui;

import javax.swing.JFrame;
// import javax.swing.JPanel;
import javax.swing.JPanel;

import com.elrik.tap.authorization.LoginScene;

public class AppWindow {
	private static AppWindow INSTANCE;
	private JFrame appFrame;
	private boolean userAuthenticated = true;

	private void setUpAppFrame() {
		this.appFrame.setSize(AppDimensions.WindowSize.getWidth(),
				AppDimensions.WindowSize.getHeight());
		this.appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.appFrame.setResizable(false);
	}

	private AppWindow() {
		this.appFrame = new JFrame();
		this.setUpAppFrame();
	}

	public static AppWindow getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AppWindow();
		}

		return INSTANCE;
	}

	public void showAppWindow() {
		appFrame.setVisible(true);
	}

	public void updateTitle(String newTitle) {
		this.appFrame.setTitle(newTitle);
	}

	public void setCurrentPanel(JPanel panel) {
		if (!isUserAuthenticated() && panel instanceof AuthenticatedScene) {
			this.setCurrentPanel(new LoginScene());
			return;
		}
		appFrame.setContentPane(panel);
	}

	public boolean isUserAuthenticated() {
		return userAuthenticated;
	}

	public void setUserIsAuthenticated(boolean userAuthenticated) {
		this.userAuthenticated = userAuthenticated;
	}
}
