package com.elrik.tap.stats;

public record PaymentMethodStats(int totalDebit, double soldDebit, int totalCredit, double soldCredit, int totalCash,
		double soldCash) {
}
