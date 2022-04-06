package com.elrik.tap.inventory;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.elrik.tap.home.HomeScene;
import com.elrik.tap.ui.AppLabel;
import com.elrik.tap.ui.AuthenticatedScene;

/**
 * Cena de pagamento que pode ser chamada apos a tela de venda de produtos.
 */
public class PaymentScene extends AuthenticatedScene {
	private AppLabel selectPaymentMethodLabel;
	private AppLabel totalPriceLabel;
	private AppLabel totalPaymentLabel;
	private AppLabel changeLabel;

	private JTextField totalPaymentField;
	private JTextField changeField;

	private JComboBox<String> selectPaymentMethodBox;

	private JButton cancelPurchaseButton;
	private JButton confirmPurchaseButton;

	private ArrayList<PurchaseEntry> purchaseEntries;
	private Inventory inventory = Inventory.getInstance();

	double totalPrice;

	private void handleCancelPurchase() {
		appWindow.setCurrentPanel(new HomeScene());
	}

	private PaymentMethod getPaymentMethod() {
		var rawMethod = selectPaymentMethodBox.getSelectedItem() + "";

		if (rawMethod.equals("Debito")) {
			return PaymentMethod.DEBIT;
		} else if (rawMethod.equals("Credito")) {
			return PaymentMethod.CREDIT;
		} else {
			return PaymentMethod.CASH;
		}
	}

	private void handleConfirmPurchase() {
		var paymentMethod = getPaymentMethod();
		var change = Double.parseDouble(changeField.getText());
		var totalPaid = Double.parseDouble(totalPaymentField.getText());

		if (totalPaid - change < totalPrice) {
			JOptionPane.showMessageDialog(null, "Total pago e insuficiente");
			return;
		}

		var purchase = new Purchase(purchaseEntries, paymentMethod, totalPaid,
				getTotalCost(), change);

		this.inventory.processPurchase(purchase);
		appWindow.setCurrentPanel(new HomeScene());
	}

	private double getTotalPrice() {
		double totalPriceSum = 0;

		for (var entry : purchaseEntries) {
			totalPriceSum += entry.quantity() * entry.product().sellPrice();
		}

		return totalPriceSum;
	}

	private double getTotalCost() {
		double totalCost = 0;

		for (var entry : purchaseEntries) {
			totalCost += entry.quantity() * entry.product().cost();
		}

		return totalCost;
	}

	public void addComponentsToTheScene() {
		this.add(selectPaymentMethodLabel);
		this.add(totalPriceLabel);
		this.add(totalPaymentLabel);
		this.add(changeLabel);
		this.add(totalPaymentField);
		this.add(changeField);
		this.add(cancelPurchaseButton);
		this.add(confirmPurchaseButton);
		this.add(selectPaymentMethodBox);
	}

	public void setupSceneLayout() {
		this.setLayout(null);

		this.totalPriceLabel.setBounds(100, 50, 600, 40);
		this.selectPaymentMethodLabel.setBounds(100, 110, 600, 30);
		this.selectPaymentMethodBox.setBounds(100, 150, 600, 30);

		this.totalPaymentLabel.setBounds(100, 190, 300, 30);
		this.changeLabel.setBounds(460, 190, 240, 30);

		this.totalPaymentField.setBounds(100, 230, 250, 30);
		this.changeField.setBounds(460, 230, 240, 30);

		this.confirmPurchaseButton.setBounds(100, 290, 600, 30);
		this.cancelPurchaseButton.setBounds(100, 330, 600, 30);
	}

	public PaymentScene(ArrayList<PurchaseEntry> purchaseEntries) {
		appWindow.updateTitle("Realizar pagamento");
		this.purchaseEntries = purchaseEntries;
		this.totalPrice = getTotalPrice();
		this.selectPaymentMethodLabel = new AppLabel("Selecione o metodo de pagamento", 20f);
		this.totalPriceLabel = new AppLabel(String.format("Total da compra: %.2f R$", totalPrice), 32f);
		this.changeLabel = new AppLabel("Troco");
		this.totalPaymentLabel = new AppLabel("Total pago");

		this.totalPaymentField = new JTextField();
		this.changeField = new JTextField();

		this.cancelPurchaseButton = new JButton("Cancelar compra");
		this.confirmPurchaseButton = new JButton("Confirmar compra");

		this.selectPaymentMethodBox = new JComboBox<String>(new String[] {
				"Debito",
				"Credito",
				"Dinheiro Fisico"
		});

		this.cancelPurchaseButton.addActionListener(e -> handleCancelPurchase());
		this.confirmPurchaseButton.addActionListener(e -> handleConfirmPurchase());

		this.addComponentsToTheScene();
		this.setupSceneLayout();
	}
}
