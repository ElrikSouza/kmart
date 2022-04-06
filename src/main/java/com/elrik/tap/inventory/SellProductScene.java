package com.elrik.tap.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;

import com.elrik.tap.home.HomeScene;
import com.elrik.tap.ui.AppLabel;
import com.elrik.tap.ui.AuthenticatedScene;

/**
 * Cena de venda de produtos para um cliente final.
 */
public class SellProductScene extends AuthenticatedScene {
	private Inventory inventory;
	private JScrollPane productListScrollPane;
	private JPanel productListContainer;
	private HashMap<String, ProductCartRow> productCartMap = new HashMap<String, ProductCartRow>();

	private JButton addProductToTheCartButton;
	private JButton cancelPurchaseButton;
	private JButton confirmPurchaseButton;

	private JTextField barcodeField;
	private JTextField quantityField;

	private AppLabel barcodeLabel;
	private AppLabel totalPriceLabel;
	private AppLabel priceLabel;
	private AppLabel quantityLabel;

	private double totalPrice = 0;

	private void renderProductList() {
		int numberOfProductRows = productCartMap.size();
		productListContainer.setPreferredSize(new Dimension(250, numberOfProductRows * 50));
		productListScrollPane.setSize(new Dimension(300, numberOfProductRows * 60 + 10));

		productListContainer.removeAll();
		for (var productRow : productCartMap.values()) {
			productRow.setSize(new Dimension(200, 120));
			this.productListContainer.add(productRow);
			this.productListContainer.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		this.productListScrollPane.revalidate();
	}

	private void updateTotalPrice(double priceVariation) {
		this.totalPrice += priceVariation;
		this.totalPriceLabel.setText(totalPrice + " R$");
	}

	private void handleRemoveProductFromCart(String barcode) {
		var productRow = productCartMap.get(barcode);
		this.productCartMap.remove(barcode);
		this.updateTotalPrice(-productRow.getProduct().sellPrice() * productRow.getQuantity());

		this.renderProductList();
	}

	private void addProductToCart(Product product, int quantity) {

		if (quantity < 0) {
			JOptionPane.showMessageDialog(null, "Quantidade de itens comprados deve ser positiva");
			return;
		}

		this.updateTotalPrice(product.sellPrice() * quantity);

		if (productCartMap.containsKey(product.barcode())) {
			var productRow = this.productCartMap.get(product.barcode());
			productRow.buyMoreItems(quantity);
		} else {
			var newProductRow = new ProductCartRow(product, quantity, this::handleRemoveProductFromCart);
			productCartMap.put(product.barcode(), newProductRow);
		}
	}

	private void clearInputFields() {
		this.barcodeField.setText("");
		this.quantityField.setText("");
	}

	private void handleAddProductToTheCart(String barcode) {
		this.addProductToTheCartButton.setEnabled(false);

		var product = inventory.getProductByBarcode(barcode);

		if (product == null) {
			JOptionPane.showMessageDialog(null, "Codigo de barras invalido.");
		} else {
			var quantity = Integer.parseInt(quantityField.getText());
			this.addProductToCart(product, quantity);
			this.renderProductList();
		}

		this.clearInputFields();
		this.addProductToTheCartButton.setEnabled(true);
	}

	private void handleConfirmPayment() {
		var purchaseEntries = new ArrayList<PurchaseEntry>();

		for (var productRow : productCartMap.values()) {
			var product = productRow.getProduct();
			var quantity = productRow.getQuantity();
			purchaseEntries.add(new PurchaseEntry(product, quantity));
		}

		if (purchaseEntries.size() == 0) {
			JOptionPane.showMessageDialog(null, "Compre ao menos 1 produto");
			return;
		}

		appWindow.setCurrentPanel(new PaymentScene(purchaseEntries));
	}

	private void addComponentsToTheScene() {
		this.add(barcodeField);
		this.add(barcodeLabel);
		this.add(totalPriceLabel);
		this.add(addProductToTheCartButton);
		this.add(productListScrollPane);
		this.add(priceLabel);
		this.add(quantityField);
		this.add(quantityLabel);
		this.add(confirmPurchaseButton);
		this.add(cancelPurchaseButton);
	}

	private void setupSceneComponents() {
		this.setLayout(null);

		int offset = 500;
		this.priceLabel.resizeFont(30f);
		this.priceLabel.setBounds(offset, 70, 200, 35);

		this.totalPriceLabel.resizeFont(30f);
		this.totalPriceLabel.setBounds(offset, 110, 200, 35);

		this.barcodeLabel.setBounds(offset, 160, 250, 30);
		this.barcodeField.setBounds(offset, 200, 250, 30);

		this.quantityLabel.setBounds(offset, 230, 250, 30);
		this.quantityField.setBounds(offset, 260, 250, 30);

		this.addProductToTheCartButton.setBounds(offset, 320, 250, 30);
		this.confirmPurchaseButton.setBounds(offset, 360, 250, 30);
		this.cancelPurchaseButton.setBounds(offset, 400, 250, 30);

		this.addProductToTheCartButton
				.addActionListener(e -> handleAddProductToTheCart(this.barcodeField.getText()));

		this.cancelPurchaseButton.addActionListener(e -> appWindow.setCurrentPanel(new HomeScene()));

		this.confirmPurchaseButton.addActionListener(e -> handleConfirmPayment());

		this.productListScrollPane.setLocation(100, 70);
		this.productListContainer.setLayout(new BoxLayout(this.productListContainer, BoxLayout.Y_AXIS));
		this.productListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}

	public SellProductScene() {
		super();
		appWindow.updateTitle("Vender produtos");

		this.inventory = Inventory.getInstance();
		this.productListContainer = new JPanel();
		this.productListScrollPane = new JScrollPane(this.productListContainer);
		this.barcodeField = new JTextField();
		this.barcodeLabel = new AppLabel("Insira o codigo de barras do produto");
		this.addProductToTheCartButton = new JButton("Adicionar Produto");
		this.totalPriceLabel = new AppLabel("0.00R$");
		this.priceLabel = new AppLabel("Preco total");
		this.quantityField = new JTextField();
		this.quantityLabel = new AppLabel("Quantidade");
		this.confirmPurchaseButton = new JButton("Confirmar e Pagar Compra");
		this.cancelPurchaseButton = new JButton("Cancelar e voltar");

		this.addComponentsToTheScene();
		this.setupSceneComponents();
	}
}
