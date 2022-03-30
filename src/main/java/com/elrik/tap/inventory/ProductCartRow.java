package com.elrik.tap.inventory;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.elrik.tap.ui.AppLabel;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Classe que define o componente utilizado para listar produtos no carrinho de
 * compras na tela de vender produtos a um cliente.
 */
public class ProductCartRow extends JPanel {
	private Product product;
	private JButton deleteProductButton;
	private AppLabel productNameLabel;
	private int quantity;

	private Consumer<String> handleDeletionCallback;

	/**
	 * 
	 * @param product                dados do produto.
	 * @param quantity               quantidade no carrinho de compras.
	 * @param handleDeletionCallback callback para remover esse componente da lista.
	 */
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

	/**
	 * Retorna a referencia do produto da linha atual.
	 * 
	 * @return produto
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * 
	 * @return a quantidade a ser vendida.
	 */
	public int getQuantity() {
		return this.quantity;
	}

	private void updateProductNameLabel() {
		this.productNameLabel.setText(String.format("(x%d %s) %s", quantity, product.unit(), product.name()));
	}

	/**
	 * Método utilziado para incrementar a quantidade de produtos a serem vendidos
	 * 
	 * @param numberOfNewItems quantidade do incremento.
	 */
	public void buyMoreItems(int numberOfNewItems) {
		this.quantity += numberOfNewItems;
		this.updateProductNameLabel();
	}
}
