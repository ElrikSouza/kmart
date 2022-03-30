package com.elrik.tap.ui;

import javax.swing.JOptionPane;

/** Classe que define uma cena que precisa de autenticação para ser utilizada */
public class AuthenticatedScene extends Scene {
	@Override
	protected void onSceneBuilt() {
		super.onSceneBuilt();

		if (!appWindow.isUserAuthenticated()) {
			JOptionPane.showMessageDialog(null, "Voce não está autenticado");
			return;
		}
	}
}
