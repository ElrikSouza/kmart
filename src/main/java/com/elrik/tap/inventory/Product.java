package com.elrik.tap.inventory;

/**
 * Record com as informações dos produtos vendidos.
 * 
 * Verifique se a versão do Java instalada suporta Records.
 */
public record Product(String name, String barcode, String description, double sellPrice, double cost, int inStock,
		String unit) {
}
