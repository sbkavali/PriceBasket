package com.bjss.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bjss.BaseTest;
import com.bjss.data.Item;
import com.bjss.data.PriceBasketSummary;
import com.bjss.data.ShoppingBasket;
import com.bjss.promo.MultiBuyPercentageDiscountPromotion;
import com.bjss.promo.Promotion;
import com.bjss.promo.SimplePercentageDiscountPromotion;
import com.bjss.repo.PromotionsRepository;
import com.bjss.repo.StockRepository;
import com.bjss.service.impl.PromotionsServiceImpl;

/**
 * Test various scenarios of applying promotions on shopping basket
 * 
 * @author Suresh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PromotionsServiceImpl.class, StockRepository.class })
public class PromotionServiceTest extends BaseTest {

	@Autowired
	private PromotionsService test;

	@Autowired
	private StockRepository stockRepository;

	@MockBean
	private PromotionsRepository promotionsRepository;

	@Test
	public void whenNoPromotionsPresent() {
		Mockito.when(promotionsRepository.getApplicablePromotions(Arrays.asList("Apples")))
				.thenReturn(Collections.<Promotion>emptySet());

		ShoppingBasket basket = new ShoppingBasket();
		basket.addItemToCart(stockRepository.getByName("Apples"));

		PriceBasketSummary shoppingBasketSummary = test.applyPromotions(basket);

		String actualPromoMessage = shoppingBasketSummary.getPromotionsSummary().getPromotionSummaryString();

		assertEquals("Unexpected promotions applied message", NO_OFFERS_MSG, actualPromoMessage);
	}

	@Test
	public void whenNonApplicablePromotionsPresent() {
		Item soup = stockRepository.getByName("Soup");
		Item bread = stockRepository.getByName("Bread");
		final Promotion promotionOnBread = new MultiBuyPercentageDiscountPromotion(bread, BigDecimal.valueOf(50), soup,
				2);
		Mockito.when(promotionsRepository.getApplicablePromotions(Arrays.asList("Bread")))
				.thenReturn(Collections.singleton(promotionOnBread));

		ShoppingBasket basket = new ShoppingBasket();
		basket.addItemToCart(stockRepository.getByName("Soup"));
		basket.addItemToCart(stockRepository.getByName("Bread"));

		PriceBasketSummary shoppingBasketSummary = test.applyPromotions(basket);

		String actualPromoMessage = shoppingBasketSummary.getPromotionsSummary().getPromotionSummaryString();

		assertEquals("Unexpected promotions applied message", NO_OFFERS_MSG, actualPromoMessage);
	}

	@Test
	public void whenAppliedSimplePromotion() {
		Item apples = stockRepository.getByName("Apples");
		Promotion promotionOnApples = new SimplePercentageDiscountPromotion(apples, BigDecimal.valueOf(10));

		Mockito.when(promotionsRepository.getApplicablePromotions(Arrays.asList("Apples")))
				.thenReturn(Collections.singleton(promotionOnApples));

		ShoppingBasket basket = new ShoppingBasket();
		basket.addItemToCart(stockRepository.getByName("Apples"));

		PriceBasketSummary shoppingBasketSummary = test.applyPromotions(basket);

		String actualPromoMessage = shoppingBasketSummary.getPromotionsSummary().getPromotionSummaryString();

		assertTrue("Apples promotion message missing", actualPromoMessage.contains(APPLES_PROMOTION));
	}

	@Test
	public void whenAppliedMultiBuyPromotion() {
		Item soup = stockRepository.getByName("Soup");
		Item bread = stockRepository.getByName("Bread");

		Promotion breadPromotion = new MultiBuyPercentageDiscountPromotion(bread, BigDecimal.valueOf(50), soup, 2);

		Mockito.when(promotionsRepository.getApplicablePromotions(Mockito.anyList()))
				.thenReturn(Collections.singleton(breadPromotion));

		ShoppingBasket basket = new ShoppingBasket();
		basket.addItemToCart(soup);
		basket.addItemToCart(soup);
		basket.addItemToCart(bread);

		PriceBasketSummary shoppingBasketSummary = test.applyPromotions(basket);

		String actualPromoMessage = shoppingBasketSummary.getPromotionsSummary().getPromotionSummaryString();

		assertTrue("Bread promotions message missing", actualPromoMessage.contains(BREAD_PROMOTION));
	}

	@Test
	public void whenAppliedSimpleAndMultiBuyPromotion() {
		Item apples = stockRepository.getByName("Apples");
		Item soup = stockRepository.getByName("Soup");
		Item bread = stockRepository.getByName("Bread");

		Promotion promotionOnApples = new SimplePercentageDiscountPromotion(apples, BigDecimal.valueOf(10));
		Promotion breadPromotion = new MultiBuyPercentageDiscountPromotion(bread, BigDecimal.valueOf(50), soup, 2);

		Mockito.when(promotionsRepository.getApplicablePromotions(Mockito.anyList()))
				.thenReturn(new HashSet<Promotion>() {
					{
						add(promotionOnApples);
						add(breadPromotion);
					}
				});

		ShoppingBasket basket = new ShoppingBasket();
		basket.addItemToCart(apples);
		basket.addItemToCart(soup);
		basket.addItemToCart(soup);
		basket.addItemToCart(bread);

		PriceBasketSummary shoppingBasketSummary = test.applyPromotions(basket);

		String actualPromoMessage = shoppingBasketSummary.getPromotionsSummary().getPromotionSummaryString();

		assertTrue("Apples promotion message missing", actualPromoMessage.contains(APPLES_PROMOTION));
		assertTrue("Bread promotions message missing", actualPromoMessage.contains(BREAD_PROMOTION));
	}

	@Test
	public void whenAppliedSimpleAndMultiBuyMixPromotion() {
		Item apples = stockRepository.getByName("Apples");
		Item soup = stockRepository.getByName("Soup");
		Item bread = stockRepository.getByName("Bread");

		Promotion promotionOnApples = new SimplePercentageDiscountPromotion(apples, BigDecimal.valueOf(10));
		Promotion breadPromotion = new MultiBuyPercentageDiscountPromotion(bread, BigDecimal.valueOf(50), soup, 2);

		Mockito.when(promotionsRepository.getApplicablePromotions(Mockito.anyList()))
				.thenReturn(new HashSet<Promotion>() {
					{
						add(promotionOnApples);
						add(breadPromotion);
					}
				});

		ShoppingBasket basket = new ShoppingBasket();
		basket.addItemToCart(apples);
		basket.addItemToCart(soup);
		basket.addItemToCart(soup);
		basket.addItemToCart(bread);
		basket.addItemToCart(soup);
		basket.addItemToCart(soup);
		basket.addItemToCart(bread);
		basket.addItemToCart(soup);
		basket.addItemToCart(bread);

		PriceBasketSummary shoppingBasketSummary = test.applyPromotions(basket);

		String actualPromoMessage = shoppingBasketSummary.getPromotionsSummary().getPromotionSummaryString();

		assertTrue("Apples promotion message missing", actualPromoMessage.contains(APPLES_PROMOTION));
		assertTrue("Bread promotions message missing", actualPromoMessage.contains(TWO_BREADS_PROMOTION));
	}

}
