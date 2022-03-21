package com.elrik.tap.inventory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.elrik.tap.db.DbConn;

public class TransactionLogService {
	private Connection conn = DbConn.getConnection();

	public boolean saveTransactionLog(TransactionLog log) {
		try {
			var statement = conn.prepareStatement(
					"INSERT INTO transaction_log(total_amount, transaction_type) VALUES(?,?)");
			statement.setDouble(1, log.totalAmount());
			statement.setString(2, log.transactionType());
			statement.execute();

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

	public ArrayList<TransactionLog> getAllLogs() {
		try {
			var statement = conn.createStatement();
			var results = statement.executeQuery("SELECT * FROM transaction_log");
			var logs = new ArrayList<TransactionLog>();

			while (results.next()) {
				logs.add(parseLogFromResultSet(results));
			}

			return logs;
		} catch (Exception e) {
			return null;
		}
	}
}
