package com.elrik.tap.inventory;

import javax.swing.JOptionPane;

public class EditProductScene extends ProductFormScene {
	private Product product;

	private void setFormDefaultValues() {
		this.nameField.setText(product.name());
		this.descriptionField.setText(product.description());
		this.unitField.setText(product.unit());
		this.barcodeField.setText(product.barcode());
		this.costField.setText(product.cost() + "");
		this.sellPriceField.setText(product.sellPrice() + "");
		this.inStockField.setText(product.inStock() + "");
	}

	public EditProductScene(String productBarcode) {
		super("Editar produto");
		this.product = this.productRepo.getProductByBarcode(productBarcode);
		setFormDefaultValues();
	}

	@Override
	protected void submitForm() {
		super.submitForm();

		if (!this.productRepo.editProduct(getProductFromForm())) {
			JOptionPane.showMessageDialog(null, "A operacao falhou");
		}

		appWindow.setCurrentPanel(new InventoryMainScene());
	}
}
