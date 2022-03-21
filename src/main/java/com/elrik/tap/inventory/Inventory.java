package com.elrik.tap.inventory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import com.elrik.tap.db.DbConn;

public class Inventory {
	private ProductRepo productRepo = new ProductRepo();
	private TransactionLogService transactionLogService = new TransactionLogService();
	private Connection conn = DbConn.getConnection();
	private static Inventory INSTANCE;

	public static Inventory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Inventory();
		}

		return INSTANCE;
	}

	public ArrayList<Product> getProductsInStock() {
		return this.productRepo.getProductsInStock();
	}

	public Product getProductByBarcode(String barcode) {
		return this.productRepo.getProductByBarcode(barcode);
	}

	public boolean buyProduct(Product product) {
		var transactionLog = new TransactionLog("buy", product.cost() * product.inStock(), new Date());
		transactionLogService.saveTransactionLog(transactionLog);
		productRepo.createProduct(product);

		return true;
	}

	public boolean removeProduct(String barcode) {
		this.productRepo.deleteProduct(barcode);
		return true;
	}

	public boolean processPurchase(Purchase purchase) {
		var transactionLog = new TransactionLog("sell", purchase.totalPaid(), new Date());
		transactionLogService.saveTransactionLog(transactionLog);

		try {
			var statement = conn.prepareStatement(
					"INSERT INTO purchase(payment_method, total_paid, total_cost, change_val) VALUES(?,?,?,?)");

			statement.setString(1, purchase.paymentMethod().getPaymentMethod());
			statement.setDouble(2, purchase.totalPaid());
			statement.setDouble(3, purchase.totalCost());
			statement.setDouble(4, purchase.change());
			statement.execute();
			statement.close();

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
