package com.elrik.tap.inventory;

public class AddItemToInventoryScene extends ProductFormScene {

	@Override
	protected void submitForm() {
		this.inventory.buyProduct(getProductFromForm());
		appWindow.setCurrentPanel(new InventoryMainScene());
	}

	public AddItemToInventoryScene() {
		super("Comprar produto e voltar para o indice de produtos");
	}
}
