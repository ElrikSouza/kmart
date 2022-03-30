package com.elrik.tap.inventory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.elrik.tap.db.DbConn;

/**
 * Repositório de produtos.
 */
public class ProductRepo {
	private Connection conn = DbConn.getConnection();

	private Product resultRowToProduct(ResultSet resultSet) {
		try {
			String barcode = resultSet.getString(1);
			String name = resultSet.getString(2);
			String description = resultSet.getString(3);
			double sellPrice = resultSet.getDouble(4);
			double cost = resultSet.getDouble(5);
			int inStock = resultSet.getInt(6);
			String unit = resultSet.getString(7);
			return new Product(name, barcode, description, sellPrice, cost, inStock, unit);
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Remover produto via código de barra
	 * 
	 * @param barcode código de barra do produto a ser removido.
	 */
	public void deleteProduct(String barcode) {
		try {
			var statement = this.conn.prepareStatement("DELETE FROM product WHERE barcode = ?");
			statement.setString(1, barcode);
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * Apenas cria um produto no banco, sem gerar logs. Esse método só deve ser
	 * utilizado por Inventory.
	 * 
	 * 
	 * @param product Produto a ser criado.
	 */
	public void createProduct(Product product) {
		try {
			var statement = this.conn.prepareStatement(
					"INSERT INTO product(barcode, name, description, sell_price, cost, inStock, unit) values (?,?,?,?,?,?,?)");

			statement.setString(1, product.barcode());
			statement.setString(2, product.name());
			statement.setString(3, product.description());
			statement.setDouble(4, product.sellPrice());
			statement.setDouble(5, product.cost());
			statement.setInt(6, product.inStock());
			statement.setString(7, product.unit());

			statement.execute();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para editar um produto.
	 * 
	 * @param product produto a ser editado (código de barra é usado para achar o
	 *                produto, ele não pode ser alterado)
	 * @return
	 */
	public boolean editProduct(Product product) {
		try {
			var statement = conn.prepareStatement(
					"UPDATE product SET name = ?, description = ?, sell_price = ?, cost = ?, inStock = ?, unit = ? WHERE barcode = ?");
			statement.setString(1, product.name());
			statement.setString(2, product.description());
			statement.setDouble(3, product.sellPrice());
			statement.setDouble(4, product.cost());
			statement.setInt(5, product.inStock());
			statement.setString(6, product.unit());
			statement.setString(7, product.barcode());

			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Acha um produto via código de barras
	 * 
	 * @param barcode código de barras a ser encontrado
	 * @return produto ou null
	 */
	public Product getProductByBarcode(String barcode) {
		try {
			var statement = conn.prepareStatement("SELECT * FROM product WHERE barcode = ?");
			statement.setString(1, barcode);
			var result = statement.executeQuery();
			result.next();
			var product = resultRowToProduct(result);

			statement.close();
			result.close();

			return product;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Acha todos os produtos em estoque
	 * 
	 * @return lista de produtos ou null
	 */
	public ArrayList<Product> getProductsInStock() {
		try {
			var statement = conn.createStatement();
			var results = statement.executeQuery("SELECT * FROM product");
			var productList = new ArrayList<Product>();

			while (results.next()) {
				productList.add(resultRowToProduct(results));
			}

			results.close();
			statement.close();

			return productList;
		} catch (Exception e) {
			return null;
		}
	}
}
