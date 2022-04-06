package com.elrik.tap.inventory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.elrik.tap.db.DbConn;

/**
 * Classe que gerencia os logs de compra e venda.
 */
public class TransactionLogService {
	private Connection conn = DbConn.getConnection();

	/**
	 * Salva um log no banco de dados
	 * 
	 * @param log log a ser salvo
	 * @return
	 */
	public boolean saveTransactionLog(TransactionLog log) {
		try {
			var statement = conn.prepareStatement(
					"INSERT INTO transaction_log(total_amount, transaction_type) VALUES(?,?)");
			statement.setDouble(1, log.totalAmount());
			statement.setString(2, log.transactionType());
			statement.execute();

			statement.close();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private TransactionLog parseLogFromResultSet(ResultSet resultSet) throws SQLException {
		var totalAmount = resultSet.getDouble(2);
		var transactionType = resultSet.getString(3);
		var transactionTimestamp = resultSet.getTimestamp(4);

		var log = new TransactionLog(transactionType, totalAmount, transactionTimestamp);

		return log;
	}

	/**
	 * Pega todos os logs salvos
	 * 
	 * @return arraylist de logs
	 */
	public ArrayList<TransactionLog> getAllLogs() {
		try {
			var statement = conn.createStatement();
			var results = statement.executeQuery("SELECT * FROM transaction_log");
			var logs = new ArrayList<TransactionLog>();

			while (results.next()) {
				logs.add(parseLogFromResultSet(results));
			}

			results.close();
			statement.close();

			return logs;
		} catch (Exception e) {
			return null;
		}
	}
}
