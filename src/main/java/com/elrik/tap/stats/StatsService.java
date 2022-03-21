package com.elrik.tap.stats;

import java.sql.Connection;

import com.elrik.tap.db.DbConn;

public class StatsService {
	private Connection conn = DbConn.getConnection();

	public SellAndCostStats getSoldAndBoughtStatsBreakdown() {
		try {
			var statement = conn.createStatement();

			var result = statement.executeQuery(
					"select sum(total_paid - change_val) as sell_val, sum(total_cost) as cost_val from purchase");

			result.next();

			var stats = new SellAndCostStats(result.getDouble(1), result.getDouble(2));

			return stats;
		} catch (Exception e) {
			return null;
		}
	}

	public PaymentMethodStats getPaymentMethodStats() {
		try {
			var statement = conn.createStatement();

			var result = statement.executeQuery(
					"select sum(total_paid) as sold, count(id) as total from purchase where payment_method = 'credit'");
			result.next();

			var soldCredit = result.getDouble(1);
			var totalCredit = result.getInt(2);

			statement.close();

			statement = conn.createStatement();

			result = statement.executeQuery(
					"select sum(total_paid) as sold, count(id) as total from purchase where payment_method = 'debit'");
			result.next();

			var soldDebit = result.getDouble(1);
			var totalDebit = result.getInt(2);

			statement.close();

			statement = conn.createStatement();

			result = statement.executeQuery(
					"select sum(total_paid) as sold, count(id) as total from purchase where payment_method = 'cash'");
			result.next();

			var soldCash = result.getDouble(1);
			var totalCash = result.getInt(2);

			return new PaymentMethodStats(totalDebit, soldDebit, totalCredit, soldCredit, totalCash,
					soldCash);

		} catch (Exception e) {
			return null;
		}
	}

	public double getTotalValueInProducts() {
		try {

			var statement = conn.createStatement();
			var result = statement.executeQuery("SELECT SUM(cost * inStock) FROM product");
			result.next();
			var totalValue = result.getDouble(1);
			return totalValue;
		} catch (Exception e) {
			return -1;
		}
	}
}
