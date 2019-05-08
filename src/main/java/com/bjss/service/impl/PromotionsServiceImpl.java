package com.bjss.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjss.data.ShoppingBasket;
import com.bjss.data.PriceBasketSummary;
import com.bjss.promo.Promotion;
import com.bjss.repo.PromotionsRepository;
import com.bjss.service.PromotionsService;

/**
 * The service implementation for applying promotions on shopping basket
 * 
 * @author Suresh
 *
 */
@Service
public class PromotionsServiceImpl implements PromotionsService {

	@Autowired
	PromotionsRepository promotopmsRepository;

	/**
	 * Apply promotions on shopping basket and return promotions summary
	 */
	@Override
	public PriceBasketSummary applyPromotions(ShoppingBasket shoppingBasket) {
		List<String> basketItemNames = shoppingBasket.getCart().keySet().stream().map(item -> item.getName())
				.collect(Collectors.toList());
		
		//Load the applicable promotions from repository
		Set<Promotion> promotionsApplicable = promotopmsRepository.getApplicablePromotions(basketItemNames);
		PriceBasketSummary shoppingBasketSummary = new PriceBasketSummary(shoppingBasket);
		for (Promotion promotion : promotionsApplicable) {
			//check if promotion is applicable on shopping basket
			if (promotion.isApplicable(shoppingBasketSummary)) {
				//apply promotion
				promotion.applyPromotion(shoppingBasketSummary);
			}
		}

		return shoppingBasketSummary;

	}

}
