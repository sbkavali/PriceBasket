package com.bjss.promo;

import java.math.BigDecimal;

import com.bjss.data.Item;
import com.bjss.data.PriceBasketSummary;
import com.bjss.exception.InvalidPromotionException;

/**
 * Simple promotion where the item value is discounted by percentage of its
 * value
 * 
 * @author Suresh
 *
 */
public class SimplePercentageDiscountPromotion implements Promotion {

	/**
	 * The item on which the promotions is applied
	 */
	private final Item item;

	/**
	 * The discount percentage
	 */
	private final BigDecimal discountPercentage;

	public SimplePercentageDiscountPromotion(Item item, BigDecimal discountPercentage) {
		this.item = item;
		this.discountPercentage = discountPercentage;
	}

	/**
	 * @return The item on which promotion is applied
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @return The discount percentage
	 */
	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	@Override
	public String getSummaryDescription() {
		return String.format("%s %.0f%s off: ", item.getName(), discountPercentage, "%");
	}

	/**
	 * Apply the promotion and populate price basket summary
	 */
	@Override	
	public void applyPromotion(PriceBasketSummary priceBasketSummary) {
		validate();
		int qty = priceBasketSummary.getShoppingBasket().getCart().get(item);
		BigDecimal discountValue = getPercentage(item.getPrice(), discountPercentage).multiply(BigDecimal.valueOf(qty))
				.setScale(2);

		priceBasketSummary.getPromotionsSummary().addAppliedPromotion(this, discountValue);

	}

	/**
	 * The name of the item
	 */
	@Override
	public String getOnPromotionItemName() {
		return item.getName();
	}

	/**
	 * Check if the basket contains items with promotion
	 */
	@Override
	public boolean isApplicable(PriceBasketSummary shoppingBasketSummary) {
		int qty = shoppingBasketSummary.getShoppingBasket().getCart().get(item);
		return qty > 0 ? true : false;
	}

	/**
	 * Simple validation to ensure the discount percentage is not more than the value of item
	 */
	@Override
	public void validate() {
		if (item == null || discountPercentage.compareTo(ONE_HUNDRED) > 0) {
			throw new InvalidPromotionException("Discount can not be more than the value of discounted item.");
		}

	}

}
