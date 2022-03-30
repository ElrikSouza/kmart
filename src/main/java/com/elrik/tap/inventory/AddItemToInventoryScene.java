package com.elrik.tap.inventory;

/**
 * Cena para adicionar comprar produtos para o estoque
 */
public class AddItemToInventoryScene extends ProductFormScene {

	@Override
	protected void submitForm() {
		this.inventory.buyProduct(getProductFromForm());
		appWindow.setCurrentPanel(new InventoryMainScene());
	}

	public AddItemToInventoryScene() {
		super("Comprar produto e voltar para o indice de produtos");
		appWindow.updateTitle("Adicionar produto ao estoque");
	}
}
