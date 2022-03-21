package com.elrik.tap.inventory;

public record Product(String name, String barcode, String description, double sellPrice, double cost, int inStock,
		String unit) {
}
