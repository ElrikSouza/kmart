package com.elrik.tap.ui;

import javax.swing.JOptionPane;

public class AuthenticatedScene extends Scene {
	@Override
	protected void onSceneBuilt() {
		super.onSceneBuilt();

		if (!appWindow.isUserAuthenticated()) {
			JOptionPane.showMessageDialog(null, "Voce nao esta autenticado");
			return;
		}
	}
}
