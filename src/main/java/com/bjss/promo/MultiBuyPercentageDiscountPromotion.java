package com.bjss.promo;

import java.math.BigDecimal;

import com.bjss.data.Item;
import com.bjss.data.PriceBasketSummary;
import com.bjss.exception.InvalidPromotionException;

/**
 * The promotion implementation for discount on bundle items (For eg: Buy 2
 * soups to get Bread at 50%)
 * 
 * @author Suresh
 *
 */
public class MultiBuyPercentageDiscountPromotion implements Promotion {

	/**
	 * The item on which discount is applicable
	 */
	private Item discountedItem;

	/**
	 * The discount percentage
	 */
	private final BigDecimal discountPercentage;

	/**
	 * The items on which the promo available
	 */
	private Item saleItem;

	/**
	 * The no of items to buy to get eligibility for promotion
	 */
	private int saleQty;

	public MultiBuyPercentageDiscountPromotion(Item discountedItem, BigDecimal discountPercentage, Item saleItem,
			int saleQty) {
		this.discountedItem = discountedItem;
		this.discountPercentage = discountPercentage;
		this.saleItem = saleItem;
		this.saleQty = saleQty;
	}

	/**
	 * Apply the promotion on shopping basket and populate Price basket summary
	 */
	@Override
	public void applyPromotion(PriceBasketSummary priceBasketSummary) {
		validate();
		int discountedItemsPurchaseQty = priceBasketSummary.getShoppingBasket().getCart()
				.getOrDefault(discountedItem, 0);

		if (discountedItemsPurchaseQty == 0)
			return;

		int purchaseQty = priceBasketSummary.getShoppingBasket().getCart().get(saleItem);

		int targetCount = purchaseQty / saleQty;

		int discountOnTargetCount = Math.min(targetCount, discountedItemsPurchaseQty);

		BigDecimal netDiscount = getPercentage(discountedItem.getPrice(), discountPercentage)
				.multiply(BigDecimal.valueOf(discountOnTargetCount)).setScale(2);

		priceBasketSummary.getPromotionsSummary().addAppliedPromotion(this, netDiscount);
	}

	@Override
	public String getOnPromotionItemName() {
		return saleItem.getName();
	}

	/**
	 * The summary of promotion applied - which is displayed on console
	 */
	@Override
	public String getSummaryDescription() {
		return String.format("%s %.0f%s off (Buy %d %s): ", discountedItem.getName(), discountPercentage, "%", saleQty,
				saleItem.getName());
	}

	/**
	 * Check if the promotion is applicable on cart
	 */
	@Override
	public boolean isApplicable(PriceBasketSummary shoppingBasketSummary) {
		int discountedItemsPurchaseQty = shoppingBasketSummary.getShoppingBasket().getCart()
				.getOrDefault(discountedItem, 0);

		if (discountedItemsPurchaseQty == 0)
			return false;

		int purchaseQty = shoppingBasketSummary.getShoppingBasket().getCart().get(saleItem);

		int targetCount = purchaseQty / saleQty;

		return targetCount > 0 ? true : false;
	}

	@Override
	public void validate() {
		if (discountedItem == null || saleQty <= 0 || saleItem == null
				|| discountPercentage.compareTo(ONE_HUNDRED) > 0) {
			throw new InvalidPromotionException("Discount can not be more than the value of discounted item.");
		}

	}

}
