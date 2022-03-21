package com.elrik.tap.stats;

import com.elrik.tap.ui.AppLabel;
import com.elrik.tap.ui.AuthenticatedScene;

public class KMartStatsScene extends AuthenticatedScene {
	private AppLabel totalProductValueLabel;
	private AppLabel totalProductValueLabelValue;

	private AppLabel profitLabel;
	private AppLabel profitLabelValue;

	private AppLabel cashPaymentsLabel;
	private AppLabel cashPaymentsLabelValue;
	private AppLabel debitPaymentsLabel;
	private AppLabel debitPaymentsLabelValue;
	private AppLabel creditPaymentsLabel;
	private AppLabel creditPaymentsLabelValue;

	private AppLabel avgPurchaseCostLabel;
	private AppLabel avgChangeLabel;

	private StatsService statsService;

	private void addComponentsToTheScene() {
		this.add(totalProductValueLabel);
		this.add(totalProductValueLabelValue);

		this.add(profitLabel);
		this.add(profitLabelValue);

		this.add(cashPaymentsLabel);
		this.add(cashPaymentsLabelValue);

		this.add(debitPaymentsLabel);
		this.add(debitPaymentsLabelValue);

		this.add(creditPaymentsLabel);
		this.add(creditPaymentsLabelValue);

		this.add(avgChangeLabel);
		this.add(avgPurchaseCostLabel);
	}

	private void setupSceneLayout() {
		this.setLayout(null);

		this.profitLabel.setBounds(450, 100, 300, 24);
		this.profitLabelValue.setBounds(450, 134, 300, 24);

		this.totalProductValueLabel.setBounds(100, 100, 300, 24);
		this.totalProductValueLabelValue.setBounds(100, 134, 300, 20);

		this.cashPaymentsLabel.setBounds(100, 250, 150, 60);
		this.cashPaymentsLabelValue.setBounds(100, 320, 200, 30);

		this.debitPaymentsLabel.setBounds(330, 250, 150, 60);
		this.debitPaymentsLabelValue.setBounds(330, 320, 200, 30);

		this.creditPaymentsLabel.setBounds(550, 250, 150, 60);
		this.creditPaymentsLabelValue.setBounds(550, 320, 200, 30);

		this.avgPurchaseCostLabel.setBounds(150, 400, 200, 60);
		this.avgChangeLabel.setBounds(450, 400, 280, 60);
	}

	public KMartStatsScene() {
		appWindow.updateTitle("Estatisticas de Compra e Venda");

		this.statsService = new StatsService();

		var paymentMethods = this.statsService.getPaymentMethodStats();
		var profit = this.statsService.getSoldAndBoughtStatsBreakdown();
		var avgPaymentAndChange = this.statsService.getAvgPaymentAndChange();

		this.totalProductValueLabel = new AppLabel("Valor Total Em Produtos", 20f);
		this.totalProductValueLabelValue = new AppLabel(
				String.format("%.3f R$", this.statsService.getTotalValueInProducts()), 16f);

		this.profitLabel = new AppLabel("Lucro", 20f);
		this.profitLabelValue = new AppLabel(String.format("%.2f = (%.2f - %.2f)",
				profit.totalSold() - profit.totalCost(), profit.totalSold(), profit.totalCost()), 16f);

		this.cashPaymentsLabel = new AppLabel("<html>Pagamentos<br>em dinheiro</html>", 16f);
		this.debitPaymentsLabel = new AppLabel("<html>Pagamentos<br>em debito</html>", 16f);
		this.creditPaymentsLabel = new AppLabel("<html>Pagamentos<br>em credito</html>", 16f);

		this.cashPaymentsLabelValue = new AppLabel(String.format("%.2f R$ (%d compras)",
				paymentMethods.soldCash(), paymentMethods.totalCash()), 14f);

		this.creditPaymentsLabelValue = new AppLabel(String.format("%.2f R$ (%d compras)",
				paymentMethods.soldCredit(), paymentMethods.totalCredit(), 14f));

		this.debitPaymentsLabelValue = new AppLabel(String.format("%.2f R$ (%d compras)",
				paymentMethods.soldDebit(), paymentMethods.totalDebit(), 14f));

		this.avgPurchaseCostLabel = new AppLabel(
				String.format("<html>Valor medio das compras<br>%.2f R$</html>",
						avgPaymentAndChange.avgPayment()),
				16f);

		this.avgChangeLabel = new AppLabel(
				String.format("<html>Valor medio do troco<br>%.2f R$</html>",
						avgPaymentAndChange.avgChange()),
				16f);

		this.setupSceneLayout();
		this.addComponentsToTheScene();
	}
}
