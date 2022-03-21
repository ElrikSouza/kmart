package com.elrik.tap.inventory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.elrik.tap.db.DbConn;

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

	public void deleteProduct(String barcode) {
		try {
			var statement = this.conn.prepareStatement("DELETE FROM product WHERE barcode = ?");
			statement.setString(1, barcode);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

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
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

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
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public Product getProductByBarcode(String barcode) {
		try {
			var statement = conn.prepareStatement("SELECT * FROM product WHERE barcode = ?");
			statement.setString(1, barcode);
			var result = statement.executeQuery();
			result.next();
			return resultRowToProduct(result);
		} catch (SQLException e) {
			return null;
		}
	}

	public ArrayList<Product> getProductsInStock() {
		try {
			var statement = conn.createStatement();
			var results = statement.executeQuery("SELECT * FROM product");
			var productList = new ArrayList<Product>();

			while (results.next()) {
				productList.add(resultRowToProduct(results));
			}

			return productList;
		} catch (Exception e) {
			return null;
		}
	}
}
