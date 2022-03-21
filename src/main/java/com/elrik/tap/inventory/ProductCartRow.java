package com.elrik.tap.inventory;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.elrik.tap.ui.AppLabel;

import java.awt.*;
import java.util.function.Consumer;

public class ProductCartRow extends JPanel {
	private Product product;
	private JButton deleteProductButton;
	private AppLabel productNameLabel;
	private int quantity;

	private Consumer<String> handleDeletionCallback;

	public ProductCartRow(Product product, int quantity, Consumer<String> handleDeletionCallback) {
		this.product = product;
		this.quantity = quantity;
		this.deleteProductButton = new JButton("X");
		this.handleDeletionCallback = handleDeletionCallback;
		this.productNameLabel = new AppLabel("");
		this.updateProductNameLabel();
		this.productNameLabel.resizeFont(16f);

		this.deleteProductButton.addActionListener(e -> this.handleDeletionCallback.accept(product.barcode()));
		this.setLayout(null);
		this.deleteProductButton.setBounds(240, 0, 50, 50);
		this.productNameLabel.setBounds(0, 0, 230, 50);
		this.setPreferredSize(new Dimension(280, 500));
		this.setSize(new Dimension(280, 500));
		this.add(deleteProductButton);
		this.add(productNameLabel);
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return this.quantity;
	}

	private void updateProductNameLabel() {
		this.productNameLabel.setText(String.format("(x%d %s) %s", quantity, product.unit(), product.name()));
	}

	public void buyMoreItems(int numberOfNewItems) {
		this.quantity += numberOfNewItems;
		this.updateProductNameLabel();
	}
}
