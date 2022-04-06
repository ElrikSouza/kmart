package com.elrik.tap.inventory;

/**
 * Record que relaciona um produto com sua quantidade comprada em uma venda a um
 * cliente
 */
public record PurchaseEntry(Product product, int quantity) {
}
