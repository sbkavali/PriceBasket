package com.bjss.promo;

import java.math.BigDecimal;

import com.bjss.data.PriceBasketSummary;

/**
 * Parent for all promotions. The concrete implementations decide on how to
 * apply a promotion. New types of promotions can be introduced by creating new
 * concrete implementationsn of this contract.
 * 
 * @author Suresh
 *
 */
public interface Promotion {

	public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

	public default BigDecimal getPercentage(BigDecimal value, BigDecimal discountPercentage) {
		return value.multiply(discountPercentage).divide(ONE_HUNDRED);
	}

	/**
	 * 
	 * @return the name of the item where this promotion is applied
	 */
	public String getOnPromotionItemName();

	/**
	 * The method to apply promotion on price basket
	 * 
	 * @param priceBasketSummary
	 */
	public void applyPromotion(PriceBasketSummary priceBasketSummary);

	/**
	 * The description of promotion applied
	 * 
	 * @return the promo string to display on console
	 */
	public String getSummaryDescription();

	/**
	 * Method to check if the promotion is applicable or not
	 * 
	 * @param shoppingBasketSummary the shopping basket based on which, the
	 *                              eligibility is calculated
	 * @return the true if promotion is eligible otherwise false
	 */
	public boolean isApplicable(PriceBasketSummary shoppingBasketSummary);

	/**
	 * Validate the promotion configuration
	 */
	public void validate();
}
