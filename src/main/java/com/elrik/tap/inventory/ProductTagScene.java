package com.elrik.tap.inventory;

import javax.swing.JButton;

import com.elrik.tap.ui.AppLabel;
import com.elrik.tap.ui.AuthenticatedScene;

/**
 * Cena que gera uma etiqueta para um produto.
 */
public class ProductTagScene extends AuthenticatedScene {
	private AppLabel barcodeLabel;
	private AppLabel nameLabel;
	private AppLabel sellPriceLabel;
	private JButton backButton;

	private void addComponentsToScene() {
		this.add(barcodeLabel);
		this.add(nameLabel);
		this.add(sellPriceLabel);
		this.add(backButton);
	}

	private void setupSceneLayout() {
		this.setLayout(null);

		this.barcodeLabel.setBounds(100, 100, 600, 50);
		this.nameLabel.setBounds(100, 160, 600, 30);
		this.sellPriceLabel.setBounds(100, 200, 600, 30);
		this.backButton.setBounds(100, 240, 300, 30);
	}

	public ProductTagScene(Product product) {
		this.barcodeLabel = new AppLabel("Codigo de barras: " + product.barcode(), 32f);
		this.nameLabel = new AppLabel("Nome: " + product.name(), 24f);
		this.sellPriceLabel = new AppLabel("Preco: " + product.sellPrice(), 24f);

		this.backButton = new JButton("Voltar");
		this.backButton.addActionListener(e -> appWindow.setCurrentPanel(new InventoryMainScene()));

		this.addComponentsToScene();
		this.setupSceneLayout();
	}
}
