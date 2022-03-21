package com.elrik.tap.inventory;

import java.util.Date;

public record TransactionLog(String transactionType, double totalAmount, Date transactionTimestamp) {

	private String formatTransactionType() {
		if (this.transactionType.equals("buy")) {
			return "Compra de produtos";
		} else {
			return "Venda de produtos";
		}
	}

	@Override
	public String toString() {
		return String.format("(%s) %s - %.2f R$", transactionTimestamp, formatTransactionType(), totalAmount);
	}
}
