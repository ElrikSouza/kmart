package com.elrik.tap.inventory;

import java.util.ArrayList;

/**
 * Record que representa uma compra pelo cliente (na venda de produtos).
 */
public record Purchase(ArrayList<PurchaseEntry> purchaseEntries, PaymentMethod paymentMethod, double totalPaid,
		double totalCost,
		double change) {
}
