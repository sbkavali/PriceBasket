package com.bjss.service;

import com.bjss.data.ShoppingBasket;
import com.bjss.data.PriceBasketSummary;

/**
 * Promotions service for applying promotions on shopping basket
 * 
 * @author Suresh
 *
 */
public interface PromotionsService {

	public PriceBasketSummary applyPromotions(ShoppingBasket shoppingBasket);

}
