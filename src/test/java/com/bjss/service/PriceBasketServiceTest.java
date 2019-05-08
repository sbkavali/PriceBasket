package com.bjss.service;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bjss.BaseTest;
import com.bjss.data.Item;
import com.bjss.data.PriceBasketSummary;
import com.bjss.data.ShoppingBasket;
import com.bjss.exception.NoItemsInShoppingBasketException;
import com.bjss.exception.NoSuchItemException;
import com.bjss.repo.PromotionsRepository;
import com.bjss.repo.StockRepository;
import com.bjss.service.impl.PriceBasketServiceImpl;
import com.bjss.service.impl.PromotionsServiceImpl;

/**
 * Test various scenarios of performing price basket calculations
 * 
 * @author Suresh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PriceBasketServiceImpl.class, PromotionsServiceImpl.class, StockRepository.class,
		PromotionsRepository.class })
public class PriceBasketServiceTest extends BaseTest {

	@Autowired
	private PriceBasketService test;

	@Autowired
	private StockRepository stockRepository;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void whenNoItemsInBasket() {
		exceptionRule.expect(NoItemsInShoppingBasketException.class);
		exceptionRule.expectMessage("No items in the basket");

		test.processPriceBasket();
	}
	
	@Test
	public void whenInvalidItemsSupplied() {
		exceptionRule.expect(NoSuchItemException.class);
		exceptionRule.expectMessage("No such item with the name - invaliditem");

		test.processPriceBasket("invaliditem");
	}

	@Test
	public void whenNoPromotionsApplied() {
		Item milk = stockRepository.getByName("Milk");

		ShoppingBasket basket = new ShoppingBasket();
		basket.addItemToCart(milk);

		PriceBasketSummary shoppingBasketSummary = test.processPriceBasket("Milk");

		String basketSummaryMessage = shoppingBasketSummary.getBasketSummaryString();

		assertTrue("Unexpected milk subtotal", basketSummaryMessage.contains(MILK_SUB_TOTAL));
		assertTrue("Unexpected milk promotion", basketSummaryMessage.contains(NO_OFFERS_MSG));
		assertTrue("Unexpected milk total", basketSummaryMessage.contains(MILK_TOTAL));

	}
	
	

	@Test
	public void whenSimplePercentagePromotionsApplied() {
		PriceBasketSummary shoppingBasketSummary = test.processPriceBasket("Apples");

		String basketSummaryMessage = shoppingBasketSummary.getBasketSummaryString();

		assertTrue("Unexpected apples subtotal", basketSummaryMessage.contains(APPLES_SUB_TOTAL));
		assertTrue("Unexpected apples promotion", basketSummaryMessage.contains(APPLES_PROMOTION));
		assertTrue("Unexpected apples total", basketSummaryMessage.contains(APPLES_TOTAL));
	}

	@Test
	public void whenSimplePercentagePromotionsAppliedOnMultipleItems() {
		PriceBasketSummary shoppingBasketSummary = test.processPriceBasket("Apples", "Apples");

		String basketSummaryMessage = shoppingBasketSummary.getBasketSummaryString();

		assertTrue("Unexpected apples subtotal", basketSummaryMessage.contains(TWO_APPLES_SUB_TOTAL));
		assertTrue("Unexpected apples promotion", basketSummaryMessage.contains(TWO_APPLES_PROMOTION));
		assertTrue("Unexpected apples total", basketSummaryMessage.contains(TWO_APPLES_TOTAL));
	}

	@Test
	public void whenAppliedBundlePromotion() {
		PriceBasketSummary shoppingBasketSummary = test.processPriceBasket("Soup", "Soup", "Bread");

		String basketSummaryMessage = shoppingBasketSummary.getBasketSummaryString();

		assertTrue("Unexpected Soup & Bread subtotal", basketSummaryMessage.contains(SOUP_SOUP_BREAD_SUB_TOTAL));
		assertTrue("Unexpected Soup & Bread promotion", basketSummaryMessage.contains(BREAD_PROMOTION));
		assertTrue("Unexpected Soup & Bread total", basketSummaryMessage.contains(SOUP_SOUP_BREAD_TOTAL));
	}

	@Test
	public void whenPromoAppliedOnApplesAndBread() {
		PriceBasketSummary shoppingBasketSummary = test.processPriceBasket("Apples", "Soup", "Soup", "Bread");

		String basketSummaryMessage = shoppingBasketSummary.getBasketSummaryString();

		assertTrue("Unexpected Soup & Bread subtotal", basketSummaryMessage.contains(APPLES_SOUPS_BREAD_SUB_TOTAL));
		assertTrue("Unexpected Soup & Bread promotion", basketSummaryMessage.contains(APPLES_PROMOTION));
		assertTrue("Unexpected Soup & Bread promotion", basketSummaryMessage.contains(BREAD_PROMOTION));
		assertTrue("Unexpected Soup & Bread total", basketSummaryMessage.contains(APPLES_SOUPS_BREAD_TOTAL));
	}

	@Test
	public void whenPromoAppliedOnMixedBasket() {
		PriceBasketSummary shoppingBasketSummary = test.processPriceBasket("Apples", "Apples", "Soup", "Soup",
				"Bread", "Soup", "Soup", "Bread", "Bread", "Milk");

		String basketSummaryMessage = shoppingBasketSummary.getBasketSummaryString();

		assertTrue("Unexpected Soup & Bread subtotal", basketSummaryMessage.contains(MIXED_BASKET_SUB_TOTAL));
		assertTrue("Unexpected Soup & Bread promotion", basketSummaryMessage.contains(TWO_APPLES_PROMOTION));
		assertTrue("Unexpected Soup & Bread promotion", basketSummaryMessage.contains(TWO_BREADS_PROMOTION));
		assertTrue("Unexpected Soup & Bread total", basketSummaryMessage.contains(MIXED_BASKET_TOTAL));
	}

}
