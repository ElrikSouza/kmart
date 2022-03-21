package com.elrik.tap.inventory;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;

import com.elrik.tap.home.HomeScene;
import com.elrik.tap.ui.AuthenticatedScene;

public class InventoryMainScene extends AuthenticatedScene {
	private ArrayList<Product> allProducts;
	private ArrayList<Product> visibleProducts;
	private Inventory inventory;
	private JButton nextProductPageButton;
	private JButton previousProductPageButton;
	private JButton searchButton;
	private JButton clearSearchButton;
	private JButton addNewProductButton;
	private JButton backToStartButton;
	private JButton openHomeSceneButton;
	private JTextField searchQueryField;
	private JPanel currentProductPagePanel;
	int offset;

	private void renderPaginatedProductList() {
		var visibleProductsLength = visibleProducts.size();
		var paginatedProductListPanel = new JPanel();
		var gridLayout = new GridLayout(3, 1);
		gridLayout.setVgap(30);

		this.setVisible(false);
		if (currentProductPagePanel != null) {
			this.remove(currentProductPagePanel);
		}

		for (int i = offset; i < visibleProductsLength && i < offset + 3; i++) {
			var product = visibleProducts.get(i);
			var productRow = new ProductRow(product, e -> {
				handleProductDelete(product.barcode());
			}, e -> {
				handleProductEdit(product.barcode());
			}, e -> {
				handleTagGeneration(product.barcode());
			});

			paginatedProductListPanel.add(productRow);
		}

		paginatedProductListPanel.setLayout(gridLayout);
		paginatedProductListPanel.setBounds(100, 100, 600, 500);

		this.add(paginatedProductListPanel);
		this.currentProductPagePanel = paginatedProductListPanel;
		this.setVisible(true);
	}

	private void fetchAllProducts() {
		this.allProducts = inventory.getProductsInStock();
		this.visibleProducts = new ArrayList<Product>(this.allProducts.size());
		visibleProducts.addAll(allProducts);
	}

	private void goNextPage() {
		if (offset + 3 < visibleProducts.size()) {
			offset += 3;
			this.renderPaginatedProductList();
		}
	}

	private void goPrevPage() {
		if (offset - 3 >= 0) {
			offset -= 3;
			this.renderPaginatedProductList();
		}
	}

	private void goStartOfTheProductList() {
		offset = 0;
		this.renderPaginatedProductList();
	}

	private String getQueryString() {
		return this.searchQueryField.getText();
	}

	private void handleSearch() {
		var searchQuery = this.getQueryString();
		this.visibleProducts = searchForProducts(searchQuery);
		offset = 0;
		this.renderPaginatedProductList();
	}

	private void handleSearchClear() {
		this.visibleProducts = allProducts;
		this.searchQueryField.setText("");
		this.offset = 0;
		this.renderPaginatedProductList();
	}

	private Product findProduct(String barcode) {
		for (var currentProduct : visibleProducts) {
			if (currentProduct.barcode() == barcode) {
				return currentProduct;
			}
		}

		return null;
	}

	private void handleTagGeneration(String barcode) {
		var product = findProduct(barcode);
		appWindow.setCurrentPanel(new ProductTagScene(product));
	}

	private void handleOpenBuyNewProductScene() {
		appWindow.setCurrentPanel(new AddItemToInventoryScene());
	}

	private void removeProductFromInMemoryProductList(String barcode) {
		this.allProducts.removeIf(product -> product.barcode() == barcode);
		this.visibleProducts = allProducts;
	}

	private void handleProductDelete(String barcode) {
		if (this.inventory.removeProduct(barcode)) {
			removeProductFromInMemoryProductList(barcode);
		}

		this.renderPaginatedProductList();
	}

	private void handleProductEdit(String barcode) {
		appWindow.setCurrentPanel(new EditProductScene(barcode));
	}

	private ArrayList<Product> searchForProducts(String query) {
		var queryResult = new ArrayList<Product>();

		for (var product : allProducts) {
			if (product.name().contains(query)) {
				queryResult.add(product);
			}
		}

		return queryResult;
	}

	private void addComponentsToScene() {
		this.add(previousProductPageButton);
		this.add(nextProductPageButton);
		this.add(searchQueryField);
		this.add(searchButton);
		this.add(clearSearchButton);
		this.add(backToStartButton);
		this.add(addNewProductButton);
		this.add(openHomeSceneButton);
	}

	private void setupSceneComponents() {
		this.setLayout(null);

		this.previousProductPageButton.setBounds(100, 610, 190, 30);
		this.backToStartButton.setBounds(300, 610, 190, 30);
		this.nextProductPageButton.setBounds(500, 610, 200, 30);

		this.openHomeSceneButton.setBounds(100, 650, 280, 30);
		this.addNewProductButton.setBounds(420, 650, 280, 30);

		this.clearSearchButton.setBounds(100, 30, 45, 40);
		this.searchQueryField.setBounds(150, 30, 350, 40);
		this.searchButton.setBounds(520, 30, 180, 40);

		this.openHomeSceneButton.addActionListener(e -> appWindow.setCurrentPanel(new HomeScene()));
		this.nextProductPageButton.addActionListener(e -> this.goNextPage());
		this.previousProductPageButton.addActionListener(e -> this.goPrevPage());
		this.backToStartButton.addActionListener(e -> this.goStartOfTheProductList());
		this.searchButton.addActionListener(e -> this.handleSearch());
		this.clearSearchButton.addActionListener(e -> this.handleSearchClear());
		this.addNewProductButton.addActionListener(e -> {
			this.handleOpenBuyNewProductScene();
		});
	}

	public InventoryMainScene() {
		this.inventory = Inventory.getInstance();
		this.offset = 0;

		this.nextProductPageButton = new JButton("Proximo");
		this.previousProductPageButton = new JButton("Anterior");
		this.searchButton = new JButton("Buscar!");
		this.searchQueryField = new JTextField();
		this.clearSearchButton = new JButton("X");
		this.backToStartButton = new JButton("Inicio da Lista");
		this.addNewProductButton = new JButton("Comprar Produto");
		this.openHomeSceneButton = new JButton("Voltar para home");

		this.fetchAllProducts();

		this.addComponentsToScene();
		this.setupSceneComponents();
		this.renderPaginatedProductList();
	}
}
