package com.elrik.tap.inventory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import com.elrik.tap.db.DbConn;

/**
 * Classe que contém as ações que o estoque de produtos pode realizar.
 * 
 * Essa classe tem uma instância estática compartilhada.
 */
public class Inventory {
	private ProductRepo productRepo = new ProductRepo();
	private TransactionLogService transactionLogService = new TransactionLogService();
	private Connection conn = DbConn.getConnection();
	private static Inventory INSTANCE;

	/**
	 * Método para pegar a instancia do singleon.
	 * 
	 * @return instância do singleton.
	 */
	public static Inventory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Inventory();
		}

		return INSTANCE;
	}

	/**
	 * Obtem os produtos em estoque.
	 * 
	 * @return ArrayList de produtos em estoque.
	 */
	public ArrayList<Product> getProductsInStock() {
		return this.productRepo.getProductsInStock();
	}

	/**
	 * Procura por um certo produto que possui um determinado código de barras.
	 * 
	 * @param barcode Código de barras do produto a ser procurado.
	 * @return produto.
	 */
	public Product getProductByBarcode(String barcode) {
		return this.productRepo.getProductByBarcode(barcode);
	}

	/**
	 * Método para editar um produto (código de barras não é editável).
	 * 
	 * @param product instancia do produto a ser editado.
	 * @return
	 */
	public boolean editProduct(Product product) {
		return this.productRepo.editProduct(product);
	}

	/**
	 * Método que processa uma compra para o estoque, e registra essa compra nos
	 * logs da aplicação.
	 * 
	 * @param product comprado
	 * @return
	 */
	public boolean buyProduct(Product product) {
		var transactionLog = new TransactionLog("buy", product.cost() * product.inStock(), new Date());
		transactionLogService.saveTransactionLog(transactionLog);
		productRepo.createProduct(product);

		return true;
	}

	/**
	 * Remove um produto do estoque
	 * 
	 * @param barcode Código de barras do produto a ser removido.
	 * @return
	 */

	public boolean removeProduct(String barcode) {
		this.productRepo.deleteProduct(barcode);
		return true;
	}

	/**
	 * Processa uma venda de produtos para um cliente, e gera um log na aplicação.
	 * 
	 * @param purchase dados da compra do cliente.
	 * @return
	 */
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
