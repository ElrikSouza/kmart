package com.elrik.tap.ui;

import javax.swing.JLabel;

public class AppLabel extends JLabel {
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
