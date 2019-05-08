package com.bjss.repo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjss.data.Item;
import com.bjss.promo.MultiBuyPercentageDiscountPromotion;
import com.bjss.promo.Promotion;
import com.bjss.promo.SimplePercentageDiscountPromotion;

/**
 * A virtual repository class of current promotions
 * 
 * @author Suresh
 *
 */
public class PromotionsRepository {

	@Autowired
	private StockRepository stockRepository;

	private List<? extends Promotion> promotions;

	/**
	 * Populate the promotions available
	 */
	@PostConstruct
	public void init() {
		Item apples = stockRepository.getByName("Apples");
		Item soup = stockRepository.getByName("Soup");
		Item bread = stockRepository.getByName("Bread");

		final Promotion applePromotion = new SimplePercentageDiscountPromotion(apples, BigDecimal.valueOf(10));
		final Promotion breadPromotion = new MultiBuyPercentageDiscountPromotion(bread, BigDecimal.valueOf(50), soup, 2);
		promotions = Collections.unmodifiableList(Arrays.asList(applePromotion, breadPromotion));

	}
	
	/**
	 * Find the promotions applicable based on basket items
	 * 
	 * @param basketItems the shopping cart
	 * @return the promotions available
	 */
	public Set<Promotion> getApplicablePromotions(List<String> basketItems) {

		return promotions.stream().filter(promoItem -> basketItems.contains(promoItem.getOnPromotionItemName()))
				.collect(Collectors.toSet());
	}

}
