package com.bjss.service;

import com.bjss.data.PriceBasketSummary;

/**
 * The service class to process Price Basket and apply promotions
 * 
 * @author Suresh
 *
 */
public interface PriceBasketService {

	/**
	 * Add items to basket, calculate totals and apply promotions
	 * 
	 * @param items the items to add to basket
	 * 
	 * @return the price basket summary with totals and promotions applied
	 */
	public PriceBasketSummary processPriceBasket(String... items);

}
