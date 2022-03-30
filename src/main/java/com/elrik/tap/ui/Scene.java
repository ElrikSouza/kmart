package com.elrik.tap.ui;

import javax.swing.JPanel;

/** Classe que define as cenas a serem utilizadas pela aplicação */
public class Scene extends JPanel {
	protected AppWindow appWindow;

	protected void onSceneBuilt() {
	}

	public Scene() {
		this.appWindow = AppWindow.getInstance();

		this.onSceneBuilt();

		this.setSize(AppDimensions.WindowSize.getWidth(),
				AppDimensions.WindowSize.getHeight());

	}
}
