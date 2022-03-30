package com.elrik.tap.inventory;

import javax.swing.JOptionPane;

/** Cena para editar os produtos do estoque. */
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

	/**
	 * @param productBarcode código de barra do produto a ser editado
	 */
	public EditProductScene(String productBarcode) {
		super("Editar produto");
		appWindow.updateTitle("Editar produto");
		this.product = this.inventory.getProductByBarcode(productBarcode);
		setFormDefaultValues();
	}

	@Override
	protected void submitForm() {
		super.submitForm();

		if (!this.inventory.editProduct(getProductFromForm())) {
			JOptionPane.showMessageDialog(null, "A operacao falhou");
		}

		appWindow.setCurrentPanel(new InventoryMainScene());
	}
}
