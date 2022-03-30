package com.elrik.tap.inventory;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.elrik.tap.ui.AppLabel;
import com.elrik.tap.ui.AuthenticatedScene;

public abstract class ProductFormScene extends AuthenticatedScene {
	protected JTextField nameField;
	protected JTextField barcodeField;
	protected JTextField sellPriceField;
	protected JTextField costField;
	protected JTextField inStockField;
	protected JTextField unitField;
	protected JTextArea descriptionField;

	protected AppLabel nameLabel;
	protected AppLabel barcodeLabel;
	protected AppLabel sellPriceLabel;
	protected AppLabel costLabel;
	protected AppLabel inStockLabel;
	protected AppLabel unitLabel;
	protected AppLabel descriptionLabel;

	protected JButton submitButton;
	protected JButton cancelButton;
	protected ProductRepo productRepo;
	protected Inventory inventory = Inventory.getInstance();

	protected String getNameValue() {
		return this.nameField.getText();
	}

	protected String getBarcodeValue() {
		return this.barcodeField.getText();
	}

	protected double getSellPriceValue() {
		return Double.parseDouble(this.sellPriceField.getText());
	}

	protected double getCostValue() {
		return Double.parseDouble(this.costField.getText());
	}

	protected int getInStockValue() {
		return Integer.parseInt(this.inStockField.getText());
	}

	protected String getDescriptionValue() {
		return this.descriptionField.getText();
	}

	protected String getUnitValue() {
		return this.unitField.getText();
	}

	protected Product getProductFromForm() {
		return new Product(getNameValue(), getBarcodeValue(), getDescriptionValue(), getSellPriceValue(),
				getCostValue(), getInStockValue(), getUnitValue());
	}

	protected void submitForm() {
	}

	protected void cancelProductInsertion() {
		appWindow.setCurrentPanel(new InventoryMainScene());
	}

	protected void addComponentsToScene() {
		this.add(nameField);
		this.add(barcodeField);
		this.add(sellPriceField);
		this.add(costField);
		this.add(inStockField);
		this.add(unitField);
		this.add(descriptionField);

		this.add(nameLabel);
		this.add(barcodeLabel);
		this.add(sellPriceLabel);
		this.add(inStockLabel);
		this.add(costLabel);
		this.add(unitLabel);
		this.add(descriptionLabel);

		this.add(submitButton);
		this.add(cancelButton);
	}

	protected void setupSceneComponents() {
		int formWidth = 600;

		this.setLayout(null);
		this.addComponentsToScene();

		this.nameLabel.setBounds(100, 50, formWidth, 30);
		this.nameField.setBounds(100, 80, formWidth, 30);

		this.barcodeLabel.setBounds(100, 110, formWidth, 30);
		this.barcodeField.setBounds(100, 140, formWidth, 30);

		this.sellPriceLabel.setBounds(100, 170, 200, 30);
		this.sellPriceField.setBounds(100, 200, 200, 30);

		this.costLabel.setBounds(400, 170, 300, 30);
		this.costField.setBounds(400, 200, 300, 30);

		this.unitLabel.setBounds(100, 230, 280, 30);
		this.unitField.setBounds(100, 260, 280, 30);

		this.inStockLabel.setBounds(400, 230, 300, 30);
		this.inStockField.setBounds(400, 260, 300, 30);

		this.descriptionLabel.setBounds(100, 290, formWidth, 30);
		this.descriptionField.setBounds(100, 320, formWidth, 60);

		this.submitButton.setBounds(100, 390, formWidth, 30);
		this.cancelButton.setBounds(100, 430, formWidth, 30);
	}

	public ProductFormScene(String submitBtnMessage) {
		this.nameField = new JTextField();
		this.barcodeField = new JTextField();
		this.sellPriceField = new JTextField();
		this.costField = new JTextField();
		this.inStockField = new JTextField();
		this.unitField = new JTextField();
		this.descriptionField = new JTextArea();

		this.nameLabel = new AppLabel("Nome");
		this.barcodeLabel = new AppLabel("Código de barras");
		this.sellPriceLabel = new AppLabel("Preço de venda");
		this.costLabel = new AppLabel("Custo de compra");
		this.inStockLabel = new AppLabel("Em estoque");
		this.unitLabel = new AppLabel("Unidade");
		this.descriptionLabel = new AppLabel("Descrição");

		this.submitButton = new JButton(submitBtnMessage);
		this.submitButton.addActionListener(e -> this.submitForm());

		this.cancelButton = new JButton("Cancelar e voltar para o indice de produtos");
		this.cancelButton.addActionListener(e -> this.cancelProductInsertion());

		this.productRepo = new ProductRepo();

		this.setupSceneComponents();
	}
}
