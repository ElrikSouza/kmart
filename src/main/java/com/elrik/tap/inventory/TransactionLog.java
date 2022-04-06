package com.elrik.tap.inventory;

import java.util.Date;

/**
 * Um log de transacao para venda e compra de produtos.
 */
public record TransactionLog(String transactionType, double totalAmount, Date transactionTimestamp) {

	private String formatTransactionType() {
		if (this.transactionType.equals("buy")) {
			return "Compra de produtos";
		} else {
			return "Venda de produtos";
		}
	}

	/**
	 * Metodo que retorna uma string formatada do log para ser exibida na interface.
	 */
	@Override
	public String toString() {
		return String.format("(%s) %s - %.2f R$", transactionTimestamp, formatTransactionType(), totalAmount);
	}
}
