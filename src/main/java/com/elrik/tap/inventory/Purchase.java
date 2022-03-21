package com.elrik.tap.inventory;

import java.util.ArrayList;

public record Purchase(ArrayList<PurchaseEntry> purchaseEntries, PaymentMethod paymentMethod, double totalPaid,
		double totalCost,
		double change) {
}
