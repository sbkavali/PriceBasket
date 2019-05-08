package com.bjss.data;

import java.util.HashMap;
import java.util.Map;

/**
 * The shopping basket, a storage of all items and thier quantities in the price
 * basket
 * 
 * @author Suresh
 *
 */
public class ShoppingBasket {

	private Map<Item, Integer> cart = new HashMap<>();

	/**
	 * Add item to shopping cart
	 * 
	 * @param item the item to be added to cart
	 */
	public void addItemToCart(Item item) {
		if (cart.computeIfPresent(item, (k, v) -> v + 1) == null) {
			cart.put(item, 1);
		}
	}

	/**
	 * @return quantity of items added to cart
	 */
	public int getItemCount() {
		Item item = null;

		return cart.getOrDefault(item, 0);
	}

	/**
	 * @return Items and their quantities in the shopping cart
	 */
	public Map<Item, Integer> getCart() {
		return cart;
	}

}
