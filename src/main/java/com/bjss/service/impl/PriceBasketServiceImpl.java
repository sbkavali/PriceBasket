package com.bjss.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bjss.data.ShoppingBasket;
import com.bjss.data.PriceBasketSummary;
import com.bjss.exception.NoItemsInShoppingBasketException;
import com.bjss.repo.StockRepository;
import com.bjss.service.PriceBasketService;
import com.bjss.service.PromotionsService;

/**
 * The service implementation for price basket 
 * 
 * @author Suresh
 *
 */
public class PriceBasketServiceImpl implements PriceBasketService {

	private Logger logger = LoggerFactory.getLogger(PriceBasketServiceImpl.class);

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private PromotionsService promotionsService;

	@Override
	public PriceBasketSummary processPriceBasket(String... items) {

		//Basket should not be empty
		if (items == null || items.length == 0) {
			logger.error("PriceBasket items can not be null!");
			throw new NoItemsInShoppingBasketException("No items in the basket");
		}

		//Populate shopping cart with items
		ShoppingBasket shoppingBasket = new ShoppingBasket();

		Arrays.stream(items).forEach(item -> shoppingBasket.addItemToCart(stockRepository.getByName(item)));

		//Apply promotions
		PriceBasketSummary shoppingBasketSummary = promotionsService.applyPromotions(shoppingBasket);

		shoppingBasketSummary.validate();

		return shoppingBasketSummary;
	}

}
