package com.elrik.tap.inventory;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

import com.elrik.tap.stats.StatsMainScene;
import com.elrik.tap.ui.AppLabel;
import com.elrik.tap.ui.AuthenticatedScene;

/**
 * Cena que exibe logs tanto de compra como de venda para o operador.
 */
public class TransactionLogScene extends AuthenticatedScene {
	private JButton goBackButton;
	private ArrayList<TransactionLog> transactionLogs;
	private JPanel logListContainer;
	private JScrollPane logListScrollPane;
	private TransactionLogService transactionLogService;

	private void renderLogList() {
		this.logListContainer.setPreferredSize(new Dimension(580, transactionLogs.size() * 50));
		this.logListContainer.removeAll();

		for (var log : transactionLogs) {
			this.logListContainer.add(new AppLabel(log.toString(), 15f));
		}

		this.logListScrollPane.revalidate();
	}

	public TransactionLogScene() {
		appWindow.updateTitle("Logs de transação");
		this.transactionLogService = new TransactionLogService();
		this.goBackButton = new JButton("Voltar");
		this.transactionLogs = this.transactionLogService.getAllLogs();
		this.logListContainer = new JPanel();
		this.logListScrollPane = new JScrollPane(logListContainer);

		this.setLayout(null);

		this.logListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.logListScrollPane.setBounds(100, 50, 600, 600);

		this.goBackButton.setBounds(100, 660, 600, 30);
		this.goBackButton.addActionListener(e -> appWindow.setCurrentPanel(new StatsMainScene()));

		this.add(goBackButton);
		this.add(logListScrollPane);

		this.renderLogList();
	}
}
