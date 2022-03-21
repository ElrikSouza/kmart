package com.elrik.tap.inventory;

import javax.swing.*;

import com.elrik.tap.ui.AppLabel;

import java.awt.Color;
import java.awt.event.*;

public class ProductRow extends JPanel {
	private JButton deleteProductBtn;
	private JButton updateProductBtn;
	private JButton generateTagBtn;
	private AppLabel productNameLabel;
	private AppLabel sellPriceLabel;
	private AppLabel costLabel;
	private AppLabel descriptionLabel;
	private AppLabel unitLabel;
	private AppLabel inStockLabel;
	private AppLabel barCodeLabel;

	private void setupSceneLayout() {
		this.setSize(600, 300);

		this.productNameLabel.setBounds(10, 10, 600, 24);
		this.productNameLabel.resizeFont(24f);

		this.deleteProductBtn.setBounds(10, 100, 100, 24);
		this.updateProductBtn.setBounds(120, 100, 100, 24);
		this.generateTagBtn.setBounds(230, 100, 150, 24);

		this.sellPriceLabel.setBounds(420, 10, 150, 30);
		this.costLabel.setBounds(420, 40, 150, 30);
		this.inStockLabel.setBounds(420, 70, 150, 30);
		this.descriptionLabel.setBounds(10, 30, 400, 60);
	}

	private void addComponentsToTheScene() {
		this.add(productNameLabel);
		this.add(sellPriceLabel);
		this.add(costLabel);
		this.add(descriptionLabel);
		this.add(unitLabel);
		this.add(inStockLabel);
		this.add(barCodeLabel);
		this.add(deleteProductBtn);
		this.add(updateProductBtn);
		this.add(generateTagBtn);
	}

	public ProductRow(Product product, ActionListener handleDeleteProduct, ActionListener handleUpdateProduct,
			ActionListener handleGenerateTag) {

		this.deleteProductBtn = new JButton("Remover");
		this.updateProductBtn = new JButton("Editar");
		this.generateTagBtn = new JButton("Gerar etiqueta");

		deleteProductBtn.addActionListener(handleDeleteProduct);
		updateProductBtn.addActionListener(handleUpdateProduct);
		generateTagBtn.addActionListener(handleGenerateTag);

		this.productNameLabel = new AppLabel(product.name());
		this.sellPriceLabel = new AppLabel("Preco venda: " + product.sellPrice() + " R$");
		this.costLabel = new AppLabel("Custo: " + product.cost() + " R$");
		this.descriptionLabel = new AppLabel(product.description());
		this.unitLabel = new AppLabel(product.unit());
		this.inStockLabel = new AppLabel(String.format("x%d %s", product.inStock(), product.unit()));
		this.barCodeLabel = new AppLabel(product.barcode());

		this.addComponentsToTheScene();
		this.setBackground(Color.gray);

		this.setLayout(null);
		this.setupSceneLayout();
	}
}
