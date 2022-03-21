package com.elrik.tap.ui;

public enum AppDimensions {
	WindowSize(750, 800);

	private int height;
	private int width;

	private AppDimensions(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}
}
