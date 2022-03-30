package com.elrik.tap.inventory;

/** Enum com os métodos de pagamentos aceitos pela aplicação. */
public enum PaymentMethod {

	DEBIT("debit"),
	CREDIT("credit"),
	CASH("cash");

	private String paymentMethod;

	private PaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Retorna o nome do método de pagamento que será utilizado pelo banco de dados,
	 * não as telas.
	 * 
	 * @return paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}
}
