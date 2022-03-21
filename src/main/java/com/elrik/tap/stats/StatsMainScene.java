package com.elrik.tap.stats;

import javax.swing.JButton;

import com.elrik.tap.home.HomeScene;
import com.elrik.tap.inventory.TransactionLogScene;
import com.elrik.tap.ui.AuthenticatedScene;

public class StatsMainScene extends AuthenticatedScene {
	private JButton openKMartStatsViewBtn;
	private JButton openTransactionLogsViewButton;
	private JButton openHomeSceneButton;

	private void openHomeSceneView() {
		appWindow.setCurrentPanel(new HomeScene());
	}

	private void openTransactionLogsView() {
		appWindow.setCurrentPanel(new TransactionLogScene());
	}

	private void openKMartStatsView() {
		appWindow.setCurrentPanel(new KMartStatsScene());
	}

	private void setupSceneComponents() {
		this.openKMartStatsViewBtn.setBounds(60, 195, 700, 50);
		this.openTransactionLogsViewButton.setBounds(60, 295, 700, 50);
		this.openHomeSceneButton.setBounds(60, 395, 700, 50);

		openKMartStatsViewBtn.addActionListener(e -> this.openKMartStatsView());
		openTransactionLogsViewButton.addActionListener(e -> this.openTransactionLogsView());
		openHomeSceneButton.addActionListener(e -> openHomeSceneView());
	}

	public StatsMainScene() {
		appWindow.updateTitle("Estatisticas e Logs");
		this.setLayout(null);

		openTransactionLogsViewButton = new JButton("Ver Historico de transacoes");
		openKMartStatsViewBtn = new JButton("Ver estatisticas de vendas");
		openHomeSceneButton = new JButton("Voltar para a home");

		this.add(openKMartStatsViewBtn);
		this.add(openTransactionLogsViewButton);
		this.add(openHomeSceneButton);

		setupSceneComponents();
	}
}
