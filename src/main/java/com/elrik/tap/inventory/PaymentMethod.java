package com.elrik.tap.inventory;

public enum PaymentMethod {

	DEBIT("debit"),
	CREDIT("credit"),
	CASH("cash");

	private String paymentMethod;

	private PaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}
}
