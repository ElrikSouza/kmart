package com.elrik.tap.ui;

import javax.swing.JLabel;

/**
 * Extensão da class JLabel para permitir redimensionar a fonte mais facilmente.
 */
public class AppLabel extends JLabel {

	/** Método para redimensionar a fonte atual */
	public void resizeFont(float newSize) {
		this.setFont(this.getFont().deriveFont(newSize));
	}

	public AppLabel(String text, float fontSize) {
		this(text);
		this.resizeFont(fontSize);
	}

	public AppLabel(String text) {
		super(text);
	}
}
