package com.elrik.tap.home;

import javax.swing.JButton;

import com.elrik.tap.inventory.InventoryMainScene;
import com.elrik.tap.inventory.SellProductScene;
import com.elrik.tap.stats.StatsMainScene;
import com.elrik.tap.ui.AuthenticatedScene;

public class HomeScene extends AuthenticatedScene {
	private JButton openSellProductsViewBtn;
	private JButton openInventoryViewButton;
	private JButton openStatsViewButton;

	private void openStatsView() {
		appWindow.setCurrentPanel(new StatsMainScene());
	}

	private void openInventoryView() {
		appWindow.setCurrentPanel(new InventoryMainScene());
	}

	private void openSellProductsView() {
		appWindow.setCurrentPanel(new SellProductScene());
	}

	private void setupSceneComponents() {
		this.openSellProductsViewBtn.setBounds(60, 195, 700, 50);
		this.openInventoryViewButton.setBounds(60, 295, 700, 50);
		this.openStatsViewButton.setBounds(60, 395, 700, 50);

		openSellProductsViewBtn.addActionListener(e -> this.openSellProductsView());
		openInventoryViewButton.addActionListener(e -> this.openInventoryView());
		openStatsViewButton.addActionListener(e -> openStatsView());
	}

	public HomeScene() {
		appWindow.updateTitle("Home");
		this.setLayout(null);

		openInventoryViewButton = new JButton("Gerenciar Estoque");
		openSellProductsViewBtn = new JButton("Vender produtos");
		openStatsViewButton = new JButton("Ver estatisticas e logs");

		this.add(openSellProductsViewBtn);
		this.add(openInventoryViewButton);
		this.add(openStatsViewButton);

		setupSceneComponents();
	}
}
